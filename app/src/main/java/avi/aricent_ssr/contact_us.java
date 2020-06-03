package avi.aricent_ssr;


// Same as admin contact us page with different background

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class contact_us extends Activity implements OnClickListener {
	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	Button btnSubmit,reset;
	EditText text1,text2,text3,text4;
	String str1,str2,str3,str4;
	String rec, subject, textMessage;
	ContactAdapter adap;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
	
		context = this;
		adap=new ContactAdapter(this);
		adap=adap.open();
		
		Button login = (Button) findViewById(R.id.cbuttonn1);
		reset=(Button)findViewById(R.id.reset_cont);
		text1 = (EditText) findViewById(R.id.cmail);
		text2 = (EditText) findViewById(R.id.cname);
		text3 = (EditText) findViewById(R.id.cphone);
		text4 = (EditText) findViewById(R.id.ccmnt);
		
		login.setOnClickListener(this);
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
 	        public void onClick(View v) {
 	        	
				text1.setText("");
				text2.setText("");
				text3.setText("");
				text4.setText("");
				
				text1.requestFocus();	                   		
 	        }
 	    });
	}

				@Override
				public void onClick(View v) {
					
					str1 = text1.getText().toString();
					str2 = text2.getText().toString();
					str3 = text3.getText().toString();
					str4 = text4.getText().toString();
					
					rec = "avi.kathuria@aricent.com";   //admin email id   ADD HERE
					subject = str2 + " wants to know something, please help";

					  if(str1.equals("")){
						  Toast.makeText(getApplicationContext(), "Email Id missing", Toast.LENGTH_SHORT).show();
						  text1.requestFocus();
					  }
					  else if(str2.equals("")){
						  Toast.makeText(getApplicationContext(), "Name missing", Toast.LENGTH_SHORT).show();
						  text2.requestFocus();
					  }
					  else if(str3.equals("")){
						  Toast.makeText(getApplicationContext(), "Mobile No. missing", Toast.LENGTH_SHORT).show();
						  text3.requestFocus();
					  }
					  else if(str4.equals("")){
						  Toast.makeText(getApplicationContext(), "Provide some comment", Toast.LENGTH_SHORT).show();
						  text4.requestFocus();
					  }
					  else
					  {		 
						  if(emailValidator(str1)) {
							}
						else
						{
							 Toast.makeText(getApplicationContext(), "invalid email address",Toast.LENGTH_SHORT).show();
							 text1.requestFocus();
							 return;
							}
							
							if(phnnoValidator(str3)){
								}
							else
							{
								Toast.makeText(getApplicationContext(), "invalid phone number ",Toast.LENGTH_SHORT).show();
								text3.requestFocus();
								return;
							}

							 if(emailValidator(str1) && phnnoValidator(str3))	
							 {
								 long row = adap.insertEntry(str1, str2, str3, str4);
								 if (row > 0) {
									 // Save the Data in Database
									 Toast.makeText(getApplicationContext(), "Thanks for contacting us!!! We'll be back to you ASAP.", Toast.LENGTH_LONG).show();
						    	
						    	if (isNetworkConnected(true)) {
							        // available network
							    	textMessage = "Hi, <br> We received and contact us mail from "+str2 +" having email "+str1+" and contact no. "+str3+" says: <br> "+str4+"<br> <br> Please provoide assistance";
									
									Properties props = new Properties();
									props.put("mail.smtp.host", "smtp.gmail.com");
									props.put("mail.smtp.socketFactory.port", "465");
									props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
									props.put("mail.smtp.auth", "true");
									props.put("mail.smtp.port", "465");
									
									session = Session.getDefaultInstance(props, new Authenticator() {
										protected PasswordAuthentication getPasswordAuthentication() {
											return new PasswordAuthentication("aricent.ssr@gmail.com", "<Add_Password");
										}
									});
									
									pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
									
									RetreiveFeedTask task = new RetreiveFeedTask();
									task.execute();
							    	}
							    else {
							        // no network
							    	Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
							    	contact_us.this.finish();
							    }
						    }
						else 
							Toast.makeText(getApplicationContext(),"Sorry!! please try again ", Toast.LENGTH_LONG).show();
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
						Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();  
				        contact_us.this.finish();
					}
				}
				
				public static boolean emailValidator(final String mailAddress) {

				    Pattern pattern;
				    Matcher matcher;

				    final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
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
