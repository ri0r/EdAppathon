package we.kill.the.batman.teamnine;

import we.kill.the.batman.teamnine.R;
import we.kill.the.batman.teamnine.services.TTSService;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class TabManager extends TabActivity{
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tablayout);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    Log.d("Tabs", "Tabbing");
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, MessageList.class);
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("artists").setIndicator("RSS",
	                      res.getDrawable(R.drawable.tabiconlayout))
	                  .setContent(intent);
	    Log.d("Tabs", "Tabbing2");
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, MessageList.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("TextToSpeech",
	                      res.getDrawable(R.drawable.tabiconlayout))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    intent = new Intent().setClass(this, MessageList.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("TextToSpeech",
	                      res.getDrawable(R.drawable.tabiconlayout))
	                  .setContent(intent);
	    tabHost.addTab(spec);

//	    intent = new Intent().setClass(this, SongsActivity.class);
//	    spec = tabHost.newTabSpec("songs").setIndicator("Songs",
//	                      res.getDrawable(R.drawable.ic_tab_songs))
//	                  .setContent(intent);
//	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}

}
