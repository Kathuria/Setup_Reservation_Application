package avi.aricent_ssr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
 
public class adm_land extends Activity {
	
	ImageButton ibtn2;
	public static final String MyPREFERENCES = "MyPrefs" ;
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    MainDatabaseHandler CancelDatabaseHandler;
    String nname;
    
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adm_land);
		CancelDatabaseHandler = new MainDatabaseHandler(this);
		//bookSetupAdapter=bookSetupAdapter.open();
		CancelDatabaseHandler.insert();  //Insert Setup List in SETUPLIST Table
		//CancelDatabaseHandler.check();
		addListenerOnButton();	
		final Context context = this;
		
		 // get the listview
		    expListView = (ExpandableListView) findViewById(R.id.lvExp);

		    // preparing list data
		    prepareListData();

		    listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		    // setting list adapter
		    expListView.setAdapter(listAdapter);
		       
		 // Listview Group click listener
		    expListView.setOnGroupClickListener(new OnGroupClickListener() {

		        @Override
		        public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {

		            return false;
		        }
		    });
		    
		    // Listview Group expanded listener
		    expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
		    	int previousItem = 0;

			    @Override
			    public void onGroupExpand(int groupPosition) {

			        if(groupPosition != previousItem )
			        	 expListView.collapseGroup(previousItem );
			        previousItem = groupPosition;
			    }
			});


		    // Listview Group collasped listener
		    expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

		        @Override
		        public void onGroupCollapse(int groupPosition) {
		        	
		        }
		    });
		    

		    // Listview on child click listener
		    expListView.setOnChildClickListener(new OnChildClickListener() {

		        @Override
		        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		            Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition)+ " : "+ listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
		            
		            // Booking Management Links
		            
		             if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Setup List"){
    	
				    	Intent intent = new Intent(context,setup_list.class);
				        startActivity(intent); 
				    }
		            
		             else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Book Setup"){
    	
				    	Intent intent = new Intent(context,book_setup.class);
				        startActivity(intent); 	
				    }
		            
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Cancel Booking"){
	    	
					    	Intent intent = new Intent(context,cancel_booking.class);
					        startActivity(intent); 
					} 
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit Booking"){
	    	
					    	Intent intent = new Intent(context,editBookingSetupList.class);
					        startActivity(intent);  	
					  } 
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Share Setup"){		
	    			
					    	Intent intent = new Intent(context,share_setup.class);		
					        startActivity(intent);		
					    }
		             
		             // Setup Management Links
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Add New Setup"){
	    	
					    	Intent intent = new Intent(context,add_setup.class);
					        startActivity(intent);  	
					  }
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Delete Setup"){
	    	
					    	Intent intent = new Intent(context,delete_setup.class);
					        startActivity(intent);  	
					  }
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Mark Status"){
	    	
					    	Intent intent = new Intent(context,mark_status.class);
					        startActivity(intent);  	
					  } 
		            
			       
		            // Rack Management Links
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Booked Rack Details"){
	    	
					    	Intent intent = new Intent(context,rack_list.class);
					        startActivity(intent); 	
					 }  
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Allocate Rack"){
	    	
					    	Intent intent = new Intent(context,allocate_rack.class);
					        startActivity(intent); 	
					 }
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "De-Allocate Rack"){
	    	
					    	Intent intent = new Intent(context,deallocate_rack.class);
					        startActivity(intent); 
					 }
		             
			         else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit Rack Allocation"){
	    	
					    	Intent intent = new Intent(context,edit_rackbk.class);
					        startActivity(intent); 
					    	
					 }
		             
		             //User Management Links
		             
		            else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Add New User"){
		            	
		            	Intent intent = new Intent(context,new_user.class);
                        startActivity(intent); 
		            }
		            
		            else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Remove User"){
    	
				    	Intent intent = new Intent(context,delete_user.class);
				        startActivity(intent); 
				    }
		             
		            else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "User Feedback"){
    	
				    	Intent intent = new Intent(context,feed_adm.class);
				        startActivity(intent);
				    }
		             
		            else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) == "Faulty Setups"){
    	
				    	Intent intent = new Intent(context,faulty_list.class);
				        startActivity(intent);
				    }
		            return false;
		        }
		    });
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_main, menu);
		return true;
	}
	
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) 
    {
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
	 	  nname=sharedpreferences.getString("nameKey", "Couldn't load");
	 	   
	 	  // Here splitting First name to display it on action bar with Hi remark
	 	  String[] temp = nname.split("@");		
		  String[] name= temp[0].split("\\.");
		  String fname="Hi "+name[0];
	 	   
        MenuItem menuItem = menu.findItem(R.id.action_name_ad);
        TextView showusername1 = (TextView) menuItem.getActionView();
        showusername1.setText(String.valueOf(fname) );

        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final Context context = this;
		int id = item.getItemId();
		if (id == R.id.action_name_ad) {
	    	  // user First Name
			return true;
		}
		else if (id == R.id.action_pwd_ad) {
		    Intent intent = new Intent(context,adm_chg_pwd.class);
            startActivity(intent);   
				return true;	
			}
		else if (id == R.id.action_rep_ad) {
			Intent intent = new Intent(context,report_list.class);
	        startActivity(intent);
				return true;	
			}
		else if (id == R.id.action_policy_ad) {
	    	  Intent intent = new Intent(context, adm_policy.class);
	    	  startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_about_ad) {
	    	  Intent intent = new Intent(context, adm_about_us.class);
	    	  startActivity(intent); 
			return true;	
		}
		else if (id == R.id.action_log_ad) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				alertDialogBuilder.setTitle("Confirm this");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Are u sure!!!!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {

							SharedPreferences sharedpreferences = getSharedPreferences
								      (MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
								      Editor editor = sharedpreferences.edit();
								      editor.clear();
								      editor.commit();
								      moveTaskToBack(true); 
								      adm_land.this.finish();
								      
							Intent intent = new Intent(context,admin_login.class);
	                        startActivity(intent);   
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
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void prepareListData() {
	    listDataHeader = new ArrayList<String>();
	    listDataChild = new HashMap<String, List<String>>();

	    // Adding child data
	    listDataHeader.add("Booking Management");
	    listDataHeader.add("Setup Management");
	    listDataHeader.add("Rack Management");
	    listDataHeader.add("User Management");

	    List<String> bookMGT = new ArrayList<String>();
	    bookMGT.add("Setup List");
	    bookMGT.add("Book Setup");
	    bookMGT.add("Edit Booking");
	    bookMGT.add("Cancel Booking");
	    bookMGT.add("Share Setup");
	    
	    List<String> setupMGT = new ArrayList<String>();
	    setupMGT.add("Add New Setup");
	    setupMGT.add("Delete Setup");
	    setupMGT.add("Mark Status");
	    
	    List<String> rackMGT = new ArrayList<String>();
	    rackMGT.add("Booked Rack Details");
	    rackMGT.add("Allocate Rack");
	    rackMGT.add("De-Allocate Rack");
	    rackMGT.add("Edit Rack Allocation");
	    
	    List<String> userMGT = new ArrayList<String>();
	    userMGT.add("Add New User");
	    userMGT.add("Remove User");
	    userMGT.add("User Feedback");
	    userMGT.add("Faulty Setups");
	   
	    listDataChild.put(listDataHeader.get(0), bookMGT); // Header, Child data
	    listDataChild.put(listDataHeader.get(1), setupMGT);
	    listDataChild.put(listDataHeader.get(2), rackMGT);
	    listDataChild.put(listDataHeader.get(3), userMGT);
	}

	
		public void addListenerOnButton(){
			
		final Context context = this;
		ibtn2 = (ImageButton) findViewById(R.id.imageButtonad2);
		
		// add button listener
		ibtn2.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context,notify.class);
                            startActivity(intent);   
			}
		});
		 
	}
		
		@Override
		public void onBackPressed() {
		     moveTaskToBack(true);		
		     // back pressed used at time of main screen, if its true will take you back
		}
}