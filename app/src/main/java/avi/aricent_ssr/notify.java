package avi.aricent_ssr;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//Class can be used later to send notifications, For now it can send notific ation to 1 device and can also update & cancel that notification.

public class notify extends Activity {
   private NotificationManager mNotificationManager;
   private int notificationID = 100;
   private int numMessages = 0;
   EditText edt;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.notify);
      edt = (EditText)findViewById(R.id.editText1);
      Button startBtn = (Button) findViewById(R.id.start);
      startBtn.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {
            displayNotification();
         }
      });

      Button cancelBtn = (Button) findViewById(R.id.cancel);
      cancelBtn.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {
            cancelNotification();
         }
      });
   
      Button updateBtn = (Button) findViewById(R.id.update);
      updateBtn.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {
            updateNotification();
         }
      });
   }
   
   protected void displayNotification() {
	      Log.i("Start", "notification");
	      /* Invoking the default notification service */
	      NotificationCompat.Builder  mBuilder = 
	      new NotificationCompat.Builder(this);	
	      String notif=edt.getText().toString();
	      mBuilder.setContentTitle("Aricent SSR Message");
	      mBuilder.setContentText(notif);
	      mBuilder.setTicker("New Message Alert!");
	      mBuilder.setSmallIcon(R.drawable.ari_launcher);
	      
	      /* Increase notification number every time a new notification arrives */
	      mBuilder.setNumber(++numMessages);
	      mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	      
	      /* notificationID allows you to update the notification later on. */
	      mNotificationManager.notify(notificationID, mBuilder.build());
	   }
   protected void cancelNotification() {
      Log.i("Cancel", "notification");
      mNotificationManager.cancel(notificationID);
   }

   protected void updateNotification() {
      Log.i("Update", "notification");
      /* Invoking the default notification service */
      NotificationCompat.Builder  mBuilder = 
      new NotificationCompat.Builder(this);	
      String notif=edt.getText().toString();
      mBuilder.setContentTitle("Updated Message");
      mBuilder.setContentText(notif);
      mBuilder.setTicker("Updated Message Alert!");
      mBuilder.setSmallIcon(R.drawable.ari_launcher);

     /* Increase notification number every time a new notification arrives */
      mBuilder.setNumber(++numMessages);
      
      mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /* Update the existing notification using same notification ID */
      mNotificationManager.notify(notificationID, mBuilder.build());
   }
}