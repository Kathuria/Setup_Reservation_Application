package avi.aricent_ssr;

import java.util.Properties;
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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class helpdesk extends Activity implements OnClickListener{
	
	Session session = null;
	ProgressDialog pdialog = null;
	Context context = null;
	Spinner spin1,spin2,spin3;
	Button btnSubmit,reset;
	EditText text1,text2;
	String str1,str2,str3,str4,str5,nname;
	String rec, subject, textMessage;
	helpAdapter helpAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpdesk);
		
		context = this;
		helpAdapter=new helpAdapter(this);
		helpAdapter=helpAdapter.open();
		
		Button login = (Button) findViewById(R.id.buttonhelp);
		reset=(Button)findViewById(R.id.reset_help);
		text1 = (EditText) findViewById(R.id.Network_Element);
		text2 = (EditText) findViewById(R.id.Activity_desc);
		spin1 = (Spinner) findViewById(R.id.Type_of_Activity);
		spin2 = (Spinner) findViewById(R.id.Catagory_Assigned);
		spin3 = (Spinner) findViewById(R.id.Request_catagory);
		
		login.setOnClickListener(this);
		  spin1.setFocusable(true);
		  spin1.setFocusableInTouchMode(true);
		  spin1.requestFocus();
		
		  reset.setOnClickListener(new OnClickListener() {
				@Override
	 	        public void onClick(View v) {
	 	        	
					text1.setText("");
					text2.setText("");
					spin1.setSelection(0);
					spin2.setSelection(0);
					spin3.setSelection(0);
						
					spin1.setFocusable(true);
					spin1.setFocusableInTouchMode(true);
					spin1.requestFocus();
	 	        }
	 	    });
	}
				@Override
				public void onClick(View v) {
					rec = "avi.kathuria@aricent.com";    //Insert here lab team email id
					subject = "Helpdesk mail @Aricent_SSR app";
					
					str1 = spin1.getSelectedItem().toString();
					str2 = spin2.getSelectedItem().toString();
					str3 = spin3.getSelectedItem().toString();
					str4 = text1.getText().toString();
					str5 = text2.getText().toString();
					
					 if(str1.equals("Select type of activity")){
						  Toast.makeText(getApplicationContext(), "Please choose activity", Toast.LENGTH_SHORT).show();
						  spin1.setFocusable(true);
						  spin1.setFocusableInTouchMode(true);
						  spin1.requestFocus();
					 }
					  else if(str2.equals("Select category")){
						  Toast.makeText(getApplicationContext(), "Please choose category", Toast.LENGTH_SHORT).show();
						  spin2.setFocusable(true);
						  spin2.setFocusableInTouchMode(true);
						  spin2.requestFocus();
					  }
					  else if(str3.equals("Select Request category")){
						  Toast.makeText(getApplicationContext(), "Please choose Request category", Toast.LENGTH_SHORT).show();
						  spin3.setFocusable(true);
						  spin3.setFocusableInTouchMode(true);
						  spin3.requestFocus();
					  }
					  else if(str4.equals("")){
						  Toast.makeText(getApplicationContext(), "Network Element Missing", Toast.LENGTH_SHORT).show();
						  text1.requestFocus();
					  }
					  else if(str5.equals("")){
						  Toast.makeText(getApplicationContext(), "Activity Description Missing", Toast.LENGTH_SHORT).show();
						  text2.requestFocus();
					  }
					  else
					  {		  
						  SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
						  nname=sharedpreferences.getString("nameKey", "Couldn't load");
						  long row = helpAdapter.insertEntry(nname, str1, str2, str3, str4, str5);
						  if (row > 0) {
							// Save the Data in Database
						    Toast.makeText(getApplicationContext(), "Your message will be sent to lab support team. Thanks", Toast.LENGTH_LONG).show();
						    
						    if (isNetworkConnected(true)) {
						        // available network
						    	textMessage = "Hi <br> We received a mail for lab support from "+ nname +" please provide the required support.<br><br><b>Activity Type:</b> "+str1+"<br><b>Category:</b> "+str2+"<br><b>Request Category:</b> "+str3+"<br><b>Network Element:</b> "+str4+"<br><b>Activity Description:</b> "+str5+"<br><br>@Admin <b>Aricent SSR APP</b><br><br>For any query regarding app plz contact "+rec+" ";
						    	
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
						    	helpdesk.this.finish();
						    } 
						} 
						else 
							Toast.makeText(getApplicationContext(),"Sorry!! please try again ", Toast.LENGTH_LONG).show();
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
					helpdesk.this.finish();
				}
			}
}
