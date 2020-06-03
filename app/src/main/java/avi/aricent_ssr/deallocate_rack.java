package avi.aricent_ssr;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class deallocate_rack extends Activity {
	
	// Spinner element
    Spinner spinner;
    Button btn;
    String text;
    DeallocateDatabaseHandler DeallocateDatabaseHandler;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.deallocate_rack);
 
        // Spinner element
        spinner = (Spinner) findViewById(R.id.Spinnerdel);
        btn = (Button) findViewById(R.id.deallocatebutton);
        loadSpinnerData();
        
        DeallocateDatabaseHandler=new DeallocateDatabaseHandler(this);
       
		
       // final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	text = spinner.getSelectedItem().toString();

		    	    if(text=="Select Setup"){
		    	    	Toast.makeText(getApplicationContext(), "No Rack is Booked!!", Toast.LENGTH_LONG).show();
		    	    }
		    	    else{
		    	    	 DeallocateDatabaseHandler.delete(text);
		    	    	 Toast.makeText(getApplicationContext(), "Rack deallocated Successfully!!", Toast.LENGTH_LONG).show();
		    	    	 deallocate_rack.this.finish();
		    	    }
		      // Log.d("1", text);
		     }
		 });
    }
 
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
    	 DeallocateDatabaseHandler db = new  DeallocateDatabaseHandler(getApplicationContext());
 
        List<String> labels = db.getAllLabel();  
        if(labels.isEmpty())
        {
        	ArrayList<String> d = new ArrayList<String>();
        	d.add("Select Setup");
        	// Creating adapter for spinner  
            ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d);  
       
            // Drop down layout style - list view with radio button  
            data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
       
            // attaching data adapter to spinner  
            spinner.setAdapter(data); 
        }
        else{
   
        // Creating adapter for spinner  
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
   
        // Drop down layout style - list view with radio button  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
   
        // attaching data adapter to spinner  
        spinner.setAdapter(dataAdapter); 
    	}
    }
}	