package avi.aricent_ssr;

import java.util.regex.Matcher;		// For Password patter matching
import java.util.regex.Pattern;		// For password pattern identification
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;		// Used to fire events respective to context of page
import android.content.DialogInterface;
import android.content.SharedPreferences;		// Used to store session values
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;		// To display content as printf...
 
// This will change admin's password

public class adm_chg_pwd extends Activity {
 
	final Context context = this;
	private Button button,reset;
	EditText oldp,newp,conp;	// Old password, New password, Compare password respectively
	String nname,pass;		
	AdminAdapter AdminAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adm_chg_pwd);
		
		AdminAdapter=new AdminAdapter(this);
		AdminAdapter=AdminAdapter.open();		// Adapter opened to acess its functions
		
		button = (Button) findViewById(R.id.buttonac1);
		reset = (Button) findViewById(R.id.reset_achg);		//reset password button
		
		// Fetching text from layout by finding their respective ids
		oldp = (EditText)findViewById(R.id.editTexta1);	
	    newp = (EditText)findViewById(R.id.editTexta2);
	    conp = (EditText)findViewById(R.id.editTexta3);

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
			    		
			    		// Fetch username and password from main activity to store session accordingly.

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
								if(passwordValidator(n))		// Password valid or not
									{
									AdminAdapter.updateEntry(nname,n);
									Toast.makeText(getApplicationContext(), "Updation Successful. Redirecting...", Toast.LENGTH_SHORT).show();
									adm_chg_pwd.this.finish();
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
					// if this button is clicked, just close the dialog box and do nothing
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
 	        	
				// Clear text values, fetched from edit text.
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