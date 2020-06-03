package avi.aricent_ssr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class book_list extends Activity {
	private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, details,username,setup_name,from_date,to_date,slot,detail;
	 TextView tvbl;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list);
		
		  db = new MainDatabaseHandler(this);
		  
		 listView = (ListView) findViewById(R.id.BookedSetupListViewA);
		 tvbl = (TextView) findViewById(R.id.tvblA);
		//method for getting all booked setup list from database 
		 displayListView();
		 
		 listView.setOnItemClickListener(new OnItemClickListener() {
			 @Override
	        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
	       	   // Get the cursor, positioned to the corresponding row in the result set
	       	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
	       	  
	       	 MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
	     	 
	          // Spinner Drop down elements
	       	text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
	       	setup_name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
	       	from_date = cursor.getString(cursor.getColumnIndexOrThrow("FROM_date"));
	       	to_date = cursor.getString(cursor.getColumnIndexOrThrow("TO_date"));
	       	slot = cursor.getString(cursor.getColumnIndexOrThrow("SLOT"));
	      //getting details of booked setup
	       	detail = db.getbookeddetailsAdmin(text);
	       	
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set title
			alertDialogBuilder.setTitle("Setup Details");

			// set dialog message
			alertDialogBuilder
				.setMessage(detail)
				.setCancelable(false)
				.setNegativeButton("Close",new DialogInterface.OnClickListener() {
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
private void displayListView() {
		
		Cursor cursor = null;
		cursor = db.getBookingDetailsAdmin();
		
		if(cursor != null && cursor.moveToFirst()){
		String[] columns = new String[] {
				MainDatabaseHandler.EDIT_NAME,
				MainDatabaseHandler.EDIT_FROM,
				MainDatabaseHandler.EDIT_TO,
				MainDatabaseHandler.EDIT_SLOT,
    			MainDatabaseHandler.EDIT_EMPID
			  };
		
		 int[] to = new int[] { 
				    R.id.nameDBEULA,
				    R.id.fromDBEULA,
				    R.id.toDBEULA,
				    R.id.slotDBEULA,
    			    R.id.empDBEULA
				  };
		
		 dataAdapter = new SimpleCursorAdapter(this, R.layout.editbooksetuplistinfo, cursor, columns, to,0);
		
		  listView.setAdapter(dataAdapter);
		}
		else{
			tvbl.setText("No Setup Is Booked");
		}
	}
}	