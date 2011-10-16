package activities;

import java.util.List;

import data.DataBaseAccessor;

import utils.Location;
import utils.Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import batmanmustdie.src.R;

public class ChooseRoute extends Activity{
	
	Context c = getBaseContext();
	
	private final String TAG = "ChooseRoute";
	
	Controller controller = Controller.getInstance();
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.d(TAG,"Got to ChooseRoute activity");
    	setContentView(R.layout.chooseroutes);
    	
    	final TextView start = (TextView)findViewById(R.id.startLocationEditor);
		final TextView end = (TextView)findViewById(R.id.endLocationEditor);
    	Button goButton = (Button)findViewById(R.id.goButton);
    	goButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View arg0) {
    			 //Disabled for now, must be tested later
    			 //getRoads will probably be called from mainActivity. No need for it here other than checking that the entered locations are correct
    			 List<Location> edLocs = controller.getRoads(start.getText().toString(), end.getText().toString());
    			 //insert start and destination into the database
//    			DataBaseAccessor.getInstance(c).insertLocations(edLocs);
    			
    			// Go back to mainActivity
    			Intent i = new Intent();
    			i.putExtra("start", start.getText().toString());
    			i.putExtra("end", end.getText().toString());
                setResult(RESULT_OK, i);
                finish();
    		}
    	});
    	Button switchButton = (Button) findViewById(R.id.switchButton);
    	switchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String temp = start.getText().toString();
				start.setText(end.getText().toString());
				end.setText(temp);
			}
		});
    }
	
}
