package avi.aricent_ssr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

// Admin can create new user any time, as if someone requests to do so.

public class new_user extends Activity
{
	EditText editTextUserName,editTextPassword,editTextConfirmPassword,editTextphnno;
	CheckBox ch;
	Button btnCreateAccount,reset;
	final Context context = this;
	LoginDataBaseAdapter loginDataBaseAdapter;
	int checked = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);
		
		// get Instance  of Database Adapter
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
		
		// Get References of Views
		editTextUserName=(EditText)findViewById(R.id.editTextn1);
		editTextUserName.setSelection(0);
		editTextPassword=(EditText)findViewById(R.id.EditTextn2);
		editTextConfirmPassword=(EditText)findViewById(R.id.EditTextn3);
		editTextphnno=(EditText)findViewById(R.id.et_phone_number);
        ch = (CheckBox)findViewById(R.id.checkBox11);
        ch.setChecked(true);
        ch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checked = 1;
                }else{
                    checked = 0;
                }
            }
        });
		btnCreateAccount=(Button)findViewById(R.id.buttonn1);
		reset=(Button)findViewById(R.id.reset_new);
		
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			String userName=editTextUserName.getText().toString();
			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();
			String phnno=editTextphnno.getText().toString();
			if(emailValidator(editTextUserName.getText().toString())){
			}
		else
		{
			Toast.makeText(getApplicationContext(), "invalid email address",Toast.LENGTH_SHORT).show();
			editTextUserName.requestFocus();
			return;
			}
			if(passwordValidator(editTextPassword.getText().toString())){
				}
			else
			{
				Toast.makeText(getApplicationContext(), "invalid password (4-8,1char,1num)",Toast.LENGTH_SHORT).show();
				editTextPassword.requestFocus();
				return;
				}
			if(phnnoValidator(editTextphnno.getText().toString())){
				}
			else
			{
				Toast.makeText(getApplicationContext(), "invalid phone number ",Toast.LENGTH_SHORT).show();
				editTextphnno.requestFocus();
				return;
			}
			// check if any of the fields are vacant
			if(userName.equals("")||password.equals("")||confirmPassword.equals("") || phnno.equals(""))
			{
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
			}
			// check if both password matches
			if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
				editTextPassword.requestFocus();
				return;
			}
			else
			{
				long row = loginDataBaseAdapter.insertEntry(userName, password,phnno,checked);
				if (row > 0) {
				    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
				    new_user.this.finish();
				} else {
					Toast.makeText(getApplicationContext(),"Username/Phone No. already exists!!", Toast.LENGTH_LONG).show();
				}
			}
		}
	});
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
				editTextUserName.setText("");
				editTextPassword.setText("");
				editTextConfirmPassword.setText("");
				editTextphnno.setText("");
		        ch.setChecked(false);
		        editTextUserName.requestFocus();               		
 	        }
 	    });
		
}
	
	public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@aricent.com";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();
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
	
	public static boolean phnnoValidator(final String phno) {

        Pattern pattern;
        Matcher matcher;

        final String PHONE_PATTERN = "[0-9]{10}";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phno);
        return matcher.matches();
    }
}