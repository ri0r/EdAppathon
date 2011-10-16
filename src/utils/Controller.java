package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import parser.FeedParser;
import parser.FeedParserFactory;
import parser.Message;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import batmanmustdie.src.R;


public class Controller {
	
	private Controller() {
		
	}
	
	public static Controller getInstance() {
		if (controller!=null) {
			return controller;
		} else {
			controller = new Controller();
			return controller;
		}
	}
	
	private static Controller controller;
	private static final String TAG="Utility";
	
	private List<Message> allMessages; // all messages from RRS feed
	public List<Message> relevantMessages = new ArrayList<Message>(); // used by mainActivity when user clicks an item in the list, might not be used later on
	
	private ArrayList<String> roadNames = new ArrayList<String>();
	
	public List<String> loadFeed(Context c){
		List<String> titles = new ArrayList<String>();
    	try{
	    	FeedParser parser = FeedParserFactory.getParser();
	    	long start = System.currentTimeMillis();
	    	allMessages = parser.parse();
	    	Log.d(TAG, "number of messages: "+allMessages.size());
	    	long duration = System.currentTimeMillis() - start;
	    	Log.i("AndroidNews", "Parser duration=" + duration);
	    	titles = new ArrayList<String>(allMessages.size());
	    	for (Message msg : allMessages){
	    		titles.add(msg.getTitle());
	    	}
	    	return titles;
    	} catch (Throwable t){
    		Log.e("AndroidNews",t.getMessage(),t);
    	}
		return titles;
    }
	
	// checks if roads retrieved from Google navigation occur in any of the
	// RSS feed entries	
	public ArrayAdapter<String> getRelevantFeedEntries(Context c) {
		getRoads("Edinburgh", "London");
		List<Integer> foundIndexes = new ArrayList<Integer>(); // used to solve the contains problem below
		List<String> relevantTitles = new ArrayList<String>();
		List<String> allTitles = loadFeed(c); // get all feed entries from RSS feed
		for (String roadName : roadNames) {
			for (int i=0;i<allTitles.size();i++) {
				if (allTitles.get(i).contains(roadName)) {// contains is a bad check, M6 is contained in both M6 and M68
					if (!foundIndexes.contains(i))
					// add RSS entry to relevant entries array
					foundIndexes.add(i);
					relevantMessages.add(allMessages.get(i));
					relevantTitles.add(allTitles.get(i));
					Log.d(TAG, "new message, index: "+i);
				}
			}
		}
		Log.d(TAG, "relevant messages: "+relevantMessages.size());
		ArrayAdapter<String> adapter = 
	    		new ArrayAdapter<String>(c, R.layout.rssrow, relevantTitles);
		return adapter;
	}
	
	
	public List<Location> getRoads(String start, String end) {	
		StringBuilder sb = new StringBuilder();
		sb.append("http://maps.googleapis.com/maps/api/directions/json?origin=");		
		sb.append(start.trim().replaceAll("[\\s]+", "+"));		
		sb.append("&destination=");
		sb.append(end.trim().replaceAll("[\\s]+", "+"));
		sb.append("&sensor=false");
		sb.append("&alternatives=true&region=uk");
		String s = sb.toString();
		
		Pattern road = Pattern.compile("[ABJM][0-9]+");
		
		JSONObject json = new JSONObject();
		ArrayList<String> htmlInstructions = new ArrayList<String>();	
		List<Location> edLocs = new ArrayList<Location>();
		ArrayList<String> names = new ArrayList<String>();
		
		try { 
			Log.d(TAG,"1");
			HttpClient hc = new DefaultHttpClient();
			Log.d(TAG,"2");
			HttpPost post = new HttpPost(s);
			Log.d(TAG,"3");

			HttpResponse rp = hc.execute(post);

			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(rp.getEntity()));
			}
			JSONArray jroutes = json.getJSONArray("routes");
			Log.d(TAG, "routes " + Integer.toString(jroutes.length()));
			for (int j = 0; j < jroutes.length(); j++) {
				JSONObject jroute = jroutes.getJSONObject(j);
				JSONArray jlegs = jroute.getJSONArray("legs");
				Log.d(TAG, "legs " + Integer.toString(jlegs.length()));
				JSONObject jleg = jlegs.getJSONObject(0);
				JSONArray jsteps = jleg.getJSONArray("steps");
				Log.d(TAG, "Steps " + Integer.toString(jsteps.length()));
				for (int i = 0; i < jsteps.length(); i++) {
					JSONObject jstep = jsteps.getJSONObject(i);
					String instr = jstep.getString("html_instructions");
					JSONObject jstart = jstep.getJSONObject("start_location");
					double lat = Double.parseDouble(jstart.getString("lat"));
					double lng = Double.parseDouble(jstart.getString("lng"));
					Matcher m = road.matcher(instr);
					while (m.find()) {						
						if (!(names.contains(m.group(0)))) {
							names.add(m.group(0));
							edLocs.add(new Location(m.group(0), lat, lng));
							Log.d(TAG, "road added " + m.group(0));
						}						
					}
					//Log.d(TAG,jstep.getString("html_instructions"));
					htmlInstructions.add(jstep.getString("html_instructions"));
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		roadNames = names; // sets the roadNames so we don't have to get the names out of edLocs
		return edLocs;
	}
}
