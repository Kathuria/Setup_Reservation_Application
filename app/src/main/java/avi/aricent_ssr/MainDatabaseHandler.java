package avi.aricent_ssr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;

import android.util.Log;

public class MainDatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "login.db";
    // Labels table name
    private static final String TABLE_LABELS = "SETUPBOOK";
    private static final String SETUP_TABLE = "SETUPLIST";
    private static final String FAULTY_TABLE = "FAULTY";
    private static final String ADMIN_TABLE = "Admin";
    static final String TABLE_LOGIN = "LOGIN";
    static final String KEY_NAME = "NAME";
    private static final String SETUP_NAME = "NAME";
    private static final String FROM_DATE = "FROM_date";		
	static final String KEY_ID = "_id";
	static final String KEY_STATUS = "STATUS";
	static final String CARD = "Type";
	static final String Email = "Email";
	static final String Rep = "Report";
	static final String FAULTY_NAME = "setup";
	static final String FAULTY_STATUS = "Status";
	static final String EDIT_NAME = "NAME";
	static final String EDIT_FROM= "FROM_date";
	static final String EDIT_TO = "TO_date";
	static final String EDIT_SLOT = "SLOT";
	static final String EDIT_EMPID = "EMP_USERID";
	static final String U_NAME = "EMP_USERID";
    public  SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    int count=10;
    
    public MainDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	public  MainDatabaseHandler open() throws SQLException 
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
                contact.setUserID(cursor.getString(6));
                // Adding contact to list
                contact.getID();
                contact.getName();
                contact.getmName();
                contact.gettName();
                contact.getFrom();
                contact.getTo();
                contact.getUserID();
                labels.add(contact);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        
        // returning lables
        return labels;
    }
    //------------method for get setups from setuplist table whose status are free----///

  public Cursor availableSetups() {

    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("SETUPLIST", new String[] {"_id", "NAME", "STATUS"}, "STATUS = 'FREE'" , null, null, null, null);
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
    	 }

  //----------method for get list of all setups from setuplist table----////
  
    public Cursor allSetups() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("SETUPLIST", new String[] {"_id", "NAME", "STATUS"}, null, null, null, null, null);
    		 
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

    public int insertval(){
    	String selectQuery = "SELECT * FROM SETUPLIST";
    	int a=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {  
            do {  
                a++;
            } while (cursor.moveToNext());  
        }  
     // closing connection
        cursor.close();
        db.close();
        return a;
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
    public void delete(String text) {
    	 try {
    		 SQLiteDatabase db = this.getWritableDatabase(); 
    		 db.execSQL("DELETE FROM "+TABLE_LABELS+" WHERE "+KEY_NAME+"='"+text+"'");
    		 updateDel(text);	
 			} 
    	 catch (Exception e) {
 			}		
    }

	private void updateDel(String text) {
     try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues1 = new ContentValues();
			String a = "FREE";
		    newValues1.put("STATUS",a);
			
			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ text +"'", null);
			db.close();
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
    //-----method for get all register user list----//
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

	public void insert(){
		try {
			String selectQuery = "SELECT  * FROM " + SETUP_TABLE;
			SQLiteDatabase ddb = this.getReadableDatabase();
			Cursor cursor = ddb.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {  
	            do {  
	            	String a = cursor.getString(1);
	            	if(a=="AR1")
	            	{
	            		break;
	            	}
	            } while (cursor.moveToNext());  
	        }
			else{
				String qry1 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,RFM) VALUES ('AR1','City (Talk Family)','YES','YES');";
				String qry2 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G,HARDWARE_LTE,RFM) VALUES ('AR2','CROCKETT BTS','YES','YES','YES','YES');";
				String qry3 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G) VALUES ('AR3','Flexi (Talk Family)','YES','YES');";
				String qry4 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G) VALUES ('AR4','Flexi EDGE','YES');";
				String qry5 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G,RFM) VALUES ('AR5','Flexi Multi BTS','YES','YES','YES');";
				String qry6 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G) VALUES ('AR6','GEMINI BTS','YES');";
				String qry7 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G) VALUES ('AR7','Intra (Talk Family)','YES','YES');";
				String qry8 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G,HARDWARE_LTE,RFM) VALUES ('AR8','MEDUSA BTS','YES','YES','YES','YES');";
				String qry9 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G,HARDWARE_LTE) VALUES ('AR9','MetroSite BTS','YES','YES','YES');";
				String qry10 = "INSERT INTO "+SETUP_TABLE+" (_id,NAME,HARDWARE_2G,HARDWARE_3G,RFM) VALUES ('AR10','MEDUSA','YES','YES','YES');";
				ddb.execSQL(qry1);
				ddb.execSQL(qry2);
				ddb.execSQL(qry3);
				ddb.execSQL(qry4);
				ddb.execSQL(qry5);
				ddb.execSQL(qry6);
				ddb.execSQL(qry7);
				ddb.execSQL(qry8);
				ddb.execSQL(qry9);
				ddb.execSQL(qry10);
		    }
		} 
		catch (Exception e) {
		}
	}

public void admdetail(){
		try {
			String selectQuery = "SELECT  * FROM " + ADMIN_TABLE;
			SQLiteDatabase ddb = this.getReadableDatabase();
			Cursor cursor = ddb.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {  
	            do {  
	            	String a = cursor.getString(1);
	            	if(a=="admin@aricent.com")	//admin email
	            	{
	            		break;
	            	}
	            } while (cursor.moveToNext());  
	        }
			else{
	            		String qry = "INSERT INTO "+ADMIN_TABLE+" (USERNAME,FNAME,PASSWORD,PHONE) VALUES ('admin@aricent.com','admin','abc123','9999999999');";
	            		ddb.execSQL(qry);
	            }	
		}
		catch (Exception e) {
		}
	}

//---------method for getting all setup list from setuplist table---//

	public List<String> getAllSetup() {
		List<String> list = new ArrayList<String>();  

        // Select All Query  
        String selectQuery = "SELECT  * FROM " + SETUP_TABLE;  
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

	public void update(String text, String eName) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues1 = new ContentValues();
			String a = "Booked";
			newValues1.put("STATUS",a);
			
			db.update(SETUP_TABLE, newValues1, SETUP_NAME+" ='"+ text +"'", null);
			Log.v("Update","Status");
			db.close();
			} 
		catch (Exception e) {
			}
	}

	//--------method for get details of particular setup from setuplist table-----//

	public String getDetails(String id) {
		
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + SETUP_TABLE + " WHERE "+ KEY_ID + " ='"+id+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
            	        + cursor.getString(1)+"\n 2G Hardware: " + cursor.getString(7)+"\n 3G Hardware: " 
            	        		+ cursor.getString(8)+" \n LTE Hardware: " + cursor.getString(9)+"\n RFM: " 
            	        + cursor.getString(10)+"\n Status: " + cursor.getString(11); 
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}

	public String getStatusData(String st1) {
		
		 String list="";  
		   
	        // Select All Query  
	        String selectQuery = "SELECT * FROM "+ SETUP_TABLE + " WHERE "+ KEY_NAME + " ='"+st1+"'";  
	   
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	   
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	                list = cursor.getString(11);//adding 2nd column data  
	            } while (cursor.moveToNext());  
	        }  
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list; 
	}

	public String getfaultyDetails(String id1) {
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + FAULTY_TABLE + " WHERE "+ KEY_ID + " ='"+id1+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);		//selectQuery,selectedArguments  
        if (cursor.moveToFirst()) {  
            do {  
            	log = log + " Type: "+cursor.getString(1) +"\n Card Name: " 
            	        + cursor.getString(2)+"\n Product Code: " + cursor.getString(3)+"\n Serial No: " 
            	        		+ cursor.getString(4)+" \n Version: " + cursor.getString(5)+"\n Description: " 
            	        + cursor.getString(6)+"\n Setup: " + cursor.getString(7)+"\n Status: " + cursor.getString(8); 
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}

	public Cursor faultySetups() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("FAULTY", new String[] {"_id","Type", "setup", "Status"}, null, null, null, null, null);
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

	public Cursor report_bugs() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("Report", new String[] {"_id","Email", "Report"}, null, null, null, null, null);
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

	public String getReportDetails(String id2) {
		String log=""; 
        // Select All Query  
        String selectQuery = "SELECT  * FROM Report WHERE "+ KEY_ID + " ='"+id2+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);				//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	log = log +cursor.getString(2); 
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}

	//-------method for get time slot data acc to time from setupbook table at user side----// 

	public List<String> getTimeSlotATT(String text2, String from2) {
		List<String> list = new ArrayList<String>();
	    // Select All Query  
		//Log.d("",from2);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text2+"' AND "+FROM_DATE+"='"+from2+"'";
	        String selectQuery1 = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text2+"'";
	        SQLiteDatabase db = this.getReadableDatabase();  
	        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
	        Cursor cursor1 = db.rawQuery(selectQuery1, null);
	        // looping through all rows and adding to list  
	        if (cursor.moveToFirst()) {  
	            do {  
	            	if(cursor.getString(6)==null){
	                	list.add(null);
	                }
	            	else if(cursor.getString(6)=="FULL DAY"){
	                	list.add(cursor.getString(6));
	                }
	            	else {
	                	list.add(cursor.getString(6));
	                }
	            } while (cursor.moveToNext());  
	        }  
	        if (cursor1.moveToFirst()) {  
	            do {  
	            	try {
	            		Date dateDB = null,dateDB1 = null;
	    	    		
	    	    		if(!(cursor1.getString(4)==null)){
	    	    			Date dateFROM = formatter.parse(from2);
		    	    		dateDB = formatter.parse(cursor1.getString(4));

		    	    		if(dateDB.after(dateFROM)){
			                }
		    	    		
			            	else if(dateDB.before(dateFROM)){
			            		if(!(cursor1.getString(5)==null)){
				            		dateDB1 = formatter.parse(cursor1.getString(5));
				                	if(dateDB1.equals(dateFROM)||dateDB1.after(dateFROM)){
				                		list.add(cursor1.getString(6));
				                	}
				                	else if(dateDB1.before(dateFROM)){
				                	}
			            		}
			            		else{
			            		}
			                  }
	    	    	   	}
	    	    		else{
	    	    		}
	    	    	} 
	            	catch (ParseException e) {
	    	    		e.printStackTrace();
	    	    	}	                
	            } while (cursor1.moveToNext());  
	        } 
	        // closing connection  
	        cursor.close();  
	        db.close();  
	   
	        // returning lables  
	        return list; 
	}
	//-------method for get time slot data acc to date from setupbook table at user side----// 

	public List<String> getTimeSlotATD(String text2, String from2, String to2, long days) {
		List<String> list = new ArrayList<String>();
	        // Select All Query  
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        Calendar c=Calendar.getInstance();
	        try {
				c.setTime(sdf.parse(from2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        for(int i = 1; i<=days;i++){
			        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text2+"' AND "+FROM_DATE+"='"+from2+"'";  
			        String selectQuery1 = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text2+"'";
			        SQLiteDatabase db = this.getReadableDatabase();  
			        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
			        Cursor cursor1 = db.rawQuery(selectQuery1, null);
			        // looping through all rows and adding to list  
			        if (cursor.moveToFirst()) {  
			            do {  
			            	if(cursor.getString(6)==null){
			                	list.add(null); 
			                }
			            	else if(cursor.getString(6)=="FULL DAY"){
			                	list.add("1");
			                }
			            	else {
			                	list.add("0");
			                }
			            } while (cursor.moveToNext());  
			        }  
			        if (cursor1.moveToFirst()) {  
			            do {  
			            	try {
			            		Date dateDB = null,dateDB1 = null;
			    	    		Date dateFROM = sdf.parse(from2);
			    	    		if(!(cursor1.getString(4)==null)){
				    	    		dateDB = sdf.parse(cursor1.getString(4));
				    	    		
				    	    		if(dateDB.after(dateFROM)){
					                }
					            	else if(dateDB.before(dateFROM)){
					            		if(!(cursor1.getString(5)==null)){
						            		dateDB1 = sdf.parse(cursor1.getString(5));
						                	if(dateDB1.equals(dateFROM)||dateDB1.after(dateFROM)){
						                		list.add(cursor1.getString(6));
						                	}
						                	else if(dateDB1.before(dateFROM)){
						                	}
					            		}
					            		else{
					            		}
					                }
			    	    		}
			    	    		else{
			    	    		}
			    	    	}
			            	catch (ParseException e) {
			    	    		e.printStackTrace();
			    	    	}
			            } while (cursor1.moveToNext());  
			        } 
			        // closing connection  
			        cursor.close();  
			        db.close();  
			        
			        c.add(Calendar.DATE, 1);  // number of days to add
			        from2 = sdf.format(c.getTime());
	        }
	        // returning lables
	        return list; 
	}

	public List<String> getTimeSlotAvail(String text, String u) {
		List<String> list = new ArrayList<String>();
        // Select All Query  
        String selectQuery = "SELECT * FROM "+ TABLE_LABELS + " WHERE "+ KEY_NAME + " ='"+text+"' AND "+FROM_DATE+"='"+u+"'";  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {  
            do {  
                
            	if(cursor.getString(6)==null){
                	list.add(null);
                }
            	else {
                	list.add(cursor.getString(6));
                }
                
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
        // returning lables  
        return list; 
	}

//-------method for get list of booked setup rather that current date for canceling/editing process at user side---//
	
	public Cursor getEditBookingDetails(String username, String currentdate) {

        SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT"}, "EMP_USERID = '"+username+"' AND FROM_date != '"+currentdate+"'" , null, null, null, null);
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

//-----method for get details of booked setup for editbookingsetuplist at user side----//
	public String getSetupDetails(String id) {
				String log="";
		        // Select All Query  
		        String selectQuery = "SELECT  * FROM SETUPBOOK WHERE "+ KEY_ID + " ='"+id+"'";;  
		        SQLiteDatabase db = this.getReadableDatabase(); 
		        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
		   
		        if (cursor.moveToFirst()) {  
		            do {  
		            	if(cursor.getString(5)!=null&&cursor.getString(8)!="")
		            	{
		            	log = log + "\nSetup Name: " 
		            	        + cursor.getString(1)+"\nFrom: " + cursor.getString(4)+"\nTo: " 
		            	        		+ cursor.getString(5)+" \nSlot: " + cursor.getString(6)+" \nOther Requirements: " + cursor.getString(8); 
		            	}
		            	else if(cursor.getString(5)==null){
		            		log = log + "\nSetup Name: " 
			            	        + cursor.getString(1)+"\nDate: " + cursor.getString(4)+" \nSlot: " + cursor.getString(6)+" \nOther Requirements: " + cursor.getString(8); 
		            	}
		            	else if(cursor.getString(8)==""){
		            		log = log + "\nSetup Name: " 
			            	        + cursor.getString(1)+"\nFrom: " + cursor.getString(4)+"\nTo: " 
			            	        		+ cursor.getString(5)+" \nSlot: " + cursor.getString(6); 
		            	}
		            	else {
		            		log = log + "\nSetup Name: " 
			            	        + cursor.getString(1)+"\nDate: " + cursor.getString(4)+" \nSlot: " + cursor.getString(6); 
		            	}
		            	break;
		            } while (cursor.moveToNext());  
		        }  
		        // closing connection  
		        cursor.close();  
		        db.close();  
		       
		        // returning lables  
		        return log; 
	}

	//---method for get details of booked setup at user side---//
	public String getbookeddetails(String text) {
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+ KEY_ID + " ='"+text+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	if(cursor.getString(5)==null&&cursor.getString(8)=="")
            	{
            	log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
            	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
            	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n Slot: " + cursor.getString(6); 
            	}
            	else if(cursor.getString(8)==""){
            		log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
                	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
                	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n To Date: " 
                	        + cursor.getString(5)+"\n Slot: " + cursor.getString(6);
            	}
            	else if(cursor.getString(5)==null){
            		log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
                	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
                	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n Slot: " + cursor.getString(6)+" \n Other Requirements: " + cursor.getString(8);
            	}
            	else{
            		log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
                	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
                	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n To Date: " 
                	        + cursor.getString(5)+"\n Slot: " + cursor.getString(6)+" \n Other Requirements: " + cursor.getString(8);
            	}
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log;
	}

	// method for get list of booked setup till current date at user side----///
	public Cursor getfreeBookingDetails(String username, String currentdate) {
	     SQLiteDatabase db = this.getReadableDatabase();
		Cursor mCursor;
    	mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT"}, "EMP_USERID = '"+username+"' AND FROM_date = '"+currentdate+"'" , null, null, null, null);
    		 
    		  if (mCursor != null) {
    			  mCursor.moveToFirst();
    		  }
		return mCursor;
	}

	//--method for get list of booked setups acc to user----//
	public Cursor getBookingDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT"}, "EMP_USERID = '"+username+"'" , null, null, null, null);
    		  if (mCursor != null) {
    			  mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

	//------------method for get booked setup list rather than current date at admin side------//

	public Cursor getEditBookingDetailsAdmin(String currentdate) {
		SQLiteDatabase db = this.getReadableDatabase();
    	Cursor mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT", "EMP_USERID"}, "FROM_date != '"+currentdate+"'" , null, null, null, null);
    		 
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
	}

		//--------method for get booked setup details for editbookingsetuplist at admin side---------//
	public String getSetupDetailsAdmin(String text) {
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM SETUPBOOK WHERE "+ KEY_ID + " ='"+text+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	if(cursor.getString(5)!=null&&cursor.getString(8)!="")
            	{
            	log = log + "\nSetup Name: " 
            	        + cursor.getString(1)+"\nFrom: " + cursor.getString(4)+"\nTo: " 
            	        		+ cursor.getString(5)+" \nSlot: " + cursor.getString(6)+" \nEMP User Id: " + cursor.getString(7)+" \nOther Requirements: " + cursor.getString(8); 
            	}
            	else if(cursor.getString(5)==null){
            		log = log + "\nSetup Name: " 
	            	        + cursor.getString(1)+"\nDate: " + cursor.getString(4)+" \nSlot: " + cursor.getString(6)+" \nEMP User Id: " + cursor.getString(7)+" \nOther Requirements: " + cursor.getString(8); 
            	}
            	else if(cursor.getString(8)==""){
            		log = log + "\nSetup Name: " 
	            	        + cursor.getString(1)+"\nFrom: " + cursor.getString(4)+"\nTo: " 
	            	        		+ cursor.getString(5)+" \nSlot: " + cursor.getString(6)+" \nEMP User Id: " + cursor.getString(7); 
            	}
            	else {
            		log = log + "\nSetup Name: " 
	            	        + cursor.getString(1)+"\nDate: " + cursor.getString(4)+" \nSlot: " + cursor.getString(6)+" \nEMP User Id: " + cursor.getString(7); 
            	}
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}

			//-------------------Method for get booked setup name on current date admin side--------//
	public Cursor getfreeBookingDetailsAdmin(String currentdate) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor mCursor;
   	    mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT", "EMP_USERID"}, "FROM_date = '"+currentdate+"'" , null, null, null, null);
   		  if (mCursor != null) {
   		   mCursor.moveToFirst();
   		  }
		return mCursor;
	}
	//----method for get list of all booked setups----//

	public Cursor getBookingDetailsAdmin() {
		 SQLiteDatabase db = this.getReadableDatabase();
	    	Cursor mCursor = db.query("SETUPBOOK", new String[] {"_id", "NAME", "FROM_date", "TO_date", "SLOT", "EMP_USERID"}, null, null, null, null, null);
	    		 
	    		  if (mCursor != null) {
	    		   mCursor.moveToFirst();
	    		  }
	    		  return mCursor;
	}
	
	//---------method for get booking details of particular booking in booked list class at admin side----//
	public String getbookeddetailsAdmin(String text) {
		
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS + " WHERE "+ KEY_ID + " ='"+text+"'";;  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	if(cursor.getString(5)==null&&cursor.getString(8)=="")
            	{
            	log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
            	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
            	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n Slot: " + cursor.getString(6)+"\n Employee ID: " + cursor.getString(7); 
            	}
            	if(cursor.getString(5)==null)
            	{
            	log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
            	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
            	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n Slot: " + cursor.getString(6)+"\n Employee ID: " + cursor.getString(7)+" \n Other Requirements: " + cursor.getString(8); 
            	}
            	else if(cursor.getString(8)==""){
            		log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
                	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
                	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n To Date: " 
                	        + cursor.getString(5)+"\n Slot: " + cursor.getString(6)+"\n Employee ID: " + cursor.getString(7);
            	}
             
            	else{
            		log = log + " ID: "+cursor.getString(0) +"\n Setup Name: " 
                	        + cursor.getString(1)+"\n Manager: " + cursor.getString(2)+"\n Team: " 
                	        		+ cursor.getString(3)+" \n From Date: " + cursor.getString(4)+"\n To Date: " 
                	        + cursor.getString(5)+"\n Slot: " + cursor.getString(6)+"\n Employee ID: " + cursor.getString(7)+" \n Other Requirements: " + cursor.getString(8);
            	}
            	break;
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log;
	 
}
	
	//---------------Method for get other requirement details in editbookingsetuplist file at admin side---------//
	public String getOtherDetailsAdmin(String text) {
		
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM SETUPBOOK WHERE "+ KEY_ID + " ='"+text+"'";
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	log=cursor.getString(8);
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}

//------method for get details of other rewuirement for particular booking at user side----//
	public String getOtherDetails(String text) {
		
		String log="";
        // Select All Query  
        String selectQuery = "SELECT  * FROM SETUPBOOK WHERE  "+ KEY_ID + " ='"+text+"'";  
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments  
   
        if (cursor.moveToFirst()) {  
            do {  
            	log=cursor.getString(8);
            } while (cursor.moveToNext());  
        }  
        // closing connection  
        cursor.close();  
        db.close();  
       
        // returning lables  
        return log; 
	}
        
	public void check() {
		 @SuppressWarnings("unused")
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  //yyyy/MM/dd HH:mm:ss
	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String selectQuery = "SELECT * FROM SETUPBOOK";
        SQLiteDatabase db = this.getReadableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list  
        try {
        if (cursor.moveToFirst()) {  
            do {  
                
            	if(!(cursor.getString(4)==null)){
					Date from = formatter.parse(cursor.getString(4));
					if(from.before(date))
					{
						if(!(cursor.getString(5)==null)){
							Date to = formatter.parse(cursor.getString(5));
							if(to.before(date)){
								String s1,s2,s3,s4;
								s1=cursor.getString(1);
								s2=cursor.getString(4);
								s3=cursor.getString(5);
								s4=cursor.getString(6);
								delete(s1,s2,s3,s4);
							}
						}
						else{
							String s1,s2,s3,s4;
							s1=cursor.getString(1);
							s2=cursor.getString(4);
							s3=cursor.getString(5);
							s4=cursor.getString(6);
							delete(s1,s2,s3,s4);
						}
					}
                }
            } while (cursor.moveToNext());  
        }
        } catch (ParseException e) {
			e.printStackTrace();
		}
        // closing connection  
        cursor.close();  
        db.close();  
	}
        
	 public void delete(String setup_name, String from, String to, String slot) {
	   	 int a = 0;
	   	 try {
	   		 SQLiteDatabase db = this.getWritableDatabase(); 
	   		 if(to==null)
	   		 db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND SLOT ='"+slot+"'"), null);
	   		 else
	   		 db.delete(TABLE_LABELS, (KEY_NAME+"='"+setup_name+"' AND FROM_date ='"+from+"' AND TO_date ='"+to+"' AND SLOT='"+slot+"'"), null);
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
				} 
	   	 		catch (Exception e) {
					e.printStackTrace();
				}
	   }
}