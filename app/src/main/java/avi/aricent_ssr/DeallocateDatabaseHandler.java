package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DeallocateDatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "login.db";
    // Labels table name
    private static final String TABLE_LABELS = "RackBook";
    private static final String KEY_NAME = "NAME";
    public  SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    
    public DeallocateDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	public  DeallocateDatabaseHandler open() throws SQLException 
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
    public List<MainGetSet> getAllLabels(){
        List<MainGetSet> labels = new ArrayList<MainGetSet>();
         
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS;
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MainGetSet contact = new MainGetSet();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setmName(cursor.getString(2));
                contact.settName(cursor.getString(3));
                contact.setFrom(cursor.getString(4));
                contact.setTo(cursor.getString(5));
                
                // Adding contact to list
                contact.getID();
                contact.getName();
                contact.getmName();
                contact.gettName();
                contact.getFrom();
                contact.getTo();
                labels.add(contact);
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning lables
        return labels;
    }

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
 			} 
    	 catch (Exception e) {
 			}
    }

	public String getData(String text) {
		
		  String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(4);//adding 2nd column data  
	            } while (cursor.moveToNext());  
	        }  
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list;  
	}

	public String getData1(String text) {
		
		 String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(5);//adding 2nd column data  
	            } while (cursor.moveToNext());  
	        }  
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list; 
	}

	public String getData2(String text) {
		
		 String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(2);//adding 2nd column data  
	            } while (cursor.moveToNext());  
	        }  
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list;
	}

	public String getData3(String text) {
		
		 String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(3);//adding 2nd column data  
	            } while (cursor.moveToNext());  
	        }  
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list;
	}
}