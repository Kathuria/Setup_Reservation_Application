package avi.aricent_ssr;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

// Receive feedback from users and display as a list

public class feed_adm extends Activity {
	bookSetupAdapter FeedbackAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_adm);
		TextView tv = (TextView) findViewById(R.id.feedadm);
		FeedHandler db = new FeedHandler(getApplicationContext());
        // Spinner Drop down elements
		 List<FeedGetSet> labels = db.getAllLabels();  
		 String log = "";

		 if(labels.isEmpty()){
	    	   tv.setText("No feedback from users");
	       }
		 else{
	        for (FeedGetSet cn : labels) {
	        	log = log + "  Rating: "+cn.getRate()+"\n  Feedback: " + cn.getFeed()+"\n\n\n";
	            // Writing Contacts to log
	        	//  Log.d("Name: ", log);
	        }
	        tv.setText(log);
		 }
	}
}	