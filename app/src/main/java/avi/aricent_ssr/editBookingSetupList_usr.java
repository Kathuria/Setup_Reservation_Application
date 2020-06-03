package avi.aricent_ssr;
	 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") public class editBookingSetupList_usr extends Activity {
	 private MainDatabaseHandler db;
	 private SimpleCursorAdapter dataAdapter;
	 ListView listView;
	 final Context context = this;
	 String text, details,username,setup_name,from_date,to_date,slot,otherdetails;
	 TextView tveb;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editbooksetuplist_usr);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		username=sharedpreferences.getString("nameKey", "Couldn't load");
		  db = new MainDatabaseHandler(this);
		
		 listView = (ListView) findViewById(R.id.editBookingSetupListView);
		 tveb = (TextView) findViewById(R.id.tveb);
		//method for getting booked setup list from database for editing
		 displayListView(username,currentdate);

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
		  	//getting details of setup
		  	details = db.getSetupDetails(text);
		  	//getting details of other requirement
		  	otherdetails = db.getOtherDetails(text);
		  	   // Get the state's capital from this row in the database.
		  	   
		  	   //Toast.makeText(getApplicationContext(),
		  	   //  countryCode, Toast.LENGTH_SHORT).show();
		  	 Intent intent = new Intent(context,edit_booking_usr.class);
			    intent.putExtra("setup_name", setup_name);
			    intent.putExtra("details", details);
			    intent.putExtra("from_date", from_date);
			    intent.putExtra("to_date", to_date);
			    intent.putExtra("slot", slot);
			    intent.putExtra("otherdetails", otherdetails);
			    startActivity(intent);
		  	  editBookingSetupList_usr.this.finish(); 
	     	}  	 
  	  });
	}
	//definition of method for display list view of setups
	private void displayListView(String username,String currentdate) {
		
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
			tveb.setText("No setup is available to make changes");
		}
	}
}
