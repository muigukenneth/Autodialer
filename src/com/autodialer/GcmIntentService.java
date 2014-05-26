package com.autodialer;

/* http://techlovejump.in/2013/11/android-push-notification-using-google-cloud-messaging-gcm-php-google-play-service-library/
 * techlovejump.in
 * tutorial link
 * 
 *  */

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
	  JSONObject json;
	  int i;
	  String messageType;
	  String messo;
	public GcmIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String msg = intent.getStringExtra("message");
		
		try {
			json = new JSONObject(msg);
			String bridge = json.getString("switch");
			messo = json.getString("gcmText");
			  i=Integer.parseInt(bridge);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(i==1){
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		 messageType = gcm.getMessageType(intent);
		
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
	                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	                List<RunningTaskInfo> services = activityManager
	                        .getRunningTasks(Integer.MAX_VALUE);
	                boolean isActivityFound = false;

	                if (services.get(0).topActivity.getPackageName().toString()
	                        .equalsIgnoreCase( getApplicationContext().getPackageName().toString())) {
	                    isActivityFound = true;
	                }

	                if (isActivityFound) {
	                	 result = Activity.RESULT_OK;
                   	   Intent intentnoma = new Intent(NOTIFICATION);
                  		   // intent.putExtra(FILEPATH, outputPath);
                  		    intentnoma.putExtra(RESULT, result);
                  		  intentnoma.putExtra("message", msg);
                  		  Log.i(TAG, "Intent sent " + result);
                  		    sendBroadcast(intentnoma);
	                   // return null;
	                } else {
	                	  sendNotification(msg); 
	                         //   return true;
	                }
	              
	              //  return false;
	                Log.i(TAG, "Received: " + extras.toString());
	            }
	        }
		 GcmBroadcastReceiver.completeWakefulIntent(intent);
		}else if(i==2){
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
			 messageType = gcm.getMessageType(intent);
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
		               
		                sendNotificationNewUpdate(msg); 
		                         //   return true;
		                
		              
		              //  return false;
		                Log.i(TAG, "Received: " + extras.toString());
		            }
		        }
			 GcmBroadcastReceiver.completeWakefulIntent(intent);
		}
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
	
	private void sendNotificationNewUpdate(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        
     // pending intent is redirection using the deep-link
        Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setData(Uri.parse("http://dash.yt/salespacer/login.php"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.sales_launcher)
        .setContentTitle("New Update Available")
         .setDefaults(Notification.DEFAULT_ALL)
         .setAutoCancel(true)
       .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(messo))
        .setContentText(messo);

       // PendingIntent pending = PendingIntent.getActivity(this, 0, resultIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
      // notificationBuilder.setContentIntent(pending);

        // using the same tag and Id causes the new notification to replace an existing one
      //  mNotificationManager.notify(String.valueOf(System.currentTimeMillis()), PUSH, notificationBuilder.build());
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
	
}