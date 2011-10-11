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


public class Utility {
	
	public static List<Location> getRoads(String start, String end) {
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://maps.googleapis.com/maps/api/directions/json?origin=");
		
		sb.append(start);
		
		sb.append("&destination=");
		sb.append(end);
		sb.append("&sensor=false");
		String s = null;
		Pattern p = Pattern.compile("[\\s]");
		JSONObject json = new JSONObject();
		ArrayList<String> htmlInstructions = new ArrayList<String>();	

		try { 
			HttpClient hc = new DefaultHttpClient();
			HttpPost post = new HttpPost(s);

			HttpResponse rp = hc.execute(post);

			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(rp.getEntity()));
			}
			JSONArray jsonArr = json.getJSONArray("steps");
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jo = jsonArr.getJSONObject(i);
				htmlInstructions.add(jo.getString("html_instructions"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException je) {
			je.printStackTrace();
		}
		List<Location> edLocs = new ArrayList<Location>();
//		Pattern p = Pattern.compile("[AJM][0-9]+");
		for (String test : htmlInstructions) {
			Matcher m = p.matcher(test);
			if (m.find()) {
				edLocs.add(new Location(m.group(0)));
			}
		}
		return edLocs;
	}

}
