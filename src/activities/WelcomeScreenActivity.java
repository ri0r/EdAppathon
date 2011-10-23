package activities;

import batmanmustdie.src.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreenActivity extends Activity{
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.welcomelayout);
		
	}
	
	public void inputRoute(View view) {
	     Intent i = new Intent(view.getContext(), ChooseRoute.class);
	     startActivity(i);
	     
	 }
	public void viewRoute(View view) {
	     Intent i = new Intent(view.getContext(), mainActivity.class);
	     startActivity(i);
	     
	 }
}
