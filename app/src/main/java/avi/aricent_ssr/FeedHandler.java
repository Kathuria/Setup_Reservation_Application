package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedHandler  extends SQLiteOpenHelper 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		public static final String DATA_ID = "ID";
		public static final String DATA_NAME = "NAME";
		private static final String DATABASE_TABLE = "Feedback";
		// SQL Statement to create a new database.
		static final String TABLE_CREATE = "create table "+"Feedback"+"( " +"ID"+" integer primary key autoincrement,"+ "RATING text NOT NULL,FEED text); ";
		// Variable to hold the database instance
		public SQLiteDatabase db;
		// Context of the application using the database.
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  FeedHandler(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  FeedHandler open() throws SQLException 
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

		 public List<FeedGetSet> getAllLabels(){
		        List<FeedGetSet> labels = new ArrayList<FeedGetSet>();
		         
		        // Select All Query
		        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
		      
		        SQLiteDatabase db = this.getReadableDatabase();
		        Cursor cursor = db.rawQuery(selectQuery, null);
		      
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		                FeedGetSet contact = new FeedGetSet();
		                contact.setRate(cursor.getString(1));
		                contact.setFeed(cursor.getString(2));
		                
		                // Adding contact to list
		                contact.getRate();
		                contact.getFeed();
		                labels.add(contact);
		            } while (cursor.moveToNext());
		        }
		         
		        // closing connection
		        cursor.close();
		        db.close();
		         
		        // returning lables
		        return labels;
		    }

		@Override
		public void onCreate(SQLiteDatabase arg0) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		}
}