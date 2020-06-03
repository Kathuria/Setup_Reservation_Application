package avi.aricent_ssr;

import java.net.InetAddress;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

// User can only request to add new user

@SuppressWarnings("unused")
public class req_user extends Activity implements OnClickListener{
	
	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	CheckBox ch;
	EditText reciep,phone;
	String rec, subject, textMessage;
	Button reset;
	LoginDataBaseAdapter loginDataBaseAdapter;
	int checked = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.req_user);
		
		// create a instance of SQLite Database
	    loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
        context = this;
        Button login = (Button) findViewById(R.id.buttonn1);
        reset = (Button) findViewById(R.id.reset_requ);
        reciep = (EditText) findViewById(R.id.EditTextn2);
        reciep.setSelection(0);
        phone = (EditText) findViewById(R.id.et_phone_number);
        ch = (CheckBox)findViewById(R.id.checkBox1);
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
        login.setOnClickListener(this);
        
        reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
		        reciep.setText("");
		        phone.setText("");
			   	ch.setChecked(false);
			   	reciep.requestFocus();              		
 	        }
 	    });
    }

	@Override
	public void onClick(View v) {
		rec = reciep.getText().toString();
		subject = "OTP for Aricent_SSR App";
		
		MyStringRandomGen msr = new MyStringRandomGen();
		String newPassword=msr.generateRandomString();
		String userName= reciep.getText().toString();
		String phnno= phone.getText().toString();
		if(emailValidator(userName)) {
		}
	else
	{
		Toast.makeText(getApplicationContext(), "invalid email address",Toast.LENGTH_SHORT).show();
		 reciep.requestFocus();
		return;
		}

		if(phnnoValidator(phnno)){
		}
		else
		{
		Toast.makeText(getApplicationContext(), "invalid phone number ",Toast.LENGTH_SHORT).show();
		phone.requestFocus();
		return;
		}
		// check if any of the fields are vacant
		if(userName.equals("")|| phnno.equals(""))
		{
				Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
				return;
		}
		long row = loginDataBaseAdapter.insertEntry(userName, newPassword,phnno,checked);
		if (row > 0) {
			// Save the Data in Database
		    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
		   
		    if (isNetworkConnected(true)) {
		        // available network
		    	textMessage = "Your Password for Aricent SSR App is "+ newPassword +"<br>  Please change it ASAP";

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				
				session = Session.getDefaultInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("aricent.ssr@gmail.com", "<Add_Password>");
					}
				});
				
				pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
				
				RetreiveFeedTask task = new RetreiveFeedTask();
				task.execute();
		    	}
		    else {
		        // no network
		    	Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
		    	req_user.this.finish(); 
		    } 
		}
		else {
			Toast.makeText(getApplicationContext(),"Username/Phone No. already exists!!", Toast.LENGTH_LONG).show();
		}
	}

	private boolean isNetworkConnected(boolean b) {
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
	}
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("aricent.ssr@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
				message.setSubject(subject);
				message.setContent(textMessage, "text/html; charset=utf-8");
				Transport.send(message);				
			} catch(MessagingException e) {
				e.printStackTrace();	
				Toast.makeText(getApplicationContext(), "Sending Failed", Toast.LENGTH_LONG).show();
			} catch(Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Sending Failed", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			pdialog.dismiss();
			reciep.setText("");
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();  
	        req_user.this.finish();
		}
	}
	
	public static boolean emailValidator(final String mailAddress) {
	    Pattern pattern;
	    Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@aricent.com";
	    pattern = Pattern.compile(EMAIL_PATTERN);
	    matcher = pattern.matcher(mailAddress);
	    return matcher.matches();
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
