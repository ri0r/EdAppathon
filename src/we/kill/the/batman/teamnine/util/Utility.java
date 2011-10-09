package we.kill.the.batman.teamnine.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import we.kill.the.batman.teamnine.EdLocation;



public class Utility {
	
	public static List<EdLocation> getRoads(String start, String end) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://maps.googleapis.com/maps/api/directions/json?origin=");
		sb.append(start);
		sb.append("&destination=");
		sb.append(end);
		sb.append("&sensor=false");
	
	        try {
	    		HttpClient hc = new DefaultHttpClient();
	    		HttpPost post = new HttpPost(sb.toString());

	    		HttpResponse rp = hc.execute(post);

	    		if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    			JSONObject json = new JSONObject(EntityUtils.toString(rp.getEntity()));
	    		}
	    	} catch(IOException e) {
	    		e.printStackTrace();
	    	} catch(JSONException je) {
	    		je.printStackTrace();
	    	}
	    	
	    //parse the JSON
	        
		
	}

}
