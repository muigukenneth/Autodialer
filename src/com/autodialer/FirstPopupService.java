package com.autodialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class FirstPopupService extends Service {
	 DatabaseHandler dbcali;
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String MESSAGE = "message";
	  public static final String NOTIFICATION = "com.autodialer.FirstPopupService";

	public FirstPopupService() {
	//	 super("Like Service");
	}
	private RequestQueue mVolleyQueue;
private final String TAG_REQUEST = "MY_TAG";
// RequestQueue queue;
String popupID;
String popupphone;
String popupname;
	String dialogmessage;
@Override
public void onCreate() {
	super.onCreate();
	
	
}

public int onStartCommand (Intent intent, int flags, int startId){

    popupID = intent.getStringExtra("ID");
    popupphone = intent.getStringExtra("PHONE");
    popupname = intent.getStringExtra("NAME");
    Kazi();
    return START_STICKY;
}
public void Kazi(){

	//super.onCreate();
// Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();
 mVolleyQueue = Volley.newRequestQueue(getApplicationContext()); 
 DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
 HashMap<String,String> user = new HashMap<String, String>();
 user = db.getUserDetails();
 dbcali = new DatabaseHandler(getApplicationContext());
 ArrayList<Contact> contact_array_from_db = dbcali.Get_Contacts();
// if(contact_array_from_db.size() > 0){
	  
 
 int text = contact_array_from_db.get(0).getID();
 String mobile = contact_array_from_db.get(0).getPhoneNumber();
 String name = contact_array_from_db.get(0).getName();
 //String  = contact_array_from_db.get(0).getName();
 
  
 Map<String,String> params = new HashMap<String, String>();
 params.put("pid",user.get("email"));
 params.put("name",popupname);
 params.put("phone",popupphone);
 params.put("num",""+popupID);
 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/statuscheck/popup_status.php", params,
		new Response.Listener<JSONObject>() {
    @Override
    public void onResponse(JSONObject response) {
    	dialogmessage="Popup created check your web and write a note";

//Intent dialogIntent = new Intent(getBaseContext(), PopupActivity.class);
//Bundle b = new Bundle();
//b.putString("MESSAGE", dialogmessage);  
//dialogIntent.putExtras(b);
//dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//getApplication().startActivity(dialogIntent);


    	
        // JSON Parsing
    	//Toast.makeText(getApplicationContext(),
		//		     "status sent", Toast.LENGTH_SHORT).show();
    	 // successfully finished
    //  result = Activity.RESULT_OK;
  // successfully finished
    	
    	
     // result = Activity.RESULT_OK;
  // Intent intent = new Intent(NOTIFICATION);
	   // intent.putExtra(FILEPATH, outputPath);
	 //   intent.putExtra(MESSAGE, dialogmessage);
	  //  intent.putExtra(RESULT, result);
	  //  sendBroadcast(intent);
    	StopService();
    	// makeSampleHttpRequest();
    	//  pDialog.dismiss();
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
    
    	// pDialog.dismiss();
        // Error handling
    	// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
			// For AuthFailure, you can re login with user credentials.
			// For ClientError, 400 & 401, Errors happening on client side when sending api request.
			// In this case you can check how client is forming the api and debug accordingly.
			// For ServerError 5xx, you can do retry or handle accordingly.
			if( error instanceof NetworkError) {
				//Toast.makeText(getApplicationContext(),
					 //    " Check your internet connection ", Toast.LENGTH_SHORT).show();
				dialogmessage="Your phone doesnt have internet connection ,no popup will be displayed on the web";
			} else if( error instanceof ClientError) { 
			} else if( error instanceof ServerError) {
				//Toast.makeText(getApplicationContext(),
					//     "Server error ", Toast.LENGTH_SHORT).show();
				dialogmessage="server error we are working on it ";
				
			} else if( error instanceof AuthFailureError) {
			} else if( error instanceof ParseError) {
				dialogmessage="parse error we are working on it ";
			} else if( error instanceof NoConnectionError) {
				//Toast.makeText(getApplicationContext(),
				//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				dialogmessage="Your phone doesnt have internet connection, no popup will be displayed on the web";
			} else if( error instanceof TimeoutError) {
				//Toast.makeText(getApplicationContext(),
					 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				dialogmessage="Your phone doesnt have internet connection ,no popup will be displayed on the web";
			}
			//x1 Intent dialogIntent = new Intent(getBaseContext(), PopupActivity.class);
	    	//x1 Bundle b = new Bundle();
	    	//x1 b.putString("MESSAGE", dialogmessage);  
	    	//x1 dialogIntent.putExtras(b);
	    	//x1 dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	//x1 getApplication().startActivity(dialogIntent);
			
			
			//Intent intent = new Intent(NOTIFICATION);
		   // intent.putExtra(FILEPATH, outputPath);
		   // intent.putExtra(MESSAGE, dialogmessage);
		  //  intent.putExtra(RESULT, result);
		  // sendBroadcast(intent);
			StopService();
			
    }
});
//mVolleyQueue.add(request);
//  makePostRequest();
//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
request.setTag(TAG_REQUEST);	
		mVolleyQueue.add(request);
}
private void StopService() {
	this.stopSelf();
}
//private void makePostRequest() {
    //	 String post_message = comment.getText().toString();
    	// Create List of BasicNameValuePairs
	  
//	Intent intent = new Intent(NOTIFICATION);
// intent.putExtra(FILEPATH, "yes");
// intent.putExtra("RESULT", result);
//sendBroadcast(intent);
	// publishResults("yap", result);
//    }
private void publishResults(String outputPath, int result) {
	    
	  }
@Override
public void onDestroy() {
 //   Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    
}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}


