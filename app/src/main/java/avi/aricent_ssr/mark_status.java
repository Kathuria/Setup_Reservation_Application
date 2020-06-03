package avi.aricent_ssr;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class mark_status extends Activity {
	
	Spinner spin1,spin2;
	TextView stat;
	String st1,st2,lb1;
	Button bt1;
	DatabaseHandler_usr DatabaseHandler_usr;
	
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark_status);
		spin1=(Spinner)findViewById(R.id.Spinfal1);
		spin2=(Spinner)findViewById(R.id.Spinfal2);
		stat=(TextView)findViewById(R.id.status);
		bt1=(Button) findViewById(R.id.markbutton);
		DatabaseHandler_usr=new DatabaseHandler_usr(this);
		loadMarkSpinnerData();
		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
  			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
  				st1=spin1.getSelectedItem().toString();
  				 MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
  				    lb1 = db.getStatusData(st1);
  	  				stat.setText(lb1);
  			}
  	 
  			public void onNothingSelected(AdapterView<?> arg0) {
  			}
  		});
		
		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
  			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
  				st2=spin2.getSelectedItem().toString();
  			}
  	 
  			public void onNothingSelected(AdapterView<?> arg0) {
  			}
  		});
	
        bt1.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	    DatabaseHandler_usr.markSetup(st1,st2);
					Toast.makeText(getApplicationContext(), "Marked Successfully!!", Toast.LENGTH_LONG).show();
		    	    mark_status.this.finish();
		     }
		 });
	}
	
	private void loadMarkSpinnerData(){
   	 MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
       	 List<String> labels = db.getAllSetup();  

   	// Creating adapter for spinner  
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
   
        // Drop down layout style - list view with radio button  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
   
        // attaching data adapter to spinner  
        spin1.setAdapter(dataAdapter);
   }
}
