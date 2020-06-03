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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class allSetupList_usr extends Activity {
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, log;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allsetuplist_usr);
		
		  db = new MainDatabaseHandler(this);
		 listView = (ListView) findViewById(R.id.allSetupListViewU);
		 //method for getting all setup list from database
		 displayListView();
	     
	      listView.setOnItemClickListener(new OnItemClickListener() {
  	   @Override
  	   public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
  	   // Get the cursor, positioned to the corresponding row in the result set
  	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
  	  
  	 MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
	 
     // Spinner Drop down elements
  	 text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
  	 //getting details of setup
  	 log = db.getDetails(text);
  
   	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set title
			alertDialogBuilder.setTitle("Setup Details");

			// set dialog message
			alertDialogBuilder
				.setMessage(log)
				.setCancelable(false)
				.setNegativeButton("Close",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close the dialog box and do nothing
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
	
		Cursor cursor = db.allSetups();
	
		String[] columns = new String[] {MainDatabaseHandler.KEY_ID,MainDatabaseHandler.KEY_NAME,MainDatabaseHandler.KEY_STATUS};
	
		int[] to = new int[] { R.id.sidAll,R.id.nameAll,R.id.statusAll};
	
		dataAdapter = new SimpleCursorAdapter(this, R.layout.all_list_info, cursor, columns, to,0);
	
		listView.setAdapter(dataAdapter);
	}
}
