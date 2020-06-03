package avi.aricent_ssr;

import java.net.InetAddress;			// To fetch internet address of mobile
import java.util.Properties;			// Used in mail function
import javax.mail.Address;				// Used in mail function
import javax.mail.Authenticator;		// Used in mail function
import javax.mail.Message;				// Used in mail function
import javax.mail.MessagingException;		// Used in mail function
import javax.mail.PasswordAuthentication;		// Used in mail function
import javax.mail.Session;			// Used in mail function
import javax.mail.Transport;		// Used in mail function
import javax.mail.internet.InternetAddress;		// Used in mail function
import javax.mail.internet.MimeMessage;			// Used in mail function
import android.app.Activity;
import android.app.ProgressDialog;			//For Loading icon while sending
import android.content.Context;	
import android.content.Intent;
import android.net.ConnectivityManager;		// To check connectivity of mobile with network
import android.net.NetworkInfo;				// To obtain network info
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import avi.aricent_ssr.frgt_pwd.RetreiveFeedTask;

@SuppressWarnings("unused")
public class adm_frgt_pwd extends Activity implements OnClickListener{

	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	EditText reciep;
	String rec, subject, textMessage;
	AdminAdapter AdminAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.adm_frgt_pwd);
		
		// create a instance of SQLite Database
		AdminAdapter=new AdminAdapter(this);
		AdminAdapter=AdminAdapter.open();        
        context = this;
        
        Button login = (Button) findViewById(R.id.buttonafr1);
        reciep = (EditText) findViewById(R.id.editTextafr1);
        reciep.setSelection(0);
        
        login.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		rec = reciep.getText().toString();			// receiptant of mail
		subject = "Password Retrival of Aricent_SSR App";
		
		String storedPassword=AdminAdapter.getSinlgeEntry(rec);
		if(storedPassword.equals("NULL")){
			Toast.makeText(getApplicationContext(), "Sorry! Email Id not found in database", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			if (isNetworkConnected(true)) {
		        // available network
				
		    	textMessage = "Your Password for Aricent SSR App is "+ storedPassword;
							
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");	
				
				session = Session.getDefaultInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("aricent.ssr@gmail.com", "<Add_Password>");
						// sending mail from aricent.ssr@gmail.com account
					}
				});
				
				pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
				
				RetreiveFeedTask task = new RetreiveFeedTask();
				task.execute();
		    	}
		    else {
		        // no network
		    	Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
		    	adm_frgt_pwd.this.finish(); 
		    } 
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
			} 
			catch(MessagingException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Sending Failed", Toast.LENGTH_LONG).show();
			} 
			catch(Exception e) {
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
	        adm_frgt_pwd.this.finish();
		}
	}
}
