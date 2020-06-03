package avi.aricent_ssr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("SimpleDateFormat")
public class edit_booking extends Activity implements OnClickListener {

	 Spinner spinner,timespin,typespin;
	 EditText nLabel,tLabel,fromLabel,toLabel,otherLabel;
	 long diff,diff1,diff2,days;
	 String text,text1,text2,text3,mName,tName,from,to,slot,type,eName,setup_name_intent,to1,from1,to2,details_intent,u,other,otherdetails;
	 Button btn;
	 bookSetupAdapter bookSetupAdapter;
	 TextView cdtv,textfrom,textto,ctstv,details,total,avail;
	
	 private DatePickerDialog fromDatePickerDialog;
     private DatePickerDialog toDatePickerDialog;
     
     private SimpleDateFormat dateFormatter;
     MainDatabaseHandler db;

     private int year;
     private int month;
     private int day;
     static final int DATE_PICKER_ID = 1111;
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.edit_booking);
		
		 bookSetupAdapter=new bookSetupAdapter(this);
  		 bookSetupAdapter=bookSetupAdapter.open();
  		 
  		 dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
  		 //getting data from previous page
  		 Bundle b = getIntent().getExtras();
  		 details_intent = b.getString("details");
  		 text = b.getString("setup_name");
  		 text1 = b.getString("from_date");
  		 text2 = b.getString("to_date");
  		 text3 = b.getString("slot");
  		 eName = b.getString("username");
  		 otherdetails = b.getString("otherdetails");
  		 
  		 
  		//getting button, spinner, edittext, textview from layout file
  		 details = (TextView) findViewById(R.id.DetailsEUA);
  		 otherLabel = (EditText) findViewById(R.id.otherDetailsEUA);	
  		 typespin = (Spinner) findViewById(R.id.BookingTypeSpinnerEUA);
  		 cdtv = (TextView) findViewById(R.id.cdtvEUA);
  		 fromLabel = (EditText) findViewById(R.id.fromdateEUA);
 		 toLabel = (EditText) findViewById(R.id.todateEUA);
 		 textfrom = (TextView) findViewById(R.id.textfromEUA);
 		 textto = (TextView) findViewById(R.id.texttoEUA);
 		 ctstv = (TextView) findViewById(R.id.ctstvEUA);
 		 total = (TextView) findViewById(R.id.totalEUA);
 		 avail = (TextView) findViewById(R.id.availabilityEUA);
 		 timespin = (Spinner) findViewById(R.id.TimeSlotSpinnerEUA);
		 btn = (Button) findViewById(R.id.editbookbuttonEUA);
		 details.setText(details_intent);

		 cdtv.setVisibility(View.GONE);
  		 textfrom.setVisibility(View.GONE);
  		 fromLabel.setVisibility(View.GONE);
  		 textto.setVisibility(View.GONE);
  		 toLabel.setVisibility(View.GONE);
  		 ctstv.setVisibility(View.GONE);
  		 timespin.setVisibility(View.GONE);
  		 total.setVisibility(View.GONE);
  		 avail.setVisibility(View.GONE);
  		 db = new MainDatabaseHandler(this);		
   		 otherLabel.setText(null);				
   		 otherLabel.setText(otherdetails);

   		 
   		 //getting date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Show selected date
        StringBuilder dateValue1=new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ");

        //for Converting Correct Date format Save into Database
        SimpleDateFormat sdf123 = new SimpleDateFormat("dd-MM-yyyy");
        String abs1= dateValue1.toString();
        Date testDate1 = null;
         try {
            testDate1 = sdf123.parse(abs1);
        } 
         catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        String DateFormat=formatter1.format(testDate1);
      //getting date in from edittext
        fromLabel.setText(DateFormat);
        fromLabel.setFocusable(false);
        fromLabel.setInputType(InputType.TYPE_NULL);
        fromLabel.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
				@Override
                public void onClick(View v) {
               	 showDialog(DATE_PICKER_ID, null);
                }
            });
      //getting date in to edittext
        toLabel.setText(DateFormat);
        toLabel.setFocusable(false);
        toLabel.setInputType(InputType.TYPE_NULL);
        toLabel.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
				@Override
                public void onClick(View v) {
               	 showDialog(DATE_PICKER_ID+1, null);
                }
            });
        
     // type spinner on click listener
  		typespin.setOnItemSelectedListener(new OnItemSelectedListener() {
 			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
 				
 				type = typespin.getSelectedItem().toString();
 				if(type.equals("According to time"))
 				{
 					cdtv.setVisibility(View.VISIBLE);
             		textfrom.setVisibility(View.VISIBLE);
             		fromLabel.setVisibility(View.VISIBLE);
 					textfrom.setText("Date");
 					fromLabel.setText("");
 					toLabel.setText("");
 					from=null;
 					total.setVisibility(View.GONE);
             		ctstv.setVisibility(View.VISIBLE);
             		timespin.setVisibility(View.VISIBLE);
             		textto.setVisibility(View.GONE);
             		toLabel.setVisibility(View.GONE);
             		avail.setVisibility(View.GONE);
 				}
 				else if(type.equals("According to days"))
 				{
 					cdtv.setVisibility(View.VISIBLE);
             		textfrom.setVisibility(View.VISIBLE);
             		fromLabel.setVisibility(View.VISIBLE);
             		textto.setVisibility(View.VISIBLE);
             		toLabel.setVisibility(View.VISIBLE);
             		textfrom.setText("From");
             		from=null;
             		fromLabel.setText("");
             		toLabel.setText("");
             		total.setVisibility(View.GONE);
             		timespin.setVisibility(View.GONE);
             		ctstv.setVisibility(View.GONE);
 				}
 			}
 	 
 			public void onNothingSelected(AdapterView<?> arg0) {	
 			}
 		});
  	// time slot spinner on click listener
  		timespin.setOnItemSelectedListener(new OnItemSelectedListener() {
 			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
 				slot = timespin.getSelectedItem().toString();
 			}
 	 
 			public void onNothingSelected(AdapterView<?> arg0) {
 			}
 		});
  	// update button on click listener
		 btn.setOnClickListener(new View.OnClickListener() {
 		    public void onClick(View v) {
		    	if(text.equals("Select"))
		    	{
		    		Toast.makeText(getApplicationContext(),"Choose a Setup", Toast.LENGTH_LONG).show();
		    	}
		    	else {
		    		other=otherLabel.getText().toString();
		    		long row=0;
		    		if(from==null){
		    			Toast.makeText(getApplicationContext(), "Choose Date", Toast.LENGTH_LONG).show();
		    		}
		    		else{
        		    		if(type.equals("According to time")){
        		    			if(slot=="No Slot Available"){
        		    				row=0;
        		    			}
        		    			else if(slot=="Select Time Slot"){
        		    				Toast.makeText(getApplicationContext(), "Choose Date First", Toast.LENGTH_LONG).show();
        		    			}
        		    			else if((total.getText().toString())=="Choose date after or equals to current date"||(total.getText().toString())=="Booking not allowed as the Date given is outside Advance Booking Period"){
         		    				row=0;
         		    			}
        		    			else{
	             		    		    to=null;
	             		    			row = bookSetupAdapter.updateSetup(text,text1,text2,text3,from,to,slot,eName,other);
        		    			}
        		    		}
        		    		else{
        		    			if(to==null){
        		    				Toast.makeText(getApplicationContext(), "Select To Date", Toast.LENGTH_LONG).show();
        		    			}
        		    			else{
            		    			if((avail.getText().toString())=="Not Available! Choose different dates"){
            		    				row=0;
            		    			}
            		    			else if((total.getText().toString())=="Choose date after or equals to current date"||(total.getText().toString())=="Choose date after or equals to current date"||(total.getText().toString())=="To date cannot be  before from date"||(total.getText().toString())=="You cannot make booking more than 30 days"||(total.getText().toString())=="Booking not allowed as the Date given is outside Advance Booking Period"){
            		    				row=0;
            		    			}
            		    			else{
	             		    			slot="FULL DAY";
	             		    			row = bookSetupAdapter.updateSetup(text,text1,text2,text3,from,to,slot,eName,other); 
            		    			}
        		    			}
        		    		}
        		    		if (row > 0) {
        							//  Save the Data in Database
        		    			Toast.makeText(getApplicationContext(), "Booking Updated Successfully!!", Toast.LENGTH_LONG).show();
       					        edit_booking.this.finish();
        						} 
        						else {
        							Toast.makeText(getApplicationContext(),"Please Make Some Changes", Toast.LENGTH_LONG).show();
        						}
		    		}
		    	}
 		     }
 		 });
	}

	//-----------------------------Spinner list According to day--------------------------//

    private void loadTimeSpinnerDataATD(String text2,String from2, String to2, long days) {
    	
   	 avail.setVisibility(View.VISIBLE);
   	 MainDatabaseHandler db1 = new MainDatabaseHandler(getApplicationContext());
   	 List<String> labels = db1.getTimeSlotATD(text2,from2,to2,days); 
   	 u = "";
   	 int size = labels.size();
		 for(int i=0;i<size;i++)
        {
			 u = labels.get(i);
        }            	 
   	 if(labels.isEmpty()){
   		 avail.setText("Available");
   	 }
   	 else {
   		 avail.setText("Not Available! Choose different dates");
   	 }
    }	                    

    //-----------------------------Spinner list According to Time--------------------------//     

    private void loadTimeSpinnerDataATT(String text2,String from2, int cnt) {

   	    double CTime;
   	    DateFormat dateFormat1 = new SimpleDateFormat("HH.mm");
		Date date1 = new Date();
		String u1 = dateFormat1.format(date1);
		CTime = Double.parseDouble(u1);
			
    	 MainDatabaseHandler db1 = new MainDatabaseHandler(getApplicationContext());
    	 List<String> labels = db1.getTimeSlotATT(text2,from2); 
    	 
    	 String uu = "",cmp="";
    	 int size = labels.size();
		 for(int i=0;i<size;i++)
         {
        	uu = labels.get(i);
        	if(uu.equals("FULL DAY"))
        		cmp=uu;
         }            	 
    	 if(labels.isEmpty()){
    		 ArrayList<String> d = new ArrayList<String>();
    		if(cnt==1){
    			if(CTime>=20.30)
         		 {
       				d.add("No Slot Available");
       				Toast.makeText(getBaseContext(), "Choose Different Date", Toast.LENGTH_SHORT).show();
         		 }
    		else if(CTime<=8.30)
      		 {
    				d.add("08.30-11.30");
      			    d.add("11.30-14.30");
          		    d.add("14.30-17.30");
          		    d.add("17.30-20.30");
      		 }
    		else if(CTime>=8.30&&CTime<=10.30)
       		 {
    			 d.add("08.30-11.30");
       			 d.add("11.30-14.30");
           		 d.add("14.30-17.30");
           		 d.add("17.30-20.30");
       		 }
    		else if(CTime>=10.30&&CTime<=13.30)
    		 {
    			 d.add("11.30-14.30");
        		 d.add("14.30-17.30");
        		 d.add("17.30-20.30");
    		 }
    		 else if(CTime>=13.30&&CTime<=16.30)
    		 {
    			 d.add("14.30-17.30");
    			 d.add("17.30-20.30");
    		 }
    		 else if(CTime>=16.30&&CTime<=20.30)
    		 {
    			 d.add("17.30-20.30");
    		 }
    	}
    	else
    		{
    			d.add("08.30-11.30");
  			    d.add("11.30-14.30");
      		    d.add("14.30-17.30");
      		    d.add("17.30-20.30");
    		}
    		 ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d);  
    		 data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
             
             // attaching data adapter to spinner  
             timespin.setAdapter(data);
    	 }
    	 
    	 else if(!(labels.isEmpty())&&!cmp.equals("FULL DAY")){

        		 ArrayList<String> d = new ArrayList<String>();
        		if(cnt==1){
         			if(CTime>=20.30)
              		 {
            				d.add("No Slot Available");
            				Toast.makeText(getBaseContext(), "Choose Different Date", Toast.LENGTH_SHORT).show();
              		 }
         			else if(CTime<=8.30)
         			{
         				d.add("08.30-11.30");
           			    d.add("11.30-14.30");
               		    d.add("14.30-17.30");
               		    d.add("17.30-20.30");
         			}
         			else if(CTime>=8.30&&CTime<=10.30)
            		 {
         				 d.add("08.30-11.30");
            			 d.add("11.30-14.30");
                		 d.add("14.30-17.30");
                		 d.add("17.30-20.30");
            		 }
         			else if(CTime>=10.30&&CTime<=13.30)
         			{
         				d.add("11.30-14.30");
         				d.add("14.30-17.30");
         				d.add("17.30-20.30");
         			}
         			else if(CTime>=13.30&&CTime<=16.30)
         			{
	            		 d.add("14.30-17.30");
	            		 d.add("17.30-20.30");
         			}
         			else if(CTime>=16.30&&CTime<=20.30)
         			{
         				d.add("17.30-20.30");
         			}
          	    }  
         		else
         		{
         			d.add("08.30-11.30");
       			    d.add("11.30-14.30");
           		    d.add("14.30-17.30");
           		    d.add("17.30-20.30");
         		}
            	 ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d);  
                 
                 // Drop down layout style - list view with radio button  
                 dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
                 int size2 = labels.size();
                 for(int j=0;j<size2;j++)
                 {
                	 dataAdapter1.remove(labels.get(j));    
                 }
                 
                 if(dataAdapter1.isEmpty())
                 {
               	  	ArrayList<String> d1 = new ArrayList<String>();
             		d1.add("No Slot Available");
             		ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d1);  
             		data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
                    timespin.setAdapter(data);
                    Toast.makeText(getBaseContext(), "Choose Different Date", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                       timespin.setAdapter(dataAdapter1);
                 }
    	 }
    	 
    	else if(cmp.equals("FULL DAY"))
    	{
  		 ArrayList<String> d = new ArrayList<String>();
  		 d.add("No Slot Available");
  		 ArrayAdapter<String> data = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, d);  
  		 data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
           
           // attaching data adapter to spinner  
           timespin.setAdapter(data);
           Toast.makeText(getBaseContext(), "Choose Different Date", Toast.LENGTH_SHORT).show();
    	}
	}

  //-----------------------------Date Picker Previous Not Used--------------------------//       

@SuppressWarnings("unused")
private void setDateTimeField() {
    fromLabel.setOnClickListener(this);
    toLabel.setOnClickListener(this);
    final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  //yyyy/MM/dd HH:mm:ss
	final Date date = new Date();
	final String u = dateFormat.format(date);
	
    Calendar newCalendar = Calendar.getInstance();
    fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
   	 
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
           
            newDate.set(year, monthOfYear, dayOfMonth);
          
            from1 = dateFormatter.format(newDate.getTime());
            diff1 = newDate.getTimeInMillis();
            long d = date.getTime();		           
            long d1 = (diff1 / (24 * 60 * 60 * 1000) - d / (24 * 60 * 60 * 1000)) + 1;

            if((newDate.getTime()).equals(date)||(newDate.getTime()).after(date)){
            	if(d1>30){ 
              	      total.setVisibility(View.VISIBLE);
                  	  total.setText("Booking not allowed as the Date given is outside Advance Booking Period");
                  	  avail.setVisibility(View.GONE);
                   }
                 else{
              	       total.setVisibility(View.GONE);
                       fromLabel.setText(from1);
                       toLabel.setText(null);
                       to=null;
                       avail.setVisibility(View.GONE);
                       from=fromLabel.getText().toString();
                 	}
            } 
            else{
	           	 total.setVisibility(View.VISIBLE);
	           	 total.setText("Choose date after or equals to current date");
	           	 fromLabel.setText("");
	           	 toLabel.setText(null);		
	          	 from=null;		
	          	 to=null;
            }
        }
    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    
    toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            to1 = dateFormatter.format(newDate.getTime());
	            diff2 = newDate.getTimeInMillis();
	            if((newDate.getTime()).equals(date)||(newDate.getTime()).after(date)){
	            total.setVisibility(View.GONE);
	            toLabel.setText(to1);
	            to=toLabel.getText().toString();
            }
            else{
	           	 total.setVisibility(View.VISIBLE);
	           	 total.setText("Choose date after or equals to current date");
	           	 toLabel.setText(null);		           	 
	       	     to=null;
            }
        }               
    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
 
    fromDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.Done), new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int which) {
	       if (which == DialogInterface.BUTTON_POSITIVE) {
	    	   dialog.cancel();
	    	   if(type.equals("According to time"))
	    	   {
	    		  int cnt;
	    		  if(from1.equals(u)){
                      cnt = 1;
                      loadTimeSpinnerDataATT(text,from,cnt);
 	    		  }
 	    		  else if(total.getText()=="Choose date after or equals to current date")
 	    		  {
 	    		  }
 	    		 else if(total.getText()=="Booking not allowed as the Date given is outside Advance Booking Period")		
 	    		  {			
 	    		  }
                  else
                  {
                	  cnt = 0;
                	  loadTimeSpinnerDataATT(text,from,cnt);
                  }
	    	   } 
	       }
	    }
	  });
   
    toDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.Done), new DialogInterface.OnClickListener() {
 	    public void onClick(DialogInterface dialog, int which) {
 	       if (which == DialogInterface.BUTTON_POSITIVE) {
 	    	   dialog.cancel();
 	    	   total.setVisibility(View.VISIBLE);
 	    	 if(from==null || fromLabel.equals(null))
 	    	 {
 	    		Toast.makeText(getBaseContext(), "Select From Date Fisrt",Toast.LENGTH_SHORT).show();
 	    		toLabel.setText("");
 	    		total.setVisibility(View.GONE);
 	    	 }
 	    	 else
 	         {
 	    		 diff = diff2 / (24 * 60 * 60 * 1000) - diff1 / (24 * 60 * 60 * 1000);
	 	         days = diff + 1;
	 	         if(days<=0){		
		 	         total.setText("To date cannot be  before from date");		
		 	         toLabel.setText("");		
		 	         avail.setVisibility(View.GONE);		
		 	         }		
		         else if(days>30){		
		   	    	 total.setText("You cannot make booking more than 30 days");		
		   	         avail.setVisibility(View.GONE);		
	   	        	}		
	 	         else{		
		 	    	 total.setText("Total Days "+days);		
		 	    	 loadTimeSpinnerDataATD(text,from,to,days);
		 	    	 }
 	         }
 	       }
 	    }
 	  });
}//setDateTimeField

//-----------------------------Date Picker New In Use--------------------------// 
	@Override
	public Dialog onCreateDialog(int id)
	{
	    switch (id) {
	  //From Date
	    case DATE_PICKER_ID:
	        // open datepicker dialog.
	        // set date picker for current date
	        // add pickerListener listner to date picker
	        //return new DatePickerDialog(this, pickerListener, year, month, day);
	
	        //Only Show till Date Not More than That.
	            DatePickerDialog fromdialog = new DatePickerDialog(this, frompickerListener, year, month, day);
	            fromdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
	            //dialog.getDatePicker().setMaxDate(new Date().getTime());
	            return fromdialog;
	          //To Date
	    case DATE_PICKER_ID+1:
	            DatePickerDialog todialog = new DatePickerDialog(this, topickerListener, year, month, day);
	            todialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
	            //dialog.getDatePicker().setMaxDate(new Date().getTime());
	            return todialog;
	    }
	    return null;
	}
	// From date picker-----------------------------------------------------------
	private DatePickerDialog.OnDateSetListener frompickerListener = new DatePickerDialog.OnDateSetListener()
	{
	    // when dialog box is closed, below method will be called.
	    @Override
	    public void onDateSet(DatePicker view, int selectedYear,
	        int selectedMonth, int selectedDay) {
	        year = selectedYear;
	        month = selectedMonth;
	        day = selectedDay;

	        // Show selected date
	        StringBuilder dateValue=new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ");

	        //for Converting Correct Date format Save into Database
	            SimpleDateFormat sdf123 = new SimpleDateFormat("dd-MM-yyyy");
	                    String abs1= dateValue.toString();
	                    Date testDate1 = null;
	                     try {
	                        testDate1 = sdf123.parse(abs1);
	                    } 
	                     catch (ParseException e) {
	                        e.printStackTrace();
	                    }
	        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
	                    String DateFormat=formatter1.format(testDate1);

	                    final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  //yyyy/MM/dd HH:mm:ss
	      				final Date date = new Date();
	      				final String u = dateFormat.format(date);       

	                    from1 = dateFormatter.format(testDate1);
	                    diff1 = testDate1.getTime();
	                    long d = date.getTime();
	                   	long d1 = (diff1 / (24 * 60 * 60 * 1000) - d / (24 * 60 * 60 * 1000)) + 1;
	                   	
	                          if(d1>30){ 
	                       	     total.setVisibility(View.VISIBLE);
	                           	 total.setText("Booking not allowed as the Date given is outside Advance Booking Period");
	                           	 fromLabel.setText(DateFormat);
	                           	 avail.setVisibility(View.GONE);
	                           	 timespin.setAdapter(null);
	            				 ArrayList<String> dd1 = new ArrayList<String>();
	                      		 dd1.add("Select Time Slot");
	                      		 ArrayAdapter<String> dat1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, dd1);  
	                      		 dat1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	                      		 timespin.setAdapter(dat1);
	                            }
	                          else{
	                       	     total.setVisibility(View.GONE);
	                       	     total.setText(null);
	                       	     fromLabel.setText(DateFormat);
	                             toLabel.setText(null);
	                             to=null;
	                             avail.setVisibility(View.GONE);
	                             from=fromLabel.getText().toString();         	 
	                          }
	                          if(type.equals("According to time"))
	           	    	   	  {
		           	    		  int cnt=-1;
		           	    		 if(from1.equals(u)){
		                               cnt = 1;
		                               loadTimeSpinnerDataATT(text,from,cnt);
		          	    		  }
		          	    		  else if(total.getText()=="Choose date after or equals to current date")
		          	    		  {
		          	    		  }
		          	    		  else if(total.getText()=="Booking not allowed as the Date given is outside Advance Booking Period")
		          	    		  {  
		          	    		  }
		                           else
		                           {
		                        	   cnt = 0;
		                        	   loadTimeSpinnerDataATT(text,from,cnt);
		                           }
	           	    	   }
	                 }
	};

	// To date-----------------------------------------------------------

	private DatePickerDialog.OnDateSetListener topickerListener = new DatePickerDialog.OnDateSetListener()
	{
	
	    // when dialog box is closed, below method will be called.
	    @Override
	    public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
	   	
	        year = selectedYear;
	        month = selectedMonth;
	        day = selectedDay;

	        // Show selected date
	        StringBuilder dateValue=new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ");
	
	        //for Converting Correct Date format Save into Database
	            SimpleDateFormat sdf123 = new SimpleDateFormat("dd-MM-yyyy");
	                    String abs1= dateValue.toString();
	                    Date testDate11 = null;
	                     try {
	                        testDate11 = sdf123.parse(abs1);
	                    } 
	                     catch (ParseException e) {
	                        e.printStackTrace();
	                    }
	                    SimpleDateFormat formatter11 = new SimpleDateFormat("dd-MM-yyyy");
	                    @SuppressWarnings("unused")
						String DateFormat=formatter11.format(testDate11);

	                    final DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");  //yyyy/MM/dd HH:mm:ss
	      				final Date date1 = new Date();
	      				@SuppressWarnings("unused")
						final String u = dateFormat1.format(date1);       
	                    to1 = dateFormatter.format(testDate11);
	                    diff2 = testDate11.getTime();

	                    total.setVisibility(View.GONE);
	                    toLabel.setText(to1);
	                    to=toLabel.getText().toString();

	                    total.setVisibility(View.VISIBLE);
	                    
	           	    	 if(from==null || fromLabel.equals(null))
	           	    	 {
	           	    		Toast.makeText(getBaseContext(), "Select From Date Fisrt",Toast.LENGTH_SHORT).show();
	           	    		toLabel.setText("");
	           	    		total.setVisibility(View.GONE);
	           	    	 }
	           	    	 else
	           	         {
	           	    		 diff = diff2 / (24 * 60 * 60 * 1000) - diff1 / (24 * 60 * 60 * 1000);
	           	    		 days = diff + 1;
	           	         }
	           	         if(days<=0){
		           	         total.setText("To date cannot be  before from date");
		           	         avail.setVisibility(View.GONE);
	           	         }
	           	         else if(days>30){		
	               	    	 total.setText("You cannot make booking more than 30 days");		
	               	         avail.setVisibility(View.GONE);		
	               	     }
	           	         else{
		           	    	 total.setText("Total Days "+days);
		           	    	 loadTimeSpinnerDataATD(text,from,to,days);}      
	                        }
	};
	
	public void onClick(View view) {
	    if(view == fromLabel) {
	        fromDatePickerDialog.show();
	    } else if(view == toLabel) {
	        toDatePickerDialog.show();
	    }        
	}

	public void onClose(DialogInterface dialogInterface)
	{
	}
}	