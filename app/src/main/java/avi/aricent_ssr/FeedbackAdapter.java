package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FeedbackAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"Feedback"+"( " +"ID"+" integer primary key autoincrement,"+ "RATING text NOT NULL,FEED text); ";
		// Variable to hold the database instance
		public SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  FeedbackAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  FeedbackAdapter open() throws SQLException 
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

		public long insertEntry(String rate,String feed)
		{
			long row = 0;
			try {
				ContentValues newValues = new ContentValues();
				// Assign values for each row.
				newValues.put("RATING", rate);
				newValues.put("FEED",feed);
				// Insert the row into your table
				row = db.insert("Feedback", null, newValues);
			} 
			catch (Exception e) {
			}
			return row;
		}		
}