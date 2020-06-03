package avi.aricent_ssr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import avi.aricent_ssr.R.id;
 
public class setup_list extends Activity {
	Button avlbtn,bkdbtn,allbtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_list);
		final Context context = this;
		avlbtn = (Button) findViewById(id.availbutton);
		bkdbtn = (Button) findViewById(id.bookedbutton);
		allbtn = (Button) findViewById(id.allbutton);
		
		avlbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,availableSetup.class);
                            startActivity(intent);   
			}
		});
		
		bkdbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,book_list.class);
                            startActivity(intent);   
			}
		});
		
		allbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,allSetupList.class);
                            startActivity(intent);   
			}
		});
	}
}	