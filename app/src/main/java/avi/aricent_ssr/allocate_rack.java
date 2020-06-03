package avi.aricent_ssr;

import java.text.DateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;			//Used to suppress warnings
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class allocate_rack extends Activity {

                Button btn,reset;
                Spinner spn;
                EditText nLabel,tLabel;
                final Context context = this;  
                String text,mName,tName,from,to;
                allocateRackAdapter bookRackAdapter;
                DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
                DateFormat fmtDateAndTime1 = DateFormat.getDateTimeInstance();
                TextView lblDateAndTimeal, lblDateAndTime1al;
                Calendar myCalendar = Calendar.getInstance();
                Calendar myCalendar1 = Calendar.getInstance();
                     
                // to pick dates, used here calender
                     DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                     public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	                     myCalendar.set(Calendar.YEAR, year);
	                     myCalendar.set(Calendar.MONTH, monthOfYear);
	                     myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	                     updateLabel();
                     	}
                     };

                     TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                    	 public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    	   myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                           myCalendar.set(Calendar.MINUTE, minute);
                           updateLabel();
                    	 }
                     };

                     private void updateLabel() {
                           lblDateAndTimeal.setText(fmtDateAndTime.format(myCalendar.getTime()));
                     }
                     
                     DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {	 
                     public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                    	 myCalendar1.set(Calendar.YEAR, year);
	                     myCalendar1.set(Calendar.MONTH, monthOfYear);
	                     myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	                     updateLabel1();
                     	}
                     };

                     TimePickerDialog.OnTimeSetListener t1 = new TimePickerDialog.OnTimeSetListener() {
                     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                           myCalendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                           myCalendar1.set(Calendar.MINUTE, minute);
                           updateLabel1();
                     }
                     };

                     private void updateLabel1() {
                           lblDateAndTime1al.setText(fmtDateAndTime1.format(myCalendar1.getTime()));
                     }

                     @Override
                     public void onCreate(Bundle savedInstanceState) {
                     super.onCreate(savedInstanceState);
                     setContentView(R.layout.allocate_rack);
                     lblDateAndTimeal = (TextView) findViewById(R.id.lblDateAndTimeal);
                     Button btnDate = (Button) findViewById(R.id.btnDateal);
                     
                     //bhupinder
                     btn = (Button) findViewById(R.id.allocatebutton);
                     reset = (Button) findViewById(R.id.reset_alrack);
             		 spn = (Spinner) findViewById(R.id.SpinnerFeedbackTypeal1);
             		 nLabel = (EditText) findViewById(R.id.mName);
             		 tLabel = (EditText) findViewById(R.id.tName);
             		 bookRackAdapter=new allocateRackAdapter(this);
             		 bookRackAdapter=bookRackAdapter.open();

             		spn.setOnItemSelectedListener(new OnItemSelectedListener() {
             			 
             			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
             				text=spn.getSelectedItem().toString();
             				//Log.d("Setup", text);
             			}
             	 
             			public void onNothingSelected(AdapterView<?> arg0) {
             			}
             		});

                     //amar
                     btnDate.setOnClickListener(new View.OnClickListener() {
                           @SuppressLint("NewApi")
						public void onClick(View v) {
                                  new DatePickerDialog(allocate_rack.this, d, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                           }
                     });

                     Button btnTime = (Button) findViewById(R.id.btnTimeal);
                     btnTime.setOnClickListener(new View.OnClickListener() {
                           public  void onClick(View v) {
                                  new TimePickerDialog(allocate_rack.this, t, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
                           }
                     });
                     
                     lblDateAndTime1al = (TextView) findViewById(R.id.lblDateAndTime1al);
                     Button btnDate1 = (Button) findViewById(R.id.btnDate1al);
                     btnDate1.setOnClickListener(new View.OnClickListener() {
                           public void onClick(View v) {
                                  new DatePickerDialog(allocate_rack.this, d1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                           }
                     });

                     Button btnTime1 = (Button) findViewById(R.id.btnTime1al);
                     btnTime1.setOnClickListener(new View.OnClickListener() {
                           public  void onClick(View v) {
                                  new TimePickerDialog(allocate_rack.this, t1, myCalendar1.get(Calendar.HOUR_OF_DAY), myCalendar1.get(Calendar.MINUTE), true).show();
                           }
                     });
                     updateLabel();
                     updateLabel1();
                  
                 	btn.setOnClickListener(new View.OnClickListener() {
             		    public void onClick(View v) {

             		    	if(text.equals("Select"))
             		    	{
             		    		Toast.makeText(getApplicationContext(),"Choose a Rack", Toast.LENGTH_LONG).show();
             		    		 spn.setFocusable(true);
             		    		 spn.setFocusableInTouchMode(true);
             		    		 
             		    		 spn.requestFocus();
             		    	}
             		    	else 
             		    	{
             		    		from=lblDateAndTimeal.getText().toString();
             		    		to=lblDateAndTime1al.getText().toString();
             		    		mName=nLabel.getText().toString();
             		    		tName=tLabel.getText().toString();
             		    		long row = bookRackAdapter.storeSetup(text,mName,tName,from,to );
             						if (row > 0) {
             							// Save the Data in Database
             						    Toast.makeText(getApplicationContext(), "Rack Booked Successfully!!", Toast.LENGTH_LONG).show();
             						    allocate_rack.this.finish();
             						} 
             						else {
             							Toast.makeText(getApplicationContext(),"Rack is Already Booked!! Choose Another Rack", Toast.LENGTH_LONG).show();
             						}
             		    	}
             		     }
             		 });
                 	
                 	reset.setOnClickListener(new OnClickListener() {
            			@Override
             	        public void onClick(View v) {
             	        	
            				spn.setSelection(0);
            				
            				lblDateAndTimeal.setText("");
            				lblDateAndTime1al.setText("");
            				nLabel.setText("");
            				tLabel.setText("");
            				
            				spn.setFocusable(true);
            				spn.setFocusableInTouchMode(true);
            				spn.requestFocus();                   		
             	        }
             	    });
          }
 }