package we.kill.the.batman.teamnine.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

public class TTSService extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		Intent checkIntent = new Intent();
//		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
//		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
//	String myText1 = "Did you sleep well?";
//	String myText2 = "I hope so, because it's time to wake up.";
//
//	Intent checkIntent = new Intent();
//	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//	startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
//	
//	private TextToSpeech mTts;
//	protected void onActivityResult(
//	        int requestCode, int resultCode, Intent data) {
//	    if (requestCode == MY_DATA_CHECK_CODE) {
//	        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
//	            // success, create the TTS instance
//	            mTts = new TextToSpeech(this, this);
//	        } else {
//	            // missing data, install it
//	            Intent installIntent = new Intent();
//	            installIntent.setAction(
//	                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//	            startActivity(installIntent);
//	        }
//	    }
//	}
//	
//	mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
//	mTts.speak(myText2, TextToSpeech.QUEUE_ADD, null);
//	mTts.setLanguage(Locale.US);
//	

}
