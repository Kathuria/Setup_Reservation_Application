package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

// Adapter used to store functions for Contact us page

public class ContactAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"Contact"+"( " +"ID"+" integer primary key autoincrement,"+ "Email text NOT NULL,Name text, Mobile text, Comment text); ";
		// Variable to hold the database instance
		public SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  ContactAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  ContactAdapter open() throws SQLException 
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

		public long insertEntry(String mail,String nam,String mob,String cmnt)
		{
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("Email", mail);
			newValues.put("Name",nam);
			newValues.put("Mobile",mob);
			newValues.put("Comment",cmnt);
			
			// Insert the row into your table
			row = db.insert("Contact", null, newValues);
			}
			catch (Exception e) {
			}
			return row;
		}			
}