package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler_usr extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "login.db";
    // Labels table name
    private static final String TABLE_LABELS = "SETUPBOOK";
    static final String TABLE_LOGIN = "LOGIN";
   // private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String U_NAME = "EMP_USERID";
    private static final String SETUP_TABLE = "SETUPLIST";
    private static final String SETUP_NAME = "NAME";
    private static final String Faulty_NAME = "FAULTY";
    public  SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    
    public DatabaseHandler_usr(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	public  DatabaseHandler_usr open() throws SQLException 
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
    void addContact(DataBaseGetSet contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name

        db.close(); // Closing database connection
    }
    

    /**
     * Inserting new label into labels table
     * */
    public void insertLabel(String label){
    }
     
    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<DataBaseGetSet> getAllLabels(String uname, String setup){
        List<DataBaseGetSet> labels = new ArrayList<DataBaseGetSet>();
         
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+U_NAME+"='"+uname+"' AND "+SETUP_NAME+"='"+setup+"'";
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataBaseGetSet contact = new DataBaseGetSet();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setFrom(cursor.getString(4));
                contact.setTo(cursor.getString(5));
                
                // Adding contact to list
                contact.getID();
                contact.getName();
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
    
    public List<String> getAllLabel(String usname){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+U_NAME+"='"+usname+"'";  
   
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
    
    public List<String> getAllEmpName(String ename){  
        List<String> list = new ArrayList<String>();  
   
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+KEY_NAME+"='"+ename+"'";  
   
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                list.add(cursor.getString(6));//adding 2nd column data  
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return list;  
    }

    //----------method for share the setup at user side-----//
      
    public long delete(String setup_name, String from, String to, String slot) {
   	 long row = 0;
   	 try {
   		SQLiteDatabase db = this.getWritableDatabase();
   		ContentValues newValues1 = new ContentValues();		
   		String aa = "SHARED";		 
	    newValues1.put("STATUS",aa);
		 
		 row = db.update("SETUPLIST",newValues1, (KEY_NAME+"='"+setup_name+"'"), null);
			} 
   	 catch (Exception e) {
				e.printStackTrace();
			}
			return row;
   }

	public void updateDel(String text) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues1 = new ContentValues();
			String a = "FREE";
		    newValues1.put("STATUS",a);
			
			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ text +"'", null);
			
			db.close();
			} catch (Exception e) {
			}
	}

	public String getData(String text) {
		
		  String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);			//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(4);			//adding 2nd column data  
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

	public List<String> load() {
		List<String> list = new ArrayList<String>();  
		   
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;  
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

	public List<String> getAllSetup(String username) {

		List<String> list = new ArrayList<String>();  
		   
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+ U_NAME + " ='"+username+"'";  
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

	public void markSetup(String st1, String st2) {
		try {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues1 = new ContentValues();
		    newValues1.put("STATUS",st2);
			
			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ st1 +"'", null);
			
			db.close();
			} 
			catch (Exception e) {
			}
	}


	public long insertfaulty(String st1, String st2, String st3, String st4,String st5, String st6, String st7, String st8) {
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
			newValues.put("Status", st8);
	
			row = db.insert(Faulty_NAME, null, newValues);

		} catch (Exception e) {
		}
		return row;
	}  

	//-------method for cancel setup at user side----//
	public long deleteC(String setup_name, String from, String to, String slot) {
	   	 long row = 0;
	   	 int a = 0;
	   	 try {
	   		 SQLiteDatabase db = this.getWritableDatabase(); 
	   		 if(to==null)
	   		 row = db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND SLOT ='"+slot+"'"), null);
	   		
	   		 else
	   		 row = db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND TO_date ='"+to+"' AND SLOT='"+slot+"'"), null);
	   	   			
	   		 ContentValues newValues1 = new ContentValues();
	   		 String selectQuery = "SELECT * FROM SETUPBOOK";
	   	    	
	   	        Cursor cursor = db.rawQuery(selectQuery, null);
	   	        if (cursor.moveToFirst()) {  
	   	            do {  
	   	            	String h = "";
	   	            	h=cursor.getString(1);
	   	            	if(h.equals(setup_name)){
	   	            		a++;
	   	                }
	   	            	else
	   	            		a=0;
	   	            }while (cursor.moveToNext());  
	   	        }  
	   		 
	   	        if(a>0){
	   			}
	   			else{
		   			String aa = "FREE";
		   		    newValues1.put("STATUS",aa);
		   			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ setup_name +"'", null);
	   			}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return row;
	   }

	      //--------------------Method for cancel setup in admin side---------------------------//

	public long deleteAdminC(String setup_name, String from,String to, String slot, String username) {
		 long row = 0;
    	 int a = 0;
    	 try {
    		 SQLiteDatabase db = this.getWritableDatabase(); 
    		 if(to==null)
    		 row = db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND SLOT ='"+slot+"' AND EMP_USERID = '"+username+"'"), null);
    		
    		 else
    		 row = db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND TO_date ='"+to+"' AND SLOT='"+slot+"' AND EMP_USERID = '"+username+"'"), null);
    	   			
    		 ContentValues newValues1 = new ContentValues();
    		 String selectQuery = "SELECT * FROM SETUPBOOK";
    	    	
    	        Cursor cursor = db.rawQuery(selectQuery, null);
    	        if (cursor.moveToFirst()) {  
    	            do {  
    	            	String h = "";
    	            	h=cursor.getString(1);
    	            	if(h.equals(setup_name)){
    	            		a++;
    	                }
    	            	else
    	            		a=0;
    	            }while (cursor.moveToNext());  
    	        }  
    		 
    	        if(a>0){
    			}
    			else{
	    			String aa = "FREE";
	    		    newValues1.put("STATUS",aa);
	    			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ setup_name +"'", null);
    			}
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 			return row;
	}  

           //------------------Method for share setup in admin side-----------------//

	public long deleteAdmin(String setup_name, String from,String to, String slot, String username) {
		 long row = 0;
    	 try {
    		 SQLiteDatabase db = this.getWritableDatabase(); 
    		 ContentValues newValues1 = new ContentValues();		
    		 String aa = "SHARED";		
    		 newValues1.put("STATUS",aa);		
    		 
    		 row = db.update("SETUPLIST", newValues1, (KEY_NAME+"='"+setup_name+"'"), null);
 			} 
    	 catch (Exception e) {
 				e.printStackTrace();
 			}
 			return row;
	}    
}