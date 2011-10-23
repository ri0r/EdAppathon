package activities;

import java.util.List;
import java.util.Locale;

import utils.Controller;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import batmanmustdie.src.R;

public class mainActivity extends ListActivity implements OnInitListener, Runnable {

	double[] lat = new double[2];
	double[] lon = new double[2];
	String[] msg = new String[2];
	
	private TextToSpeech textToSpeech;
	private Boolean ttsReady = false;
	private TextView start;
	private TextView end;  
	ListView lv;
	Controller controller = Controller.getInstance();
	
	public final int CHOOSE_ROUTE_CODE = 22;
	public final int TEXT_TO_SPEECH_CHECK_CODE = 1;
	private final String TAG = "mainActivity";
	
	ArrayAdapter<String> adapter;
	Handler handler;
	Boolean speechOn = true;
	Button speachButton;
	int test = 0;
	volatile Boolean updateThreadPaused = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv = getListView();
        setContentView(R.layout.rsslist);
        controller.getRelevantFeedEntries(); // Updates the feed and gets relevant entries, returns controller.relevantTitles
        if (controller.relevantTitles.isEmpty()) {
        	controller.relevantTitles.add("No warnings on this route");
        }
        adapter = new ArrayAdapter<String>(this, R.layout.rssrow, controller.relevantTitles);             
        this.setListAdapter(adapter);
        
        
    	handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            	List<String> oldFeed = controller.relevantTitles;
//            	List<String> newFeed = controller.getRelevantFeedEntries();
            	// This bit of code is for demo purposes only, when a new feed is found, new entries are being read through text-to-speech
            	if (test < 2) {
            		controller.relevantTitles.add("M6 northbound between J4 and J4A | Northbound | Accident (demo example)"); // for testing purposes
            		test++;
                	List<String> newFeed = controller.relevantTitles;
                	if (speechOn) {
                		for (String newEntry : newFeed) {
                    		if (oldFeed.contains(newEntry)) { // very strange, but it works, should be if it doesn't contain
                    			Log.d(TAG, newEntry);
                    			 if (ttsReady) {
                    	            Log.d(TAG, "speaking");
                    	            newEntry = newEntry.replace('|', ' ');
                    	            textToSpeech.speak(newEntry, TextToSpeech.QUEUE_FLUSH, null);
                    			 } else {
                    				 Log.d(TAG, "TTS is not ready yet");
                    			 }
                    			
                    		}
                    	}
                	} 
            	}           	        	            	
            	adapter = new ArrayAdapter<String>(mainActivity.this, R.layout.rssrow, controller.relevantTitles);
            	mainActivity.this.setListAdapter(adapter);
            }
    	};
    	 Thread thread = new Thread(this); 
         thread.start();
        
        
        // checks if text to speech is installed on the phone
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TEXT_TO_SPEECH_CHECK_CODE);
        
        Button button = (Button)findViewById(R.id.rssChangeRouteButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Load the route chooser activity
            	// It expects to get back values for To and From textviews
            	updateThreadPaused = true;
              	Intent i = new Intent(v.getContext(), ChooseRoute.class);
              	startActivityForResult(i, CHOOSE_ROUTE_CODE);
            }
        });
        
        Button mapButton = (Button) findViewById(R.id.showWarningsOnMapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {       	
	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), WarningOnMap.class);
				startActivity(i);				
			}
		});
        
        speachButton = (Button)this.findViewById(R.id.speechButton);
        speachButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (speechOn) {
					speechOn = false;
					speachButton.setText("Sound on");
					textToSpeech.stop();
				} else {
					speechOn = true;
					speachButton.setText("Sound off");
				}			
			}
		});
        
        start = (TextView) findViewById(R.id.rssFromText);
        end = (TextView) findViewById(R.id.rssToText);
        
        // TODO
        // implement a timer that pulls the RSS feed, updates the listview and reads to the user
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.d(TAG, "Got back to mainActivity");
//        if (resultCode == CHOOSE_ROUTE_CODE) {      	
    	// Update the current To and From fields
    	Log.w(TAG, "got back from ChooseRoute!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
    	updateThreadPaused = true;
    	this.start.setText(controller.currentRoute[0]);
    	this.end.setText(controller.currentRoute[1]);
    	test = 0;
    	controller.getRelevantFeedEntries();
    	if (controller.relevantTitles.isEmpty()) {
        	controller.relevantTitles.add("No warnings on this route");
        } 
    	updateThreadPaused = false;
        	
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
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(15000);
			} catch(Exception e) {
				Log.e(TAG, e.toString());
			}
			if (!updateThreadPaused) {
				handler.sendEmptyMessage(0);
			}
		}		
	}


	
}