package avi.aricent_ssr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;

public class splash extends Activity  {
	Context context = null;
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            context = this;
	            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	            //getActionBar().hide();
	            setContentView(R.layout.splash); // Your Splash Layout
	            Handler handler = new Handler();
	     // run a thread
	        handler.postDelayed(new Runnable() {
	            public void run() {
	                // make sure we close the splash screen so the user won't come back when it presses back key
	                splash.this.finish();
	                // start the home screen
	                Intent intent = new Intent(context,MainActivity.class);
	    	        startActivity(intent); 
	            }
	        }, 5000); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
	}
}