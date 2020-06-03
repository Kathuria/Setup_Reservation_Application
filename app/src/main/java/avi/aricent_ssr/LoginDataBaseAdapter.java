package avi.aricent_ssr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final String TABLE_LOGIN = "LOGIN";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// SQL Statement to create a new database.
		static final String DATABASE_CREATE = "create table "+"LOGIN"+"( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text NOT NULL UNIQUE,FNAME text,LNAME text,PASSWORD text,PHONE integer UNIQUE,NOTIFICATIONS text); ";
		// Variable to hold the database instance
		public  SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  LoginDataBaseAdapter open() throws SQLException 
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

		public long insertEntry(String userName,String password,String phone,int notify)
		{
			long row = 0;
			try {
			ContentValues newValues = new ContentValues();
			// Assign values for each row.
			String[] temp = userName.split("@");
			String[] name= temp[0].split("\\.");
			if(temp[0].contains("."))
			{
				String fname=makeProper(name[0]);
				String lname=makeProper(name[1]); 
				String[] nlname=lname.split("\\d");
				newValues.put("FNAME", fname);
				newValues.put("LNAME", nlname[0]);
			}
			else
			{
				String fname=makeProper(temp[0]);
				newValues.put("FNAME", fname);
			}
			newValues.put("USERNAME", userName);
			newValues.put("PASSWORD",password);
			newValues.put("PHONE",phone);
			newValues.put("PHONE",phone);
			newValues.put("NOTIFICATIONS",notify);
			// Insert the row into your table
			row = db.insert("LOGIN", null, newValues);
			} 
			catch (Exception e) {
			}
			return row;
		}
		public int deleteEntry(String UserName)
		{
		    String where="USERNAME=?";
		    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
	        return numberOFEntriesDeleted;
		}	
		public String getSinlgeEntry(String userName)
		{
			Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NULL";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
			cursor.close();
			return password;				
		}
		public boolean getNotifEntry(String userName)
		{
			Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return false;
	        }
		    cursor.moveToFirst();
			String value= cursor.getString(cursor.getColumnIndex("NOTIFICATIONS"));
			int notif = Integer.parseInt(value);
			cursor.close();
			if(notif==1)
				return true;
			else
				return false;
		}
		
		public void  updateEntry(String userName,String password)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("USERNAME", userName);
			updatedValues.put("PASSWORD",password);
	        String where="USERNAME = ?";
		    db.update("LOGIN",updatedValues, where, new String[]{userName});			   
		}
		
		public void  updateNotif(String userName,String notif)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("NOTIFICATIONS", notif);
	        String where="USERNAME = ?";
		    db.update("LOGIN",updatedValues, where, new String[]{userName});			   
		}
		
		public String makeProper(String theString) throws java.io.IOException
		{  
		    java.io.StringReader in = new java.io.StringReader(theString.toLowerCase());  
		     boolean precededBySpace = true;  
		     StringBuffer properCase = new StringBuffer();      
		        while(true) {        
		        int i = in.read();  
		          if (i == -1)  break;        
		            char c = (char)i;  
		            if (c == ' ' || c == '"' || c == '(' || c == '.' || c == '/' || c == '\\' || c == ',') {  
		              properCase.append(c);  
		              precededBySpace = true;  
		           } else {  
		              if (precededBySpace) {   
		             properCase.append(Character.toUpperCase(c));  
		           } else {   
		                 properCase.append(c);   
		           }  
		           precededBySpace = false;  
		        }  
		        }  
		    return properCase.toString();      
		}
}