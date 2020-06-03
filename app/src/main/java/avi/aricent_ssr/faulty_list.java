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

public class faulty_list extends Activity {
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, log, setup_name;
	 TextView tvfl;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faulty_list);
		
		  db = new MainDatabaseHandler(this);
		  listView = (ListView) findViewById(R.id.faultyListView);
		  tvfl= (TextView) findViewById(R.id.tvfl);
		  //db.open();
	      displayListView();

	      listView.setOnItemClickListener(new OnItemClickListener() {
	 	   @Override
	 	   public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
	 	   // Get the cursor, positioned to the corresponding row in the result set
		 	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
		 	 	   
		 	   MainDatabaseHandler db = new MainDatabaseHandler(getApplicationContext());
			 
			 	text = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
			 	setup_name = cursor.getString(cursor.getColumnIndexOrThrow("setup"));
			 	log = db.getfaultyDetails(text);

		      	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					// set title
					alertDialogBuilder.setTitle("Faulty Hardware Details");
		
					// set dialog message
					alertDialogBuilder
					.setMessage(log)
					.setCancelable(false)
					.setPositiveButton("Can't Repair",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									//update status of setup to 0 and send notification of plz take new hw with the random code
									dialog.cancel();
								}
							  })
					.setNegativeButton("Repaired",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							//update status of setup to 1 and send notification of hw repaired
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

		Cursor cursor = db.faultySetups();
		if(cursor != null && cursor.moveToFirst()){
		String[] columns = new String[] {
				MainDatabaseHandler.CARD,
				MainDatabaseHandler.FAULTY_NAME,
				MainDatabaseHandler.FAULTY_STATUS,
				MainDatabaseHandler.KEY_ID
			  };
		
		 int[] to = new int[] { 
				    R.id.fault1,
				    R.id.fault2,
				    R.id.fault3
				  };
		
		 dataAdapter = new SimpleCursorAdapter(this, R.layout.faulty_list_info, cursor, columns, to,0);
		 
		  listView.setAdapter(dataAdapter);
		  tvfl.setVisibility(View.GONE);
		}
		else{
			tvfl.setVisibility(View.VISIBLE);
			tvfl.setText("No setup is faulty");
		} 
	}
}
