package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class delhandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "login.db";
    // Labels table name
    private static final String TABLE_LABELS = "LOGIN";
    private static final String SETUP_TABLE = "SETUPBOOK";
    private static final String SETUP_deletion = "SETUPLIST";
    //private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "USERNAME";
    private static final String SETUP_NAME = "STATUS";
    private static final String EMP_NAME = "EMP_USERID";
    public  SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    
    public delhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	public  delhandler open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
       
    }
  
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
       
    }
    
 // Adding new contact
    void addContact(MainGetSet contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name

        db.close(); // Closing database connection
    }

    /**
     * Inserting new lable into lables table
     * */
    public void insertLabel(String label){
    }
     
    /**
     * Getting all labels
     * returns list of labels
     * */  

    public List<String> getAllLabel(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS;  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(1));//adding 2nd column data  
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }  
    
    public void delete(String text) {
    	 try {
    		 SQLiteDatabase db = this.getWritableDatabase(); 
    		 
    		 db.execSQL("DELETE FROM "+TABLE_LABELS+" WHERE "+KEY_NAME+"='"+text+"'");
    		 
    		 updateSetupListAfterUserDel(text);
    		 updateSetupBookAfterUserDel(text);
 			} 
    	 catch (Exception e) {
 			}
    }
    
    public void delete_setup(String text) {
   	 try {
   		 SQLiteDatabase db = this.getWritableDatabase(); 
   		 
   		 db.execSQL("DELETE FROM "+ SETUP_deletion +" WHERE NAME ='"+ text +"'");
			} 
   	 catch (Exception e) {
			}		
   }
    
    public void updateSetupListAfterUserDel(String eName) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues1 = new ContentValues();
			String a = "FREE";
		    newValues1.put("STATUS",a);
			String b = "Booked By: ";
			db.update("SETUPLIST", newValues1, SETUP_NAME+" ='"+ b+eName +"'", null);
			
			db.close();
			} 
		catch (Exception e) {
			}
	}
    
    public void updateSetupBookAfterUserDel(String eName) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL("DELETE FROM "+SETUP_TABLE+" WHERE "+EMP_NAME+"='"+eName+"'");
			} 
		catch (Exception e) {
			}
	}
    
    public List<String> getsetupLabel(){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
        String selectQuery = "SELECT * FROM " + SETUP_deletion;  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(1));//adding 2nd column data  
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
   
        // returning lables  
        return list;  
    }         
}