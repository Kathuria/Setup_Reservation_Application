package avi.aricent_ssr;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
public class rack_list extends Activity {
	allocateRackAdapter allocateRackAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rack_list);
		TextView tv = (TextView) findViewById(R.id.rv);
		DeallocateDatabaseHandler db = new DeallocateDatabaseHandler(getApplicationContext());
        // Spinner Drop down elements
        List<MainGetSet> lables = db.getAllLabels();
        String log = "";

       if(lables.isEmpty()){
    	   tv.setText("No Booking is available");
       }
       else{
        for (MainGetSet cn : lables) {
        	log = log + " ID: "+cn.getID()+"\n NAME: " + cn.getName()+"\n MANAGER: " + cn.getmName()+"\n TEAM: " + cn.gettName()+" \n From: " + cn.getFrom()+"\n To: " + cn.getTo()+ "\n\n";
        }
        tv.setText(log);
       }
	}
}	