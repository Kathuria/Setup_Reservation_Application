package avi.aricent_ssr;

import com.pushbots.push.Pushbots;		//pushbots library to use in notifications
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
 
 public class land_page extends Activity {
 
	ImageButton button1,button3,button4,button5,button6,button9,button10,freebtn;
	ToggleButton tbtn;
	bookSetupAdapter bookSetupAdapter;
	MainDatabaseHandler CancelDatabaseHandler;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String nname,val,regId="";
	int checked=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.land_page);
		bookSetupAdapter = new bookSetupAdapter(this);
		CancelDatabaseHandler = new MainDatabaseHandler(this);
		//CancelDatabaseHandler.check();
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
		//bookSetupAdapter=bookSetupAdapter.open();
		CancelDatabaseHandler.insert();
		addListenerOnButton();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_main, menu);
		return true;
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) 
    {
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
	 	nname=sharedpreferences.getString("nameKey", "Couldn't load");
	 	   
	 	String[] temp = nname.split("@");
		String[] name= temp[0].split("\\.");
		String fname="Hi "+name[0];
	 	   
		Pushbots.sharedInstance().setAlias(name[0]);			//Set Alias for pushbots acc
	
        MenuItem menuItem = menu.findItem(R.id.action_name);
        TextView showusername1 = (TextView) menuItem.getActionView();
        showusername1.setText(String.valueOf(fname) );
        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final Context context = this;
		int id = item.getItemId();
		if (id == R.id.action_name) {
	    	  // user First Name
			return true;
		}
		else if (id == R.id.action_pwd) {
		    Intent intent = new Intent(context,chg_pwd.class);
            startActivity(intent);   
				return true;	
			}
		else if (id == R.id.action_rate) {
			 Intent intent = new Intent(context,feed_usr.class);
             startActivity(intent);    
			return true;	
		}
		else if (id == R.id.action_contact) {
			Intent intent = new Intent(context, contact_us.class);
	    	  startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_policy) {
	    	  Intent intent = new Intent(context, policy.class);
	    	  startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_about) {
	    	  Intent intent = new Intent(context, about_us.class);
           startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_report) {
	    	  Intent intent = new Intent(this, report_bug.class);
           startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_log) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set title
				alertDialogBuilder.setTitle("Confirm this");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Are u sure!!!!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							SharedPreferences sharedpreferences = getSharedPreferences
								      (MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
								      Editor editor = sharedpreferences.edit();
								      editor.clear();
								      editor.commit(); 
								      moveTaskToBack(true); 
								      land_page.this.finish();
							Intent intent = new Intent(context,MainActivity.class);
	                        startActivity(intent);   
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}

	public void addListenerOnButton() {
		 
		final Context context = this;
		freebtn = (ImageButton) findViewById(R.id.freebutton);
		button1 = (ImageButton) findViewById(R.id.imageButtonl1);
		button3 = (ImageButton) findViewById(R.id.buttonl1);
		button4 = (ImageButton) findViewById(R.id.buttonl2);
		button5 = (ImageButton) findViewById(R.id.buttonl3);
		button6 = (ImageButton) findViewById(R.id.buttonl4);
		button9 = (ImageButton) findViewById(R.id.buttonl5);
		button10 = (ImageButton) findViewById(R.id.buttonl6);
		tbtn=(ToggleButton) findViewById(R.id.toggleButton1);
		
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
	 	nname=sharedpreferences.getString("nameKey", "Couldn't load");

		Boolean notif=loginDataBaseAdapter.getNotifEntry(nname);
		
		//boolean mBool = true;
		tbtn.setChecked(notif);
		 tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	            @Override
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	                if(isChecked){
	                    checked = 1;
	                    
	                    Pushbots.sharedInstance().setPushEnabled(true);		// register device again 
	                    regId = Pushbots.sharedInstance().regID();					//To get device Registration ID 
	                    
	                    if(regId.equals(""))
	                    	Toast.makeText(getApplicationContext(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
	                    else if(regId.equals(""))
	                    	Toast.makeText(getApplicationContext(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
	                    else
	                    	Toast.makeText(getApplicationContext(), "Notifications Enabled with regId "+regId, Toast.LENGTH_SHORT).show();
	                }
	                else{
	                    checked = 0;
	                    
	                    Pushbots.sharedInstance().setPushEnabled(false);	//Unregister Device
	                    Pushbots.sharedInstance().unRegister();				//Unregister Device
	                    
	                    regId = Pushbots.sharedInstance().regID();					//To get device Registration ID 
	                    
	                    if(regId.equals(""))
	                    	Toast.makeText(getApplicationContext(), "Notifications Disabled", Toast.LENGTH_SHORT).show();
	                    else if(regId.equals(""))
	                    	Toast.makeText(getApplicationContext(), "Notifications Disabled", Toast.LENGTH_SHORT).show();
	                    else
	                    	Toast.makeText(getApplicationContext(), "Notifications Disabled with regId "+regId, Toast.LENGTH_SHORT).show();
	                }
                    val=String.valueOf(checked);
                    loginDataBaseAdapter.updateNotif(nname,val);
	            }
		 });
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,req_user.class);
                            startActivity(intent);   
			}
		});
		
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,setup_list_usr.class);
                            startActivity(intent);   
			}
		});
		
		button4.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,book_setup_usr.class);
                           startActivity(intent);   
			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,editBookingSetupList_usr.class);
                            startActivity(intent);   
			}
		});
		
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,cancel_booking_usr.class);
                            startActivity(intent);   
			}
		});
		
		button9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,faulty_setup.class);
                            startActivity(intent);   
			}
		});
		
		button10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,helpdesk.class);
                            startActivity(intent);   
			}
		});
		
		freebtn.setOnClickListener(new OnClickListener() {		
			@Override		
			public void onClick(View arg0) {		
			    Intent intent = new Intent(context,share_setup_usr.class);		
                            startActivity(intent);   		
			}		
		});
	}
	
	@SuppressLint("DefaultLocale") 
	public String makeProper(String theString) throws java.io.IOException{  
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
	
	@Override
	public void onBackPressed() {
		  moveTaskToBack(true);   
	}
}