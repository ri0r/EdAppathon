package activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Controller;
import utils.Location;
import utils.Routes;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import batmanmustdie.src.R;
import data.DataBaseAccessorHelper;

public class ChooseRoute extends Activity{
	
	Context c = getBaseContext();
	
	private final String TAG = "ChooseRoute";
	
	Controller controller = Controller.getInstance();
		
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.d(TAG,"Got to ChooseRoute activity");
    	setContentView(R.layout.chooseroutes);
    	
    	final DataBaseAccessorHelper dah = new DataBaseAccessorHelper(getApplicationContext());
    	try {
    		dah.createDataBase();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	dah.openDataBase();
    	final TextView start = (TextView)findViewById(R.id.startLocationEditor);
		final TextView end = (TextView)findViewById(R.id.endLocationEditor);
		final ListView viewPrevRoutes = (ListView) findViewById(R.id.savedRoutesListView);
    	Button goButton = (Button)findViewById(R.id.goButton);
    	goButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View view) {
    			 //Disabled for now, must be tested later
    			 //getRoads will probably be called from mainActivity. No need for it here other than checking that the entered locations are correct
    			String startLocation = start.getText().toString();
    			String endLocation = end.getText().toString();
    		
    			 List<Location> edLocs = controller.getRoads(startLocation, endLocation);
    			 //insert start and destination into the database   			 
    			 dah.insertRoute(new Routes(startLocation, endLocation)); 
    			 
    			// Go back to mainActivity
    			Intent i = new Intent(view.getContext(), mainActivity.class);
    			controller.currentRoute[0] = startLocation;
				controller.currentRoute[1] = endLocation;
    			startActivity(i);
    			// not sure why this is needed, Oleg
                setResult(RESULT_OK, i);
                finish();
    		}
    	});
    	
    	
    	viewPrevRoutes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String s = (String) viewPrevRoutes.getItemAtPosition(position);
				String[] se = s.split(" to ");
				Log.d(TAG, "start: ".concat(se[0]));
				Log.d(TAG, "stop: ".concat(se[1]));
				controller.currentRoute[0] = se[0];
				controller.currentRoute[1] = se[1];
				// Go back to mainActivity
    			Intent i = new Intent(view.getContext(), mainActivity.class);
    			startActivity(i);
                setResult(RESULT_OK, i);
                finish();
			}
    		
    	});

    	final ArrayList<String> prevRoutes = dah.getRoutes();
    	Log.d("Accessing DB", ""+prevRoutes.size());
    	if (prevRoutes != null) { 
    		Log.d("ChooseRoute", "Trying to display Previous routes");
//    		ArrayList<String> a1 = new ArrayList<String>();
    		ArrayAdapter<String> a2 = new ArrayAdapter<String>(this, R.layout.testlayout, prevRoutes);
    		viewPrevRoutes.setAdapter(a2);
    	}
    	viewPrevRoutes.setOnItemLongClickListener(new OnItemLongClickListener(){
    		
    		@Override
    		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
    				int arg2, long arg3) {
    			prevRoutes.remove(arg2);
    			viewPrevRoutes.invalidateViews();
    			return false;
    		}
    		
    	});
    }
	
}
