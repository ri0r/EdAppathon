package we.kill.the.batman.teamnine;

import java.util.List;

import we.kill.the.batman.teamnine.data.DataBaseAccessor;
import we.kill.the.batman.teamnine.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EdAppathonActivity extends Activity {
    /** Called when the activity is first created. */
	private static String MSG_TO_SAY = "MSG_TO_SAY";
	private String msgToSay;
	Context c = getBaseContext();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("Activity","Got Here!  Into onCreate EdAppathonActivity");
        setContentView(R.layout.chooseroutes);
        Button b = (Button)findViewById(R.id.getRoadsButton);
        b.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
        		TextView v1 = (TextView)findViewById(R.id.startLocationEditor);
        		TextView v2 = (TextView)findViewById(R.id.endLocationEditor);
        		List<EdLocation> edLocs = Utility.getRoads(v1.getText().toString(), v2.getText().toString());
        		DataBaseAccessor.getInstance(c).insertLocations(edLocs);
        		
        	}
        });
        
    	super.onCreate(savedInstanceState);
       

    }
 
}