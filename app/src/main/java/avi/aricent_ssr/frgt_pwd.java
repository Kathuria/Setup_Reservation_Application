package avi.aricent_ssr;

// Same as admin forget password but with different background image

import java.net.InetAddress;
import java.util.Properties;
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
import android.widget.EditText;
import android.widget.Toast;
import avi.aricent_ssr.req_user.RetreiveFeedTask;

@SuppressWarnings("unused")
public class frgt_pwd extends Activity implements OnClickListener{

	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	EditText reciep;
	String rec, subject, textMessage;
	LoginDataBaseAdapter loginDataBaseAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.frgt_pwd);
		
		// create a instance of SQLite Database
	    loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
        
        context = this;
        
        Button login = (Button) findViewById(R.id.buttonfr1);
        reciep = (EditText) findViewById(R.id.editTextfr1);
        reciep.setSelection(0);
        
        login.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		rec = reciep.getText().toString();
		subject = "Password Retrival of Aricent_SSR App";
		
		String storedPassword=loginDataBaseAdapter.getSinlgeEntry(rec);
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
						}
					});
					
					pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
					
					RetreiveFeedTask task = new RetreiveFeedTask();
					task.execute();
			    	}
			   else {
			        // no network
				   Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
			    	 Intent intent = new Intent(context, MainActivity.class);	
			         startActivity(intent); 
			         frgt_pwd.this.finish();
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
	        frgt_pwd.this.finish();
		}
	}
}
