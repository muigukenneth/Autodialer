package com.autodialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CheckNoteidService extends Service {
	 DatabaseHandler dbcali;
	 private RequestQueue mVolleyQueue2;
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.CheckNoteidService";
	  int stop;
	  String st;
	  int yourdata;
	  DatabaseHandlerHistory dbtwo ;
	public CheckNoteidService() {
	//	 super("Like Service");
	}
	//private RequestQueue mVolleyQueue;
private final String TAG_REQUEST = "MY_TAG";
//RequestQueue queue;
String dialogmessage;
	
	
@Override
public void onCreate() {
	super.onCreate();
		//super.onCreate();
// Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
 //Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();
	 mVolleyQueue2 = Volley.newRequestQueue(getApplicationContext()); 
	  dbtwo= new DatabaseHandlerHistory(getApplicationContext());
	 DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
  HashMap<String,String> user = new HashMap<String, String>();
  user = db.getUserDetails();
  dbcali = new DatabaseHandler(getApplicationContext());
 // ArrayList<Contact> contact_array_from_db = dbcali.Get_Contacts();
	// if(contact_array_from_db.size() > 0){
  yourdata = dbtwo.Get_Total_History();
	
  if(Integer.toString(yourdata)!=null)
  {	
	  if(yourdata==1){
		  stop=1;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==2){
		  stop=2;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==3){
		  stop=3;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==4){
		  stop=4;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==5){
		  stop=5;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==6){
		  stop=6;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==7){
		  stop=7;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==8){
		  stop=8;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==9){
		  stop=9;
		  SaveHistoryNoInternet();  
	  }
	  if(yourdata==10){
		  stop=10;
		  SaveHistoryNoInternet();  
	  }else{
		  StopService();  
	  }
  }else{
	  StopService();
  }
  
// makePostRequest();
 
}
public void SaveHistoryNoInternet(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(1);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	// Toast.makeText(getApplicationContext(),
		    		//			    "Note posted successfully", Toast.LENGTH_LONG).show();
		    	if(stop==1){
		    	
		    	dbtwo.delete();
		    	StopService();
		    	}else{
		    		SaveHistoryNoInternet2();	
		    	}
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
					//dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
					//dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
					//dialogmessage="No internet connection No Note not posted";
				}
		    
		    //	Toast.makeText(getApplicationContext(),
				//		     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet2(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(2);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	// Toast.makeText(getApplicationContext(),
		    	//				    "Note posted successfully", Toast.LENGTH_LONG).show();
		    	if(stop==2){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet3();	
			    	}
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
					//dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
					//dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
						//     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet3(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(3);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==3){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet4();	
			    	}
		    
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet4(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(4);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==4){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet5();	
			    	}
		    
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet5(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(5);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	if(stop==5){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet6();	
			    	}
		    
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet6(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(6);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	if(stop==6){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet7();	
			    	}
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet7(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(7);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==7){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet8();	
			    	}
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet8(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(8);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==8){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet9();	
			    	}
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet9(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(9);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==9){
		    		dbtwo.delete();
			    	StopService();
			    	}else{
			    		SaveHistoryNoInternet10();	
			    	}
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
public void SaveHistoryNoInternet10(){
	  String Name = "";
	   String Phone = "";
	   String Note = "";
	   String Email = "";
	   String Username = "";
	  Cursor cursor = dbtwo.Get_Note(10);  //cursor hold all your data
	  if(cursor!=null)
	  {
	    
		  cursor.moveToFirst();
		  Name = cursor.getString(1);
		  Phone = cursor.getString(2);
		  Note = cursor.getString(3);
		  Email = cursor.getString(4);
		  Username = cursor.getString(5);
		  
		  Log.i ("display",Name+Phone+Note+Email+Username);
		//  Toast.makeText(getApplicationContext(),
		//		    st, Toast.LENGTH_LONG).show();
	  }
	 
	
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",Email);
	  params.put("username",Username);
	  params.put("phone",Phone);
	  params.put("name",Name);
	  params.put("note",Note);
	// params.put("note",savenote.getText().toString());
	//  params.put("id",id);
	 
	// Toast.makeText(getApplicationContext(),
		//	    ""+params, Toast.LENGTH_LONG).show();
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/insert_saved_notes.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    //	savenote.setText("");
		    	//dialogmessage="Popup created check your web and write a note";
		    	
		    	if(stop==10){
			    	StopService();dbtwo.delete();
			    	StopService();
			    	}else{
			    		StopService();
			    	}
		    	
				// TakeNote.this.finish();
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
					//dialogmessage="No internet connection Note not posted";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="server error we are working on it Note not posted";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					//dialogmessage="Parse Error update";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
				//	dialogmessage="No internet connection No Note not posted";
				}
		    
		    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
		    	StopService();		
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
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
//intent.putExtra(FILEPATH, "yes");
//intent.putExtra("RESULT", result);
//sendBroadcast(intent);
	// publishResults("yap", result);
// }
private void publishResults(String outputPath, int result) {
	    
	  }
@Override
public void onDestroy() {
// Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
 
}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}

