package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class allocateRackAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		private static final String DATABASE_TABLE = "RackBook";
		//Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"RackBook"+"( " +"ID"+" integer primary key autoincrement,"+ "NAME  text NOT NULL UNIQUE,MANAGER text, TEAM text, FROM_date text,TO_date text); ";
		
		static final String SETUP_TABLE_CREATE = "create table " + "RackList" + "(" + " NAME text NOT NULL UNIQUE);";
		// Variable to hold the database instance
		public SQLiteDatabase ddb;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  allocateRackAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  allocateRackAdapter open() throws SQLException 
		{
			ddb = dbHelper.getWritableDatabase();
			return this;
		}

		public void close() 
		{
			ddb.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return ddb;
		}

		public long storeSetup(String text,String mName,String tName,String from,String to)
		{	
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			newValues.put("NAME", text);
			newValues.put("MANAGER", mName);
			newValues.put("TEAM", tName);
			newValues.put("FROM_date", from);
			newValues.put("TO_date", to);

			row = ddb.insert("RackBook", null, newValues);
			} catch (Exception e) {
			}
			return row;
		}
		
		public long updateSetup(String text, String mName, String tName, String from, String to) {
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			newValues.put("NAME", text);
			newValues.put("MANAGER", mName);
			newValues.put("TEAM", tName);
			newValues.put("FROM_date", from);
			newValues.put("TO_date", to);
			
			// Insert the row into your table
			row = ddb.update(DATABASE_TABLE, newValues, DATA_NAME+" ='"+ text +"'", null);
			
			} catch (Exception e) {
			}
			return row;		
		}			
}