package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class bookSetupAdapter_usr 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		public static final String FROM_DATE = "FROM_date";		
		public static final String TO_DATE = "TO_date";		
		public static final String SLOT = "SLOT";
		
		private static final String DATABASE_TABLE = "SETUPBOOK";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"SETUPBOOK"+"( " +"_id"+" integer primary key autoincrement,"+ "NAME  text NOT NULL,MANAGER text, TEAM text, FROM_date text,TO_date text,SLOT text, EMP_USERID text, OTHER_DETAILS text); ";
		// Variable to hold the database instance
		public SQLiteDatabase ddb;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		public  bookSetupAdapter_usr(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  bookSetupAdapter_usr open() throws SQLException 
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

		//------method for book setup at user side---//
		public long storeSetup(String text,String mName,String tName,String from,String to,String slot,String eName,String other)
		{	
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			newValues.put("NAME", text);
			newValues.put("MANAGER", mName);
			newValues.put("TEAM", tName);
			newValues.put("FROM_date", from);
			newValues.put("TO_date", to);
            newValues.put("SLOT", slot);
			newValues.put("EMP_USERID", eName);		
			newValues.put("OTHER_DETAILS", other);
			
			row = ddb.insert("SETUPBOOK", null, newValues);
			Log.v("Store","Setup");
			MainDatabaseHandler c = new MainDatabaseHandler(context);
			 c.update(text,eName);
			} catch (Exception e) {
			
			}
			return row;
		}
		
		
		public long insertfaulty(String st1, String st2, String st3, String st4,String st5, String st6, String st7) {
			long row = 0;
			try {
				ContentValues newValues = new ContentValues();
				newValues.put("Type", st1);
				newValues.put("Name", st2);
				newValues.put("pcode", st3);
				newValues.put("sno", st4);
				newValues.put("ver", st5);
				newValues.put("pdesc", st6);
				newValues.put("setup", st7);
				
				row = ddb.insert("FAULTY", null, newValues);
			} 
			catch (Exception e) {
			}
			return row;
		}  

		//-------method for edit booking at user side-----//
		
		public long updateSetup(String text, String text1,String text2,String text3,String from, String to,String slot,String ename,String other) {
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			newValues.put("FROM_date", from);
			newValues.put("TO_date", to);
			newValues.put("SLOT", slot);
			newValues.put("OTHER_DETAILS", other);
			// Insert the row into your table
			if(text2==null)
		    row = ddb.update(DATABASE_TABLE, newValues, (DATA_NAME+" ='"+ text +"' AND "+FROM_DATE+" ='"+ text1 +"' AND "+SLOT+" ='"+ text3 +"'"), null);	
			else
			row = ddb.update(DATABASE_TABLE, newValues, (DATA_NAME+" ='"+ text +"' AND "+FROM_DATE+" ='"+ text1 +"' AND "+TO_DATE+" ='"+ text2 +"' AND "+SLOT+" ='"+ text3 +"'"), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return row;
		}		
}
