package avi.aricent_ssr;

import javax.mail.Session;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Created to give user the power to report any bug related to app and will be shown to user to modify it.

public class report_bug extends Activity implements OnClickListener {
	 Session session = null;
	 Context context = null;
	 Button btnSubmit,reset;
	 EditText text1;
	 String str1,nname;
	 ReportAdapter repoadp;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_bug);
		context = this;
		repoadp= new ReportAdapter(this);
		repoadp= repoadp.open();
		Button login = (Button) findViewById(R.id.buttonrep);
		reset = (Button) findViewById(R.id.reset_bug);
		text1 = (EditText) findViewById(R.id.userrep);
		login.setOnClickListener(this);
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
				text1.setText("");
				text1.requestFocus();     		
 	        }
 	    });
	}
				@Override
				public void onClick(View v) {
					str1 = text1.getText().toString();
					  if(str1.equals("")){
						  Toast.makeText(getApplicationContext(), "Please enter bug details", Toast.LENGTH_SHORT).show();
						  text1.requestFocus();
					  }
					  else
					  {		  
						  SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
						  nname=sharedpreferences.getString("nameKey", "Couldn't load");
						  long row = repoadp.insertEntry(nname, str1);
						  if (row > 0) {
							// Save the Data in Database
						    Toast.makeText(getApplicationContext(), "Your bug has been reported. Thanks we'll look at the issue.", Toast.LENGTH_LONG).show();
						    	report_bug.this.finish();
						    }  
						else 
							Toast.makeText(getApplicationContext(),"Sorry!! please try again ", Toast.LENGTH_LONG).show();
					  }
				}
}