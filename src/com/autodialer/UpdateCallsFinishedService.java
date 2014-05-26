package com.autodialer;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
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

public class UpdateCallsFinishedService extends Service {

	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.UpdateCallsFinishedService";

	public UpdateCallsFinishedService() {
	//	 super("Like Service");
	}
	private RequestQueue mVolleyQueue;
  private final String TAG_REQUEST = "MY_TAG";
 // RequestQueue queue;
	
	
	
  @Override
  public void onCreate() {
  	super.onCreate();
		//super.onCreate();
     // Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
      //Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();
  	 mVolleyQueue = Volley.newRequestQueue(getApplicationContext()); 
  	 DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
       HashMap<String,String> user = new HashMap<String, String>();
       user = db.getUserDetails();
       Map<String,String> params = new HashMap<String, String>();
       params.put("pid",user.get("email"));
 	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
 	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
 	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/statuscheck/update_callsfinished.php", params,
 			new Response.Listener<JSONObject>() {
 	    @Override
 	    public void onResponse(JSONObject response) {
 	    	
 	        // JSON Parsing
 	    //	Toast.makeText(getApplicationContext(),
			//		     "status sent", Toast.LENGTH_SHORT).show();
 	    	 // successfully finished
 	    //  result = Activity.RESULT_OK;
 	  // successfully finished
 	      result = Activity.RESULT_OK;
 	   Intent intent = new Intent(NOTIFICATION);
		   // intent.putExtra(FILEPATH, outputPath);
		    intent.putExtra(RESULT, result);
		    sendBroadcast(intent);
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
					Toast.makeText(getApplicationContext(),
						     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					Toast.makeText(getApplicationContext(),
						     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
				} else if( error instanceof NoConnectionError) {
					Toast.makeText(getApplicationContext(),
								     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					Toast.makeText(getApplicationContext(),
						      "connection timeout ", Toast.LENGTH_SHORT).show();
				
				}
				Intent intent = new Intent(NOTIFICATION);
			   // intent.putExtra(FILEPATH, outputPath);
			    intent.putExtra(RESULT, result);
			    sendBroadcast(intent);
				StopService();
 	    }
 	});
 	mVolleyQueue.add(request);
    //  makePostRequest();
      
  }
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
  	return super.onStartCommand(intent, flags, startId);
  }
  private void StopService() {
  	this.stopSelf();
  }
// private void makePostRequest() {
      //	 String post_message = comment.getText().toString();
      	// Create List of BasicNameValuePairs
	  
//	Intent intent = new Intent(NOTIFICATION);
 // intent.putExtra(FILEPATH, "yes");
 // intent.putExtra("RESULT", result);
//  sendBroadcast(intent);
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

