package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class bookSetupAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		int cnt;
		private static final String DATABASE_TABLE = "SETUPBOOK";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"SETUPBOOK"+"( " +"_id"+" integer primary key autoincrement,"+ "NAME  text NOT NULL,MANAGER text, TEAM text, FROM_date text,TO_date text,SLOT text, EMP_USERID text, OTHER_DETAILS text); ";
		
		static final String SETUP_TABLE_CREATE = "create table "+"SETUPLIST"+"( " +"_id"+" text primary key ,"+ "NAME  text NOT NULL,Serial_no text DEFAULT 'NA',Acq_date text DEFAULT 'NA',Purchased_loan text DEFAULT 'NA',Manufacturer text DEFAULT 'NA',Owner text DEFAULT 'NA', HARDWARE_2G text DEFAULT 'NO', HARDWARE_3G text DEFAULT 'NO', HARDWARE_LTE text DEFAULT 'NO', RFM text DEFAULT 'NO',STATUS text DEFAULT 'FREE', Other_detail text DEFAULT 'NA'); ";
		
		static final String TABLE_Faulty = "create table "+"FAULTY"+"( " +"_id"+" integer primary key autoincrement,"+ "Type  text NOT NULL,Name text, pcode text, sno text,ver text, pdesc text, setup text UNIQUE, Status text DEFAULT '1'); ";
		
		// Variable to hold the database instance
		public SQLiteDatabase ddb;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		public  bookSetupAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  bookSetupAdapter open() throws SQLException 
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

		//-----------------------Method for setup booking at admin side---------------------------//

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

				 MainDatabaseHandler c = new MainDatabaseHandler(context);
				 c.update(text,eName);
			} 
			catch (Exception e) {
			}
			return row;
		}
		
		public long addset(String name,String serial,String dat,String loan,String manuf,String own,String oth)
		{
			MainDatabaseHandler c = new MainDatabaseHandler(context);
			cnt = c.insertval();
			String str = String.valueOf(cnt);
			Log.d("------------->>>",str);
			cnt++;
			long row = 0;
			try {
				ContentValues newValues = new ContentValues();
				newValues.put("_id", "AR"+cnt);
				newValues.put("NAME", name);
				newValues.put("Serial_no", serial);
				newValues.put("Acq_date", dat);
				newValues.put("Purchased_loan", loan);
				newValues.put("Manufacturer", manuf);
				newValues.put("Owner", own);
				newValues.put("Other_detail", oth);		
		
				row = ddb.insert("SETUPLIST", null, newValues);
			} 
			catch (Exception e) {
			}
			return row;
		}
		
		//-----------------------------Method for edit booking in admin side------------------------//
		
		public long updateSetup(String text, String text1, String text2,String text3, String from, String to, String slot, String eName, String other) {		
			long row = 0;		
			try {				
			ContentValues newValues = new ContentValues();		
			newValues.put("FROM_date", from);		
			newValues.put("TO_date", to);		
			newValues.put("SLOT", slot);	
			newValues.put("OTHER_DETAILS", other);
			// Insert the row into your table		
			if(text2==null)		
		    row = ddb.update(DATABASE_TABLE, newValues, (DATA_NAME+" ='"+ text +"' AND FROM_date ='"+ text1 +"' AND SLOT ='"+ text3 +"'"), null);			
			else		
			row = ddb.update(DATABASE_TABLE, newValues, (DATA_NAME+" ='"+ text +"' AND FROM_date ='"+ text1 +"' AND TO_date ='"+ text2 +"' AND SLOT ='"+ text3 +"'"), null);		
					
			} 
			catch (Exception e) {			
				e.printStackTrace();		
			}		
			return row;		
		}	
}