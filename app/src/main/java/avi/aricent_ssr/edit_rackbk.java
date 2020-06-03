package avi.aricent_ssr;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class edit_rackbk extends Activity {

	Spinner spinner;
	String text,mName,tName,from,to;
	Button btn;
	allocateRackAdapter allocateRackAdapter ;
	TextView label1,label2;
	EditText n,tn;
	String lb1text,lb2text,nLabel,tLabel;
	final Context context = this;
	
     DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
     DateFormat fmtDateAndTime1 = DateFormat.getDateTimeInstance();
     
          TextView lblDateAndTime, lblDateAndTime1;
          Calendar myCalendar = Calendar.getInstance();
          Calendar myCalendar1 = Calendar.getInstance();

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
                lblDateAndTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
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
                lblDateAndTime1.setText(fmtDateAndTime1.format(myCalendar1.getTime()));
          }
          
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_rackbk);
		
		allocateRackAdapter =new allocateRackAdapter (this);
		allocateRackAdapter =allocateRackAdapter .open();
  		 
  		 spinner = (Spinner) findViewById(R.id.SpinnerEr);
		 btn = (Button) findViewById(R.id.editrackbutton);
		 n = (EditText) findViewById(R.id.mNameEr);
  		 tn = (EditText) findViewById(R.id.tNameEr);

		 loadSpinnerData();

		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
  			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
  				
  				text=spinner.getSelectedItem().toString();
  				DeallocateDatabaseHandler db = new DeallocateDatabaseHandler(getApplicationContext());

  				    lb1text = db.getData(text);
  	  				lb2text = db.getData1(text);
  	  				nLabel = db.getData2(text);
  	  		     	tLabel = db.getData3(text);
  	  		     	n.setText(nLabel);
  	  		     	tn.setText(tLabel);
  	  			    oldupdateLabel();
  	  		        oldupdateLabel1();
  			}
  	 
  			public void onNothingSelected(AdapterView<?> arg0) {	
  			}
  		});
		 
		//amar
		 lblDateAndTime = (TextView) findViewById(R.id.lblDateAndTimeEr);
         Button btnDate = (Button) findViewById(R.id.btnDateEr);        
         btnDate.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                      new DatePickerDialog(edit_rackbk.this, d, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
               }
         });

         Button btnTime = (Button) findViewById(R.id.btnTimeEr);
         btnTime.setOnClickListener(new View.OnClickListener() {
               public  void onClick(View v) {
                      new TimePickerDialog(edit_rackbk.this, t, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar
                                    .get(Calendar.MINUTE), true).show();
               }
         });
         
         lblDateAndTime1 = (TextView) findViewById(R.id.lblDateAndTimeE1r);
         Button btnDate1 = (Button) findViewById(R.id.btnDateE1r);
         btnDate1.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                      new DatePickerDialog(edit_rackbk.this, d1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                                    myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
               }
         });

         Button btnTime1 = (Button) findViewById(R.id.btnTimeE11r);
         btnTime1.setOnClickListener(new View.OnClickListener() {
               public  void onClick(View v) {
                      new TimePickerDialog(edit_rackbk.this, t1, myCalendar1.get(Calendar.HOUR_OF_DAY), myCalendar1
                                    .get(Calendar.MINUTE), true).show();
               }
         });
         updateLabel();
         updateLabel1();

		 btn.setOnClickListener(new View.OnClickListener() {
  		    public void onClick(View v) {

  		    	    mName=n.getText().toString();
		    		tName=tn.getText().toString();
  		    		from=label1.getText().toString();
  		    		to=label2.getText().toString();
  		    		
  		    		long row = allocateRackAdapter.updateSetup(text,mName,tName,from,to);
  						if (row > 0) {
  							// Save the Data in Database
  						    Toast.makeText(getApplicationContext(), "Rack allocation details updated Successfully!!", Toast.LENGTH_LONG).show(); 	
   					        edit_rackbk.this.finish();
  						} 
  						else {
  							Toast.makeText(getApplicationContext(),"Please Make Changes", Toast.LENGTH_LONG).show();
  						}
  		     }
  		 });
	}

	public void oldupdateLabel(){
		label1 = (TextView) findViewById(R.id.lblDateAndTimeEr);
 	    label1.setText(lb1text);
	  }
	
	public void oldupdateLabel1(){
		label2 = (TextView) findViewById(R.id.lblDateAndTimeE1r);
	    label2.setText(lb2text);
	}

	private void loadSpinnerData() {
        // database handler
		DeallocateDatabaseHandler db = new DeallocateDatabaseHandler(getApplicationContext());
        
        List<String> labels = db.getAllLabel();  
   
        // Creating adapter for spinner  
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
   
        // Drop down layout style - list view with radio button  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
   
        // attaching data adapter to spinner  
        spinner.setAdapter(dataAdapter); 
    }
}	