package com.autodialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
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

public class GetContactsService extends Service {
	private static final String LOG = "AUTO-DAILER-DEBBUG";
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.samir.MyLikeService";
	  List<ItemParse> arrayOfList;
	  private static final String ARRAY_NAME = "contacts";
		private static final String ID = "id_identifier";
		private static final String NAME = "name";
		//event venue
		// static  String PROFILE = "event image";
		private static final String PHONE = "phone";
		private static final String EMAIL = "Savedemail";
		private static final String SECOND_NAME = "Secondname";
		//private static final String BIRTH_DATE = "event cash";
		//private static final String EVENT_GOOGLE = "event location";
		//private static final String EVENT_TIME = "event time";
	public GetContactsService() {
	//	 super("Like Service");
	}
	private RequestQueue mVolleyQueue;
private final String TAG_REQUEST = "MY_TAG";
// RequestQueue queue;FILE
	
	
	
@Override
public void onCreate() {
	super.onCreate();
	arrayOfList = new ArrayList<ItemParse>();
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
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/Testing/message/messagestatus.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	parseFlickrImageResponse(response);
	        // JSON Parsing
	    	Toast.makeText(getApplicationContext(),
					     "username sent", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(getApplicationContext(),
						     "client error ", Toast.LENGTH_SHORT).show();
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
						      "connection timeout check your internet connection ", Toast.LENGTH_SHORT).show();
				
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
private void parseFlickrImageResponse(JSONObject response) {//throws JSONException {
	
	//if(response.has("nyc")) {
		try {
			//JSONObject photos = response.getJSONObject("success");
			// Get the data array containing posts
            JSONArray items = response.getJSONArray(ARRAY_NAME);
			//JSONArray items = photos.getJSONArray("posts");

			arrayOfList.clear();
			
			for(int index = 0 ; index < items.length(); index++) {
			
				JSONObject jsonObj = items.getJSONObject(index);
				
				//String farm = jsonObj.getString("username");
			//	String id = jsonObj.getString("post_id");
			//	String secret = jsonObj.getString("secret");
				//String server = jsonObj.getString("server");
				
				//String imageUrl = "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + "_t.jpg";
			//	String imageUrl =jsonObj.getString("title");
				
				ItemParse model = new ItemParse();
				//model.setImageUrl(imageUrl);
				//model.setTitle(jsonObj.getString("message"));
				model.setId(jsonObj.getInt(ID));
				model.setName(jsonObj.getString(NAME));
				model.setSecondName(jsonObj.getString(SECOND_NAME));
				model.setEmail(jsonObj.getString(EMAIL));
				model.setPhone(jsonObj.getInt(PHONE));
			//	model.setBirthdate(jsonObj.getString(BIRTH_DATE));
			//	model.setEventLocation(jsonObj.getString(EVENT_GOOGLE));
			//	model.setEventVenue(jsonObj.getString(EVENT_VENUE));
				//model.setEventTime(jsonObj.getString(EVENT_TIME));
				arrayOfList.add(model);
				Log.i(LOG, "===== inawork hadi hapa");
				//Toast.makeText(getApplicationContext(),
				//	     "json impatikana", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
