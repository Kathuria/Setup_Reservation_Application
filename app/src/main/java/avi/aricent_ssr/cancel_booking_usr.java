package avi.aricent_ssr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class cancel_booking_usr extends Activity {
 
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, details,username,setup_name,from_date,to_date,slot;
     DatabaseHandler_usr DatabaseHandler_usr;
     TextView tvcb;
     @SuppressLint("SimpleDateFormat") @Override
     public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.cancel_booking_usr);
		 
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		username=sharedpreferences.getString("nameKey", "Couldn't load");
		  db = new MainDatabaseHandler(this);
		  
		 listView = (ListView) findViewById(R.id.cancelBookingSetupListView);
		 tvcb= (TextView) findViewById(R.id.tvcb);
		//method for getting booked setup list acc to user rather than current date from database
		 displayListView(username,currentdate);
       
         DatabaseHandler_usr=new DatabaseHandler_usr(this);
       
		 listView.setOnItemClickListener(new OnItemClickListener() {
		 @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
       	   // Get the cursor, positioned to the corresponding row in the result set
       	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
       	  
	       	 db = new MainDatabaseHandler(getApplicationContext());
	       	 
	          // Spinner Drop down elements
	       	text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
	       	setup_name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
	       	from_date = cursor.getString(cursor.getColumnIndexOrThrow("FROM_date"));
	       	to_date = cursor.getString(cursor.getColumnIndexOrThrow("TO_date"));
	       	slot = cursor.getString(cursor.getColumnIndexOrThrow("SLOT"));
	       	   
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle(setup_name);
	
			// set dialog message
			alertDialogBuilder
			.setMessage("Do you want to cancel setup?")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							long row = 0;
							row = DatabaseHandler_usr.deleteC(setup_name,from_date,to_date,slot);
							if(row>0){
							Toast.makeText(getApplicationContext(), "Booking Cancel Successfully!!", Toast.LENGTH_LONG).show();
							cancel_booking_usr.this.finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Booking Cancel Failed!!", Toast.LENGTH_LONG).show();
							}		
						}
					  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		  }
      });
       	  
    }

   //definition of method for display list view of setups
    private void displayListView(String username, String currentdate) {
    	
    	Cursor cursor = null;
    	cursor = db.getEditBookingDetails(username,currentdate);
    	
    	if(cursor != null && cursor.moveToFirst()){
    		
    		String[] columns = new String[] {
        			MainDatabaseHandler.EDIT_NAME,
        			MainDatabaseHandler.EDIT_FROM,
        			MainDatabaseHandler.EDIT_TO,
        			MainDatabaseHandler.EDIT_SLOT
        		  };
        	
        	 int[] to = new int[] { 
        			    R.id.nameDBEUL,
        			    R.id.fromDBEUL,
        			    R.id.toDBEUL,
        			    R.id.slotDBEUL
        			  };
        	
        	 dataAdapter = new SimpleCursorAdapter(this, R.layout.editbooksetuplistinfo_usr, cursor, columns, to,0);
        	
        	  listView.setAdapter(dataAdapter);
    		
    	}
    	else{
    		tvcb.setText("No setup is availale for cancellation");
    	}
    }
}