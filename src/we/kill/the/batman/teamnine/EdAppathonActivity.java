package we.kill.the.batman.teamnine;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.widget.TextView;

public class EdAppathonActivity extends Activity implements OnInitListener{
    /** Called when the activity is first created. */
//	private static String MSG_TO_SAY = "MSG_TO_SAY";
//	private String msg;
	private TextToSpeech mTts;
	private static final String TAG = "Text-to-Speech";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello Austin");
        setContentView(R.layout.main);
//        msg = "Hello Batman, time to die!";// hahahhaahha hahhaha hahahaha hahahaha ";
//        Intent i = new Intent(EdAppathonActivity.this, TTSService.class);
//        i.putExtra(MSG_TO_SAY, msgToSay);
//        startActivity(i);
//        startActivity(i);
        startTextToSpeech();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        say("Hello Batman, time to die!");
    }
    
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
}