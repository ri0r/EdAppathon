package activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import utils.Controller;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import batmanmustdie.src.*;
//import batmanmustdie.src.R;

public class mainActivity extends ListActivity implements OnInitListener {

	double[] lat = new double[2];
	double[] lon = new double[2];
	String[] msg = new String[2];
	
	private TextToSpeech textToSpeech;
	private Boolean ttsReady = false;
	private TextView start;
	private TextView end;  
	ListView lv;
	Controller controller = Controller.getInstance();
	
	public final int CHOOSE_ROUTE_CODE = 0;
	public final int TEXT_TO_SPEECH_CHECK_CODE = 1;
	private final String TAG = "mainActivity";
	
	ArrayAdapter<String> adapter;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv = getListView();
        setContentView(R.layout.rsslist);
        controller.getRelevantFeedEntries(); // Updates the feed and gets relevant entries, returns controller.relevantTitles 
        adapter = new ArrayAdapter<String>(this, R.layout.rssrow, controller.relevantTitles);             
        this.setListAdapter(adapter);
        
        // checks if text to speech is installed on the phone
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TEXT_TO_SPEECH_CHECK_CODE);
        
        Button button = (Button)findViewById(R.id.rssChangeRouteButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Load the route chooser activity
            	// It expects to get back values for To and From textviews
              	Intent i = new Intent(v.getContext(), ChooseRoute.class);
              	startActivityForResult(i, CHOOSE_ROUTE_CODE);
            }
        });
        
        Button mapButton = (Button) findViewById(R.id.showWarningsOnMapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {       	
	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), WarningOnMap.class);
				i.putExtra("lat", lat);
				i.putExtra("lon", lon);
				i.putExtra("msg", msg);
				startActivity(i);				
			}
		});
        
        start = (TextView) findViewById(R.id.rssFromText);
        end = (TextView) findViewById(R.id.rssToText);
        
        // TODO
        // implement a timer that pulls the RSS feed, updates the listview and reads to the user
    }
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == TEXT_TO_SPEECH_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
            	textToSpeech = new TextToSpeech(this, this);
            	Log.d(TAG, "Text to speech exists");
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        } else if (requestCode == CHOOSE_ROUTE_CODE) {
        	// update the warnings for the current route
        	// TODO implement it!
        	
        	// Update the current To and From fields
        	Bundle fromTo = data.getExtras();
        	if (fromTo != null) {
        		// string names are "start" and "end"
        		this.start.setText(fromTo.getString("start"));
        		this.end.setText(fromTo.getString("end"));
        	}
        	// read out updates
            if (ttsReady) {
            	Log.d(TAG, "speaking");
            	
//                String myText1 = fromTo.getString("start");
//                String myText2 = fromTo.getString("end");
                List<String> templist = controller.getRelevantFeedEntries();
                int num = templist.size();
                if (num > 0) {
                	String initial = templist.get(0);
                	initial = initial.replace('|', ' ');
                	textToSpeech.speak(initial, TextToSpeech.QUEUE_FLUSH, null);
                	for (int i =1; i < num; i++) {
                    	initial = templist.get(i);
                    	initial = initial.replace('|', ' ');
                		textToSpeech.speak(initial, TextToSpeech.QUEUE_ADD, null);
                	}
                }
//            	textToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
//            	textToSpeech.speak(myText2, TextToSpeech.QUEUE_ADD, null);
            } 
        }
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(controller.relevantMessages.get(position).getLink().toExternalForm()));
		this.startActivity(viewMessage);
	}
	

	
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Language data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } else {
            	Log.d(TAG, "TextToSpeech is ready for use");
            	ttsReady = true;
            }
        } else {
            // Initialization failed.
            Log.e(TAG, "Could not initialize TextToSpeech.");
        }
		
	}

	
}