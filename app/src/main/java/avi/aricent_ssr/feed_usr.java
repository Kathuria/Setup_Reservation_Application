package avi.aricent_ssr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class feed_usr extends Activity {
 
	 RatingBar ratingBar;
	 Button btnSubmit,reset;
	 EditText text;
	 String rate,feed;
	 TextView txtRatingValue;
	 FeedbackAdapter FeedbackAdapter;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_usr);
		
		addListenerOnButton();
		addListenerOnRatingBar();
		
		FeedbackAdapter=new FeedbackAdapter(this);
		FeedbackAdapter=FeedbackAdapter.open();
		
		// To focus on rating bar
		ratingBar.setFocusable(true);
		ratingBar.setFocusableInTouchMode(true);
		ratingBar.requestFocus();
	}
	 
	public void addListenerOnRatingBar() {
		
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		txtRatingValue = (TextView) findViewById(R.id.textView2);
	 
		//if rating value is changed,display the current rating value in the result (textview) automatically
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,boolean fromUser) {
	 
				txtRatingValue.setText(String.valueOf(rating));
				rate=String.valueOf(rating);
			}
		});
	  }
	 
		  public void addListenerOnButton() {
			ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
			btnSubmit = (Button) findViewById(R.id.buttonfeed);
			reset = (Button) findViewById(R.id.reset_feed);
			text = (EditText) findViewById(R.id.userfeed);
		 
			btnSubmit.setOnClickListener(new OnClickListener() {
		 
				@Override
				public void onClick(View v) {
					feed = text.getText().toString();
					if(feed.equals("")){
						Toast.makeText(getApplicationContext(), "Please write something in feed", Toast.LENGTH_LONG).show();
						text.requestFocus();
					}
					else{
					long row = FeedbackAdapter.insertEntry(rate, feed);
						if (row > 0) {
							// Save the Data in Database
						    Toast.makeText(getApplicationContext(), "Thanks for your valueble feedback!!", Toast.LENGTH_LONG).show();
						    feed_usr.this.finish();
						} 
						else {
							Toast.makeText(getApplicationContext(),"Please rate us", Toast.LENGTH_LONG).show();
							ratingBar.setFocusable(true);
							ratingBar.setFocusableInTouchMode(true);
							ratingBar.requestFocus();
						}
					}
				}
		 
			});
			
			reset.setOnClickListener(new OnClickListener() {
				@Override
	 	        public void onClick(View v) {
					text.setText("");
					txtRatingValue.setText("");
					ratingBar.setRating(0.0f);
					ratingBar.setFocusable(true);
					ratingBar.setFocusableInTouchMode(true);
					ratingBar.requestFocus();             		
	 	        }
	 	    });
		 }
}	