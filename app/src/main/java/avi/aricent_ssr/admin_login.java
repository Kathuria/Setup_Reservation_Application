package avi.aricent_ssr;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class admin_login extends Activity {

	private EditText username1,password1;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String name1 = "nameKey"; 
	public static final String pass1 = "passwordKey"; 
	SharedPreferences sharedpreferences;
	TextView attempts;
	int counter = 3;
	ImageView button,button1;
	AdminAdapter AdminAdapter;
	MainDatabaseHandler CancelDatabaseHandler;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_login);
		
		// create a instance of SQLite Database
	     AdminAdapter=new AdminAdapter(this);
	     AdminAdapter=AdminAdapter.open();
	     
		CancelDatabaseHandler = new MainDatabaseHandler(this);
		CancelDatabaseHandler.admdetail();
		
		addListenerOnButton();
	      attempts = (TextView)findViewById(R.id.editTextad3);	//Fetch text from page
	      attempts.setText(Integer.toString(counter));			// and inserts into counter
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// the 3 dots on right upper corner.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// inside 3 dots....
		int id = item.getItemId();
		if (id == R.id.action_about) {
	    	  Intent intent = new Intent(this, adm_about_us.class);
             startActivity(intent); 
			return true;
		}
		else if (id == R.id.action_contact) {
	    	  Intent intent = new Intent(this, contact_us.class);
	             startActivity(intent); 
				return true;
			}
		return super.onOptionsItemSelected(item);
	}
	
	
	  @Override
	   protected void onResume() {
	      sharedpreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	      super.onResume();
	      // To resume app after opt out of app and restore task
	   }
	      
	 
		public void addListenerOnButton() {

			final Context context = this;
			button = (ImageView) findViewById(R.id.imageButtona1);
			button1 = (ImageView) findViewById(R.id.imageButtona2);
			username1 = (EditText)findViewById(R.id.editTextad1);
			username1.setSelection(0);
		    password1 = (EditText)findViewById(R.id.editTextad2);
		      
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					      Editor editor = sharedpreferences.edit();
					      String u1 = username1.getText().toString();
					      String p1 = password1.getText().toString();
					      editor.putString(name1, u1);
					      editor.putString(pass1, p1);
					      editor.commit();

					// fetch the Password form database for respective user name*/
					String storedPassword=AdminAdapter.getSinlgeEntry(u1);
					
					if(u1.equals("@aricent.com")){
						Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
						username1.requestFocus();
					}
					
					else if(p1.equals("")){
						Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
						password1.requestFocus();
					}
					
					// check if the Stored password matches with  Password entered by user
					else if(p1.equals(storedPassword))
					{
						Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, adm_land.class);
                        startActivity(intent);
                        admin_login.this.finish();
					}
				else
					{
						Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
						counter--;
						//Here counter of attempts decreased if attempt is wrong
						attempts.setText(Integer.toString(counter));
						if(counter==0){
							button.setEnabled(false);
						}
					}
				}
			});
			
			button1.setOnClickListener(new OnClickListener() { 
				@Override
				public void onClick(View arg0) {
	 
				    Intent intent = new Intent(context,adm_frgt_pwd.class);
	                            startActivity(intent);   
				}
			});  
		}
			
		@Override 
		protected void onDestroy() {
			super.onDestroy();
		    // Close The Database
			AdminAdapter.close();
		}
		@Override
		public void onBackPressed() {	     
			admin_login.this.finish(); 	  
		    }
	}