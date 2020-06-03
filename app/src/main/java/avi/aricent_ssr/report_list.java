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

// All bugs reported by user displayed in this list.

public class report_list extends Activity {
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, log, name;
	 TextView tvrl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_list);
		  db = new MainDatabaseHandler(this);
		  listView = (ListView) findViewById(R.id.reportListView);
		  tvrl= (TextView) findViewById(R.id.tvrl);
		  //db.open();
	     displayListView();

	      listView.setOnItemClickListener(new OnItemClickListener() {
		   @Override
		   public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
			   // Get the cursor, positioned to the corresponding row in the result set
			   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			   
			   MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
			 	text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
			 	name = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
			 	log = db.getReportDetails(text);

		     	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set title
				alertDialogBuilder.setTitle("Report by: "+name);
	
				// set dialog message
				alertDialogBuilder
					.setMessage(log)
					.setCancelable(false)
					.setNegativeButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
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

	private void displayListView() {
		Cursor cursor = db.report_bugs();
	
		if(cursor != null && cursor.moveToFirst()){
		String[] columns = new String[] {
				MainDatabaseHandler.Email,
				MainDatabaseHandler.Rep,
				MainDatabaseHandler.KEY_ID
			  };
		
		 int[] to = new int[] { R.id.fault2re };
		
		 dataAdapter = new SimpleCursorAdapter(this, R.layout.report_list_info, cursor, columns, to,0);
		 
		  listView.setAdapter(dataAdapter);
		  tvrl.setVisibility(View.GONE);
		}
		else{
			tvrl.setVisibility(View.VISIBLE);
			tvrl.setText("No reports are available");
		} 
	}
}