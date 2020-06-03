package avi.aricent_ssr;

//Same as admin change password

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class chg_pwd extends Activity {
 
	final Context context = this;
	private Button button,reset;
	EditText oldp,newp,conp;
	String nname,pass;
	LoginDataBaseAdapter loginDataBaseAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chg_pwd);
		
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
		
		button = (Button) findViewById(R.id.buttonc1);
		reset = (Button) findViewById(R.id.reset_chg);
		oldp = (EditText)findViewById(R.id.editText1);
	    newp = (EditText)findViewById(R.id.editText2);
	    conp = (EditText)findViewById(R.id.editText3);
		
		// add button listener
		button.setOnClickListener(new OnClickListener() {
 
		@Override
		public void onClick(View arg0) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// set title
		alertDialogBuilder.setTitle("Confirm this");

		// set dialog message
		alertDialogBuilder
			.setMessage("Are u sure!!!!")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
			    	   nname=sharedpreferences.getString("nameKey", "Couldn't load");
			    		pass = sharedpreferences.getString("passwordKey", "Couldn't load");

			    		String o = oldp.getText().toString();
			    		String n = newp.getText().toString(); 
			    		String c = conp.getText().toString(); 
			    		
			    		 if(o.equals("")){
							  Toast.makeText(getApplicationContext(), "old password is blank", Toast.LENGTH_SHORT).show();
								 oldp.requestFocus();
						 }
						  else if(n.equals("")){
							  Toast.makeText(getApplicationContext(), "new password is blank", Toast.LENGTH_SHORT).show();
							  newp.requestFocus();
							  }
						  else if(c.equals("")){
							  Toast.makeText(getApplicationContext(), "confirm password is blank", Toast.LENGTH_SHORT).show();
								 conp.requestFocus();
						  }
						  else if(pass.equals(o))
						  {
							  if(n.equals(o)){
								Toast.makeText(getApplicationContext(), "New Password can't be same as old password", Toast.LENGTH_SHORT).show();
								newp.requestFocus();
							  }
							  else
							  {
								  if(n.equals(c))
								  {
									  if(passwordValidator(n))
									  {
										  loginDataBaseAdapter.updateEntry(nname,n);
										  Toast.makeText(getApplicationContext(), "Update Successfull!!  Redirecting...", Toast.LENGTH_SHORT).show();
										  chg_pwd.this.finish();
									  }	
									  else{
										  Toast.makeText(getApplicationContext(), "invalid password (4-8,1char,1num)",Toast.LENGTH_SHORT).show();
										  newp.requestFocus();
									  	  }
							           }
								  else{
									  Toast.makeText(getApplicationContext(), "confirm password error", Toast.LENGTH_SHORT).show();
									  conp.requestFocus();
								  	   }
								  }
					     	}
					  		else{
					  			Toast.makeText(getApplicationContext(), "Old password Mismatch", Toast.LENGTH_SHORT).show();
					  			 oldp.requestFocus();
					  			}
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
			}
		});
		
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
 	        	
				oldp.setText("");
			    newp.setText("");
			    conp.setText("");

			    oldp.requestFocus();                   		
 	        }
 	    });
	}		
	
		public static boolean passwordValidator(final String pass) {

		    Pattern pattern1;
		    Matcher matcher1;

		    final String PWD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z]).{4,8}$";
		    /*check for text string to have atleast one Numeric and one Character and the  length of text string to be 
		    between 4 to 8 characters*/

		    pattern1 = Pattern.compile(PWD_PATTERN);
		    matcher1 = pattern1.matcher(pass);
		    return matcher1.matches();
		}
	}