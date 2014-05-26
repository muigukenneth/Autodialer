package com.autodialer;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
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

public class LastStopService extends Service {
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.LastStopService";
	  int i;
	  int intminus;
	  SharedPreferences myPrefsnow;
	public LastStopService() {
	//	 super("Like Service");
	}
	private RequestQueue mVolleyQueue;
private final String TAG_REQUEST = "MY_TAG";
// RequestQueue queue;
	
	
	
@Override
public void onCreate() {
	super.onCreate();
	 myPrefsnow = PreferenceManager.getDefaultSharedPreferences(this);
		//super.onCreate();
   // Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
    //Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();
   mVolleyQueue = Volley.newRequestQueue(getApplicationContext()); 
   DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
   DatabaseHandler dbother = new DatabaseHandler(getApplicationContext());
   HashMap<String,String> user = new HashMap<String, String>();
   user = db.getUserDetails();
    Map<String,String> params = new HashMap<String, String>();
    int visible = myPrefsnow.getInt("num", 1);
    if(visible>=1){
    String yourdata = dbother.getDetailsreverse(Integer.toString(visible));
	
	  if(yourdata!=null)
	  {
	i=Integer.parseInt(yourdata);
	
	 intminus=i;
	//SharedPreferences.Editor editor = myPrefsnow.edit();
	//editor.putInt("num", intminus);
	//editor.commit(); 
	//Toast.makeText(MainControl.this,
	 //   ""+intminus, Toast.LENGTH_LONG).show();
	  }
    }else{
    	intminus=1;
    	SharedPreferences.Editor editor = myPrefsnow.edit();
		editor.putInt("num", 1);
		editor.commit(); 
    }
    params.put("pid",user.get("email"));
    params.put("identify",""+intminus);
	// params.put("title","http://www.globegokartshows.co.ke/api/eventuserpp/"+"Kenneth"+".png");
	// params.put("message","1");
	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/statuscheck/laststop.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	
	        // JSON Parsing
	    	//Toast.makeText(getApplicationContext(),
				//	     "status changed", Toast.LENGTH_SHORT).show();
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
				//	Toast.makeText(getApplicationContext(),
					///    " Check your internet connection laststop ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
				//	Toast.makeText(getApplicationContext(),
					//	     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
				//	Toast.makeText(getApplicationContext(),
					//	     "parse error laststop", Toast.LENGTH_SHORT).show();
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
					//	      "internet connection too slow  ", Toast.LENGTH_SHORT).show();
				
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
