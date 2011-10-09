package we.kill.the.batman.teamnine;

import java.util.Locale;


import java.util.List;

import we.kill.the.batman.teamnine.data.DataBaseAccessor;
import we.kill.the.batman.teamnine.util.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.TextView;

public class EdAppathonActivity extends Activity implements OnInitListener{
    /** Called when the activity is first created. */

//	private static String MSG_TO_SAY = "MSG_TO_SAY";
//	private String msg;
	private TextToSpeech mTts;
	private static final String TAG = "Text-to-Speech";
	

    public void startTextToSpeech(){
    	mTts = new TextToSpeech(this, this);
    	mTts.setSpeechRate((float) 0.5);
    }

	@Override
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Language data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
//                 mAgainButton.setEnabled(true);
                Log.i(TAG, "Text-To-Speech has succesfully started.");
            }
        } else {
            // Initialization failed.
            Log.e(TAG, "Could not initialize TextToSpeech.");
        }
	}
	public void say(String msg) {
		mTts.speak(msg,
	            TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
	            null);
	}
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

    	startTextToSpeech();
    	this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	say("Hello Batman, time to die!");
    	super.onCreate(savedInstanceState);


    }

}