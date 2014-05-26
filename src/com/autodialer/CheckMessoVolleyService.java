package com.autodialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
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

public class CheckMessoVolleyService extends Service {
	private static final String LOG = "AUTO-DAILER-DEBBUG";
	ArrayList<ItemParse> arrayOfListnew;
	  private static final String ARRAY_NAME = "message";
	  private static final String INDEX = "Message";
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.CheckMessoVolleyService";
	  
	  
	  public static int tabService=0;
	    Vibrator vibrator;
	    int NOTIFY_ID=0;
	    String Oldrid="";
	    boolean notificationflag=false;
	    int lastlength=0;
	    private Timer timer1=new Timer();    

	    private static long UPDATE_INTERVAL = 1*5*1000;  //default


	    Context ctx;
	    private static Timer timer = new Timer();
	 // HashMap<String,String> user;
	//  DatabaseHandlerUser dbtwo ;
	  AlertDialog.Builder builder;
	  AlertDialog alert;
	public CheckMessoVolleyService() {
	//	 super("Like Service");
	}
	private RequestQueue mVolleyQueue;
private final String TAG_REQUEST = "MY_TAG";
// RequestQueue queue;
	
private RequestQueue mVolleyQueue2;	
	
@Override
public void onCreate() {
	super.onCreate();
	 mVolleyQueue2 = Volley.newRequestQueue(CheckMessoVolleyService.this); 
	 arrayOfListnew = new ArrayList<ItemParse>();
	 Dailingmainmethod();
	// startService();
		//super.onCreate();
	// dbtwo= new DatabaseHandlerUser(CheckMessoVolleyService.this);
	   // user = new HashMap<String, String>();
	    // user = dbtwo.getUserDetails();
	    
    
}
private void startService()
{      
    timer.scheduleAtFixedRate(    

            new TimerTask() {

                public void run() {

                	//Dailingmainmethod(); 

                }
            }, 1000,UPDATE_INTERVAL);
    Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
}



public void Dailingmainmethod() {
	DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
    HashMap<String,String> user = new HashMap<String, String>();
    user = db.getUserDetails();
    Map<String,String> params = new HashMap<String, String>();
    params.put("pid",user.get("email"));
	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/message/messostatuscheck.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	//parseFlickrImageResponse(response);
	    //	makeSampleHttpRequest2();
	    	parseFlickrImageResponse(response);
	        // JSON Parsing
	    	//Toast.makeText(getActivity(),
				//	     "doing the dirty work", Toast.LENGTH_SHORT).show();
	    	 // successfully finished
	    //  result = Activity.RESULT_OK;
	  // successfully finished
	   
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
					Toast.makeText(CheckMessoVolleyService.this,
						     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					Toast.makeText(CheckMessoVolleyService.this,
						     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
				} else if( error instanceof NoConnectionError) {
					Toast.makeText(CheckMessoVolleyService.this,
								     "no internet connection ", Toast.LENGTH_SHORT).show();
					//handle all errors as fast as possible
				} else if( error instanceof TimeoutError) {
					Toast.makeText(CheckMessoVolleyService.this,
						      "connection timeout ", Toast.LENGTH_SHORT).show();
				
				}
				Intent intent = new Intent(NOTIFICATION);
  			   // intent.putExtra(FILEPATH, outputPath);
  			    intent.putExtra("result", 9);
  			    sendBroadcast(intent);
  				StopService();
   	 
  			 // shutdownService();
			//	Progressbar.setVisibility(View.GONE);
	    }
	});
	mVolleyQueue2.add(request);
 //  makePostRequest();
   

}
private void parseFlickrImageResponse(JSONObject response) {//throws JSONException {
	
	//if(response.has("nyc")) {
		try {
			//dbHandler.delete();
			//JSONObject photos = response.getJSONObject("success");
			// Get the data array containing posts
            JSONArray items = response.getJSONArray(ARRAY_NAME);
			//JSONArray items = photos.getJSONArray("posts");

			arrayOfListnew.clear();
			
			for(int index = 0 ; index < items.length(); index++) {
			
				JSONObject jsonObj = items.getJSONObject(index);
				
				//String farm = jsonObj.getString("username");
			//	String id = jsonObj.getString("post_id");
			//	String secret = jsonObj.getString("secret");
				//String server = jsonObj.getString("server");
				
				//String imageUrl = "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + "_t.jpg";
			//	String imageUrl =jsonObj.getString("title");
				
			//	ItemParse model = new ItemParse();
				//model.setImageUrl(imageUrl);
				//model.setTitle(jsonObj.getString("message"));
			//	model.setId(jsonObj.getInt(ID));
				//model.setName(jsonObj.getString(NAME));
				//model.setSecondName(jsonObj.getString(SECOND_NAME));
				//model.setEmail(jsonObj.getString(EMAIL));
				//model.setPhone(jsonObj.getInt(PHONE));
				  String res = jsonObj.getString(INDEX);
				  int i=Integer.parseInt(res);
                  if(i == 1){
                	//  Toast.makeText(getApplicationContext(),
                    //          "Message Equals 1", Toast.LENGTH_LONG).show();
                  //	 Toast.makeText(getApplicationContext(),
                      //        "Message Equals 1", Toast.LENGTH_LONG).show();
                  	 //result = Activity.RESULT_CANCELED;
                  	Intent intent = new Intent(NOTIFICATION);
     			   // intent.putExtra(FILEPATH, outputPath);
     			    intent.putExtra("result", i);
     			    sendBroadcast(intent);
     			   Intent intentnoma=new Intent(CheckMessoVolleyService.this,CallingService.class);
     				CheckMessoVolleyService.this.startService(intentnoma);
     				StopService();
     				//shutdownService();
                  //	 pDialog.dismiss();
                     // pDialog.setMessage("Loading User Space");
                     // pDialog.setTitle("Getting Data");
                    //  DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
                    //  JSONObject json_user = json.getJSONObject("user");
                      /**
                       * Clear all previous data in SQlite database.
                       **/
                     // UserFunctions logout = new UserFunctions();
                     // logout.logoutUser(getApplicationContext());
                     // db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                    
                  //	Intent intent=new Intent(Login.this,MyLikeService.class);
          		//	Login.this.startService(intent);	
                    //  Intent myIntent = new Intent(Login.this, MainControl.class);
                    //  startActivityForResult(myIntent, 0);
                    //   finish();
             	  // finish();
                     /**
                      *If JSON array details are stored in SQlite it launches the User Panel.
                      **/
                    //  Intent upanel = new Intent(getApplicationContext(), Main.class);
                    ///  upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //  pDialog.dismiss();
                    //  startActivity(upanel);
                      /**
                       * Close Login Screen
                       **/
                    //  finish();
                  }
                  	 else if (i == 2){
                  		 
                  	//	 Toast.makeText(getApplicationContext(),
                  		//Toast.makeText(getApplicationContext(),
                          //      "Message Equals 2", Toast.LENGTH_LONG).show();
                  		// result = Activity.RESULT_OK;
                  		Intent intent = new Intent(NOTIFICATION);
         			   // intent.putExtra(FILEPATH, outputPath);
         			    intent.putExtra("result", i);
         			    sendBroadcast(intent);
         				StopService();
         			  // shutdownService();
                  		 //pDialog.dismiss();
                  	 }
                  else if(i == 3){
                  //	Toast.makeText(getApplicationContext(),
                      //        "Message Equals 3", Toast.LENGTH_LONG).show();
                  	// result = Activity.RESULT_OK;
                  	Intent intent = new Intent(NOTIFICATION);
     			   // intent.putExtra(FILEPATH, outputPath);
     			    intent.putExtra("result", res);
     			    sendBroadcast(intent);
     				StopService();
      	 
     			  //shutdownService();
                     // pDialog.dismiss();
                    //  loginErrorMsg.setText("Incorrect username/password");
                  }
				
				
				// mobile = model.getSecondName();
				//mobiletwo = arrayOfList.get(1);
				// mobile = secondname;
				 //String mobileon = arrayOfList.;
				Log.i(LOG, "===== inawork hadi hapa");
			//	Log.i(LOG, mobile);
				//test.setText(mobile);
				//Toast.makeText(getApplicationCont(),
				//	     "json impatikana", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
}
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
	//return super.onStartCommand(intent, flags, startId);
	 return START_STICKY;
}
private void StopService() {
	this.stopSelf();
}
private void shutdownService()
{
    if (timer != null) timer.cancel();
    Log.i(getClass().getSimpleName(), "Timer stopped...");
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
	shutdownService();  
}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
