package avi.aricent_ssr;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class delete_setup extends Activity {
	
	 Spinner spinner;
	    Button btn;
	    String text="";
	    int cnt;
	    delhandler delhandler;
	    
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_setup);
		
		spinner = (Spinner) findViewById(R.id.Spin_set);
        btn = (Button) findViewById(R.id.del_set);
        loadSpinnerData();
        delhandler=new delhandler(this);
        final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
		   
        	public void onClick(View v) {
		    	cnt=spinner.getSelectedItemPosition();
		    	if(cnt>=0){
			    	text = spinner.getSelectedItem().toString();
		    	}
		    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					// set title
					alertDialogBuilder.setTitle("Confirm this");
					// set dialog message
					alertDialogBuilder
						.setMessage("Are u sure!!!!")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								if(cnt==-1)
								{
									Toast.makeText(getApplicationContext(), "No setup exists", Toast.LENGTH_LONG).show();
								}
								else
								{
									delhandler.delete_setup(text);
									Toast.makeText(getApplicationContext(), "Setup Deleted Successfully!!", Toast.LENGTH_LONG).show();
							        delete_setup.this.finish();
								}
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						});
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
					}
		      // Log.d("1", text);  
		 });
    }
 
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        delhandler db = new delhandler(getApplicationContext());
         
        List<String> labels = db.getsetupLabel();  
   
        // Creating adapter for spinner  
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
   
        // Drop down layout style - list view with radio button  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
   
        // attaching data adapter to spinner  
        spinner.setAdapter(dataAdapter); 
	}
}