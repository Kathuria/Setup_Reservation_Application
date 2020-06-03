package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

 
public class faulty_setup extends Activity {
	String st1,st2,st3,st4,st5,st6,st7,st8,username;
	Spinner spn1,spn2;
	Button mrbtn,reset;
	Context context = null;
	DatabaseHandler_usr db;
	bookSetupAdapter bookSetupAdapter;
	EditText edt1,edt2,edt3,edt4,edt5;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faulty_setup);
		context = this;
		
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		username=sharedpreferences.getString("nameKey", "Couldn't load");
        // Spinner Drop down elements
		
		mrbtn=(Button)findViewById(R.id.mark_it);
		reset=(Button)findViewById(R.id.reset_fault);
		spn1 = (Spinner) findViewById(R.id.Spinner);
		spn2 = (Spinner) findViewById(R.id.Used_In_Setup);
		edt1 = (EditText) findViewById(R.id.cardname);
		edt2 = (EditText) findViewById(R.id.product_code);
		edt3 = (EditText) findViewById(R.id.Serial_no);
		edt4 = (EditText) findViewById(R.id.Version);
		edt5 = (EditText) findViewById(R.id.problem_description);
		
		db=new DatabaseHandler_usr(this);
		db=db.open();
		loadspinnerdata(username);
	
		bookSetupAdapter=new bookSetupAdapter(this);
 		bookSetupAdapter=bookSetupAdapter.open();
 		
 		
 		// To set spinner focusable.
 		spn1.setFocusable(true);
		spn1.setFocusableInTouchMode(true);
		spn1.requestFocus();

		spn1.setOnItemSelectedListener(new OnItemSelectedListener() {
			 
 			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
 				st1=spn1.getSelectedItem().toString();
 			}
 	 
 			public void onNothingSelected(AdapterView<?> arg0) {
 			}
 		});
		
		mrbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					st2=edt1.getText().toString();
		    		st3=edt2.getText().toString();
		    		st4=edt3.getText().toString();
		    		st5=edt4.getText().toString();
		    		st6=edt5.getText().toString();
		    		st7=spn2.getSelectedItem().toString();
		    		
		    		if(st7.equals("No Booking")){
						  Toast.makeText(getApplicationContext(), "Please Book a setup 1st", Toast.LENGTH_SHORT).show();
							 spn2.setFocusable(true);
							 spn2.setFocusableInTouchMode(true);
							 spn2.requestFocus();
		    		}
		    		else if(st1.equals("Select"))
 		    	    {
	 		    		Toast.makeText(getApplicationContext(),"Choose a Card", Toast.LENGTH_LONG).show();
	 		    		 spn1.setFocusable(true);
	 		    		 spn1.setFocusableInTouchMode(true);
	 		    		 spn1.requestFocus();
 		           }
                else if(st2.equals("")){
						  Toast.makeText(getApplicationContext(), "Enter Name of Card", Toast.LENGTH_SHORT).show();
						  edt1.requestFocus();
					  }
				  else if(st3.equals("")){
						  Toast.makeText(getApplicationContext(), "Enter Product Code", Toast.LENGTH_SHORT).show();
						  edt2.requestFocus();
					  }
				  else if(st4.equals("")){
						  Toast.makeText(getApplicationContext(), "Enter serial Number", Toast.LENGTH_SHORT).show();
						  edt3.requestFocus();}
				  else if(st5.equals("")){
						  Toast.makeText(getApplicationContext(), "Enter version", Toast.LENGTH_SHORT).show();
						  edt4.requestFocus();
					  }
				  else if(st6.equals("")){
						  Toast.makeText(getApplicationContext(), "Enter problem description", Toast.LENGTH_SHORT).show();
						  edt5.requestFocus();
					  }
				  else
				  {		  
 		    		st8="0";
 		    		long row =db.insertfaulty(st1,st2,st3,st4,st5,st6,st7,st8);
 						if (row > 0) {
 							// Save the Data in Database
 						    Toast.makeText(getApplicationContext(), "Hardware Marked Successfully!!", Toast.LENGTH_LONG).show();
					        faulty_setup.this.finish();
 						} 
 						else {
 							Toast.makeText(getApplicationContext(),"Hardware is Already Marked!!", Toast.LENGTH_LONG).show();
 						}
 		    	} 
			}
		}); 
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
				spn1.setSelection(0);
				spn2.setSelection(0);
				edt1.setText("");
				edt2.setText("");
				edt3.setText("");
				edt4.setText("");
				edt5.setText("");
				
				spn1.setFocusable(true);
				spn1.setFocusableInTouchMode(true);
				spn1.requestFocus();                  		
 	        }
 	    });
	}
	
	
	private void loadspinnerdata(String username) {
		
        	 List<String> labels = db.getAllSetup(username);  
         	// Creating adapter for spinner  

        	 if(labels.isEmpty())
             {
             	ArrayList<String> d = new ArrayList<String>();
             	d.add("No Booking");
             	ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d);  
                 
                 // Drop down layout style - list view with radio button  
                 data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
            
                 // attaching data adapter to spinner  
                 spn2.setAdapter(data);
             }
             else
             {
              ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
              // Drop down layout style - list view with radio button  
              dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
         
              // attaching data adapter to spinner  
              spn2.setAdapter(dataAdapter);
             }
	}
}	