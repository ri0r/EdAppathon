package we.kill.the.batman.teamnine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EdAppathonActivity extends Activity {
    /** Called when the activity is first created. */
	private static String MSG_TO_SAY = "MSG_TO_SAY";
	private String msgToSay;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello Austin");

        setContentView(tv);
        setContentView(R.layout.main);
        msgToSay = "Hello Batman, time to die!";
        Intent i = new Intent(EdAppathonActivity.this, TTSService.class);
        i.putExtra(MSG_TO_SAY, msgToSay);
        startActivity(i);

    }
    
 
}