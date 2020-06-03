package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class helpAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"HelpDesk"+"( " +"ID"+" integer primary key autoincrement,"+ "Email text NOT NULL,Activity_type text,Category text,Request_cat text,Network_ele text,Activity_desc text); ";
		// Variable to hold the database instance
		public SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  helpAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  helpAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}

		public void close() 
		{
			db.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}

		public long insertEntry(String mail,String act,String cat,String ract,String nwe,String actdesc)
		{
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("Email", mail);
			newValues.put("Activity_type",act);
			newValues.put("Category",cat);
			newValues.put("Request_cat",ract);
			newValues.put("Network_ele",nwe);
			newValues.put("Activity_desc",actdesc);
			// Insert the row into your table
			row = db.insert("HelpDesk", null, newValues);
			} 
			catch (Exception e) {
			}
			return row;
		}
}