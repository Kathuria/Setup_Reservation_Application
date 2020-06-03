package avi.aricent_ssr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import avi.aricent_ssr.R.id;

public class setup_list_usr extends Activity {
	Button avlbtn,bkdbtn,allbtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_list_usr);
		final Context context = this;
		avlbtn = (Button) findViewById(id.availbuttonU);
		bkdbtn = (Button) findViewById(id.bookedbuttonU);
		allbtn = (Button) findViewById(id.allbuttonU);
		
		avlbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,availableSetup_usr.class);
                            startActivity(intent);   
			}
		});
		
		bkdbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,book_list_usr.class);
                            startActivity(intent);   
			}
		});
		
		allbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context,allSetupList_usr.class);
                            startActivity(intent);   
			}
		});
       }
}	