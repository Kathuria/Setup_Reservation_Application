package avi.aricent_ssr;

import com.pushbots.push.Pushbots;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	private EditText username,password;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String name = "nameKey"; 
	public static final String pass = "passwordKey"; 
	SharedPreferences sharedpreferences;
	TextView attempts;
	int counter = 3;
	ImageView button1,button2,button3;
	LoginDataBaseAdapter loginDataBaseAdapter;
	private boolean doubleBackToExitPressedOnce;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
		
		addListenerOnButton();
	      attempts = (TextView)findViewById(R.id.editText3);
	      attempts.setText(Integer.toString(counter));
	      
		  Pushbots.sharedInstance().init(this);			//Initiate pushbots for notifications
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
	    	  Intent intent = new Intent(this, about_us.class);
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
		      sharedpreferences=getSharedPreferences(MyPREFERENCES, 
		      Context.MODE_PRIVATE);
		      super.onResume();
		   }
		public void addListenerOnButton() {
			final Context context = this;
			button1 = (ImageView) findViewById(R.id.imageButton1);
			button2 = (ImageView) findViewById(R.id.imageButton2);
			button3 = (ImageView) findViewById(R.id.imageButton3);
			username = (EditText)findViewById(R.id.editText1);
			username.setSelection(0);
		    password = (EditText)findViewById(R.id.editText2);
	 
			button2.setOnClickListener(new OnClickListener() {
				public void onClick(View view){

				      Editor editor = sharedpreferences.edit();
				      String u = username.getText().toString();
				      String p = password.getText().toString();
				      editor.putString(name, u);
				      editor.putString(pass, p);
				      editor.commit();
					
					// fetch the Password form database for respective user name*/
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(u);
					if(u.equals("@aricent.com")){
						Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
						username.requestFocus();
					}
					else if(p.equals("")){
						Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
						password.requestFocus();
					}
					// check if the Stored password matches with  Password entered by user
					else if(p.equals(storedPassword))
					{
						Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, land_page.class);
                        startActivity(intent);
                        MainActivity.this.finish();
					}
				else
					{
						Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
						counter--;
						attempts.setText(Integer.toString(counter));
						if(counter==0){
							button2.setEnabled(false);
						}
					}
				}
			});
			
			button1.setOnClickListener(new OnClickListener() {
				 
				@Override
				public void onClick(View arg0) {
				    Intent intent = new Intent(context, admin_login.class);
	                startActivity(intent);   
	                MainActivity.this.finish();            
				}
	 
			});
			button3.setOnClickListener(new OnClickListener() {
				 
				@Override
				public void onClick(View arg0) {
				    Intent intent = new Intent(context, frgt_pwd.class);
	                startActivity(intent);   
	                MainActivity.this.finish();
				}
			});
		}
		
		@Override 
		protected void onDestroy() {
			super.onDestroy();
		    // Close The Database
			loginDataBaseAdapter.close();
		}
		
		@Override
		public void onBackPressed() {
		    if (doubleBackToExitPressedOnce) {
		        super.onBackPressed();
		        return;
		    }

		    this.doubleBackToExitPressedOnce = true;
		    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
		    
		    new Handler().postDelayed(new Runnable() {
		        @Override
		        public void run() {
		            doubleBackToExitPressedOnce=false;   
		        }
		    },2000);		//waiting period between 2 back pressed
		} 
}