package com.autodialer;

/* http://techlovejump.in/2013/11/android-push-notification-using-google-cloud-messaging-gcm-php-google-play-service-library/
 * techlovejump.in
 * tutorial link
 * 
 *  */

import java.util.Iterator;
import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService{
	Context context;
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCM Demo";
    private int result = Activity.RESULT_CANCELED;
    public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.GcmIntentService";
	public GcmIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String msg = intent.getStringExtra("message");
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		
		 if (!extras.isEmpty()) {
			 
			 if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                sendNotification("Send error: " + extras.toString());
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_DELETED.equals(messageType)) {
	                sendNotification("Deleted messages on server: " +
	                        extras.toString());
	            // If it's a regular GCM message, do some work.
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	                // This loop represents the service doing some work.
	                for (int i=0; i<5; i++) {
	                    Log.i(TAG, "Working... " + (i+1)
	                            + "/5 @ " + SystemClock.elapsedRealtime());
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e) {
	                    }
	                }
	                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
	                // Post notification of received message.
	                //sendNotification("Received: " + extras.toString());
	              //  sendNotification(msg);
	                ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	                List<RunningAppProcessInfo> l = mActivityManager
	                        .getRunningAppProcesses();
	                Iterator<RunningAppProcessInfo> i = l.iterator();
	                while (i.hasNext()) {
	                    RunningAppProcessInfo info = i.next();

	                    if (info.uid == getApplicationInfo().uid && info.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) 
	                        {
	                    	   result = Activity.RESULT_OK;
	                     	   Intent intentnoma = new Intent(NOTIFICATION);
	                    		   // intent.putExtra(FILEPATH, outputPath);
	                    		    intentnoma.putExtra(RESULT, result);
	                    		    sendBroadcast(intentnoma);
	                         //   return true;
	                       }
	                   }
	                sendNotification(msg);
	              //  return false;
	                Log.i(TAG, "Received: " + extras.toString());
	            }
	        }
		 GcmBroadcastReceiver.completeWakefulIntent(intent);
	}





	private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Intent myintent = new Intent(this, MainControl.class);
        myintent.putExtra("message", msg);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		myintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.sales_launcher)
        .setContentTitle("You initiated a call from our Website")
         .setDefaults(Notification.DEFAULT_ALL)
         .setAutoCancel(true)
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText("click here to start the call"))
        .setContentText("click here to start the call");

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
	
}