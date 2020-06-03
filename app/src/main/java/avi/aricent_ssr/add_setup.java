package avi.aricent_ssr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// This class will add setup in the database. Only admin got these rights.

public class add_setup extends Activity {
	
	 Button btnSubmit,reset;
	 EditText text1,text2,text3,text4,text5,text6,text7;
	 String str1,str2,str3,str4,str5,str6,str7;
	 bookSetupAdapter  bookSetupAdapter ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_setup);
		
		addListenerOnButton();
		bookSetupAdapter =new bookSetupAdapter (this);
		bookSetupAdapter =bookSetupAdapter.open();			
		// Calling Adapter which contains all insertion, deletion, etc functions
	}

		  public void addListenerOnButton() {
			  
			  // Find id from layout file and cast into respective data-type.
				btnSubmit = (Button) findViewById(R.id.subbtn);				
				reset = (Button) findViewById(R.id.reset_add);
				text1 = (EditText) findViewById(R.id.Name);
				text2 = (EditText) findViewById(R.id.Sr_no);
				text3 = (EditText) findViewById(R.id.Acq_Dat);
				text4 = (EditText) findViewById(R.id.Purchase);
				text5 = (EditText) findViewById(R.id.Manufacturer);
				text6 = (EditText) findViewById(R.id.Owner);
				text7 = (EditText) findViewById(R.id.other);

			btnSubmit.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// All text data will be fetched and cast into string value.
					str1 = text1.getText().toString();
					str2 = text2.getText().toString();
					str3 = text3.getText().toString();
					str4 = text4.getText().toString();
					str5 = text5.getText().toString();
					str6 = text6.getText().toString();
					str7 = text7.getText().toString();
					
					long row = bookSetupAdapter.addset(str1, str2, str3, str4, str5,str6,str7);
					// calling addset function from bookSetupAdapter with the values from layout page passed.
						if (row > 0) {
							//It means data inserted and proceed with next step. 
							//Save the Data in bookSetupAdapter Database
						    Toast.makeText(getApplicationContext(), ""+str1+" setup added Successfully!!!", Toast.LENGTH_LONG).show();
						    add_setup.this.finish();
						} 
						else {
							// Data insertion failed.
							Toast.makeText(getApplicationContext(),"Sorry!! please try again ", Toast.LENGTH_LONG).show();
							text1.requestFocus();
						}
				}
			});
			
			reset.setOnClickListener(new OnClickListener() {
				@Override
	 	        public void onClick(View v) {
					
					// Clearing the value of fields. Remember to clear edit text values.
					text1.setText("");
					text2.setText("");
					text3.setText("");
					text4.setText("");
					text5.setText("");
					text6.setText("");
					text7.setText("");
			
					text1.requestFocus();		// At last, focus will be again comes back to assigned field of page                 		
	 	        }
	 	    });
		 }
}
