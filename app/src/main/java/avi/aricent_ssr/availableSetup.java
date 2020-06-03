package avi.aricent_ssr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class availableSetup extends Activity {
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, log, setup_name;
	 TextView tvas;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.availablesetup);
		
		  db = new MainDatabaseHandler(this);
		  tvas= (TextView) findViewById(R.id.tvas);
		  listView = (ListView) findViewById(R.id.availSetupListView);
		//method for getting free setup list from database
	     displayListView();

	      listView.setOnItemClickListener(new OnItemClickListener() {
	 	   @Override
	 	   public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
	 	   // Get the cursor, positioned to the corresponding row in the result set
	 	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
	 	 	   
	 	   MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
		 
		 	text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
		 	setup_name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
		 	//getting details of setup
		 	log = db.getDetails(text);
		 	  	   
	      	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set title
				alertDialogBuilder.setTitle("Setup Details");
	
				// set dialog message
				alertDialogBuilder.setMessage(log).setCancelable(false)
				.setPositiveButton("Book",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
							Intent intent = new Intent(context,book_setup.class);
							intent.putExtra("setup_name", setup_name);
							startActivity(intent);
							availableSetup.this.finish();
						   }
				     })
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
		
		Cursor cursor = db.availableSetups();
		if(cursor != null && cursor.moveToFirst()){
		String[] columns = new String[] {MainDatabaseHandler.KEY_ID,MainDatabaseHandler.KEY_NAME,MainDatabaseHandler.KEY_STATUS};
		
		 int[] to = new int[] { R.id.sidAV,R.id.nameAV,R.id.statusAV};
		
		 dataAdapter = new SimpleCursorAdapter(this, R.layout.avail_list_info, cursor, columns, to,0);
		 
		  listView.setAdapter(dataAdapter);
		}
		else{
			tvas.setText("No setup is available");
		} 
	}
}