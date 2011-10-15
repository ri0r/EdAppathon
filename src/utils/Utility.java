package utils;

import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Utility {
	
	private static final String TAG="Utility";
	
	public static List<Location> getRoads(String start, String end) {
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://maps.googleapis.com/maps/api/directions/json?origin=");
		
		sb.append(start.trim());
		
		sb.append("&destination=");
		sb.append(end.trim());
		sb.append("&sensor=false");
		sb.append("&alternatives=true&region=uk");
		String s = sb.toString();
		Pattern p = Pattern.compile("[\\s]");
		Pattern road = Pattern.compile("[ABJM][0-9]+");
		
		JSONObject json = new JSONObject();
		ArrayList<String> htmlInstructions = new ArrayList<String>();	
		List<Location> edLocs = new ArrayList<Location>();
		
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
			//ArrayList<String> routes = new ArrayList<String>();
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
					float lat = Float.parseFloat(jstart.getString("lat"));
					float lng = Float.parseFloat(jstart.getString("lng"));
					Matcher m = road.matcher(instr);
					while (m.find()) {
						
						edLocs.add(new Location(m.group(0), lat, lng));
						Log.d(TAG, "road added " + m.group(0));
					}
					//Log.d(TAG,jstep.getString("html_instructions"));
					htmlInstructions.add(jstep.getString("html_instructions"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//		} catch (JSONException je) {
//			je.printStackTrace();
		}
		
//		Pattern p = Pattern.compile("[AJM][0-9]+");
//		for (String test : htmlInstructions) {
//			Matcher m = p.matcher(test);
//			if (m.find()) {
//				edLocs.add(new Location(m.group(0)));
//			}
//		}
		return edLocs;
	}

}
