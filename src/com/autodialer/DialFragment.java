package com.autodialer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;


import android.R.array;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DialFragment extends SherlockFragment  {
	TextView login;
	ListView listnew;
	TextView test;
	String mobile;
	ItemParse mobiletwo;
	private SpeedScrollListener listener;
	private static final String LOG = "AUTO-DAILER-DEBBUG";
	ArrayList<ItemParse> arrayOfList;
	  private static final String ARRAY_NAME = "contacts";
		private static final String ID = "id";
		private static final String NAME = "name";
		//event venue
		// static  String PROFILE = "event image";
		private static final String PHONE = "phone";
		private static final String EMAIL = "Savedemail";
		private static final String SECOND_NAME = "Secondname";
		private RequestQueue mVolleyQueue2;
		private DailingNumbersAdapter plusAdapter;
		private final String TAG_REQUEST = "MY_TAG";
		JsonObjectRequest jsonObjRequesttwo;
		 HashMap<String,String> user;
		 DatabaseHandlerUser db ;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	setHasOptionsMenu(true);
       // if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
         //   return null;
      //  }
       // View mLinearLayout = mFadingHelper.createView(inflater);
    //    if (mArguments != null){
    ////        ImageView img = (ImageView) mLinearLayout.findViewById(R.id.image_header);
      //      img.setImageResource(mArguments.getInt(ARG_IMAGE_RES));
       // }
        LinearLayout mLinearLayout =  (LinearLayout)inflater.inflate(R.layout.dial,container, false); 
    
		//super.onCreate();
   // Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
    //Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();
	
        db= new DatabaseHandlerUser(getActivity());
       user = new HashMap<String, String>();
        user = db.getUserDetails();
       login = (TextView) mLinearLayout.findViewById(R.id.textwelcome);
       test = (TextView) mLinearLayout.findViewById(R.id.textViewno);
       
       listnew = (ListView) mLinearLayout.findViewById(R.id.list);
       listnew.setEmptyView(mLinearLayout.findViewById(R.id.empty));
        login.setText("Welcome  "+user.get("uname"));
        listener = new SpeedScrollListener();
		  listnew.setOnScrollListener(listener);
        return mLinearLayout;
      	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		
		    arrayOfList = new ArrayList<ItemParse>();
		    mVolleyQueue2 = Volley.newRequestQueue(getActivity()); 
		   // Dailingmainmethod();
		   // new NetCheck().execute();	
		  //  makeSampleHttpRequest2();
		   
		  //  Dailingmainmethod();
		    plusAdapter = new DailingNumbersAdapter(getActivity(),listener,
					arrayOfList);
		    
	   		listnew.setAdapter(plusAdapter);
	   	 //String mobileon = arrayOfList.get(1).getName();
		  //  Log.i(LOG, mobileon);
		    Intent intent=new Intent(getActivity(),CheckMessageService.class);
			getActivity().startService(intent);
		//	 Intent intentnew=new Intent(getActivity(),GetContactsService.class);
		//		getActivity().startService(intentnew);
	}
public void makeSampleHttpRequest2() {
		
		//String url = "http://api.flickr.com/services/rest";
		String url = "http://www.globegokartshows.co.ke/Testing/message/messagestatus.php";
		//Uri.Builder builder = Uri.parse(url).buildUpon();
		//builder.appendQueryParameter("api_key", "5e045abd4baba4bbcd866e1864ca9d7b");
		//builder.appendQueryParameter("method", "flickr.interestingness.getList");
		//builder.appendQueryParameter("format", "json");
		//builder.appendQueryParameter("nojsoncallback", "1");
		
		// builder.toString(),
		jsonObjRequesttwo = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					parseFlickrImageResponse(response);
					//plusAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
					showToast("JSON parse error");
				}
				Toast.makeText(getActivity(),
					     "doing the dirty work", Toast.LENGTH_SHORT).show();
			//	loading.setVisibility(View.GONE);
			//	stopProgress();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
				// For AuthFailure, you can re login with user credentials.
				// For ClientError, 400 & 401, Errors happening on client side when sending api request.
				// In this case you can check how client is forming the api and debug accordingly.
				// For ServerError 5xx, you can do retry or handle accordingly.
				if( error instanceof NetworkError) {
					Toast.makeText(getActivity(),
						      " Check your internet connection", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
					Toast.makeText(getActivity(),
						      "Client error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ServerError) {
					Toast.makeText(getActivity(),
						      "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					Toast.makeText(getActivity(),
						      "Parse Error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof NoConnectionError) {
					Toast.makeText(getActivity(),
								      "no internet connection", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					Toast.makeText(getActivity(),
						      "connection timeout", Toast.LENGTH_SHORT).show();
				}
				
				//stopProgress();
				//showToast(error.getMessage());
			}
		});
		
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
		jsonObjRequesttwo.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequesttwo.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(jsonObjRequesttwo);
	}
private void showToast(String msg) {
	Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
}

	private class NetCheck extends AsyncTask<String,String,Boolean>
	  {
	      private ProgressDialog nDialog;

	      @Override
	      protected void onPreExecute(){
	          super.onPreExecute();
	          nDialog = new ProgressDialog(getActivity());
	          nDialog.setTitle("Checking Network");
	          nDialog.setMessage("Loading..");
	          nDialog.setIndeterminate(false);
	        nDialog.setCancelable(true);
	          nDialog.show();
	      }
	      /**
	       * Gets current device state and checks for working internet connection by trying Google.
	      **/
	      @Override
	      protected Boolean doInBackground(String... args){



	          ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	          NetworkInfo netInfo = cm.getActiveNetworkInfo();
	          if (netInfo != null && netInfo.isConnected()) {
	              try {
	                  URL url = new URL("http://www.google.com");
	                  HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	                  urlc.setConnectTimeout(3000);
	                  urlc.connect();
	                  if (urlc.getResponseCode() == 200) {
	                      return true;
	                  }
	              } catch (MalformedURLException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	              } catch (IOException e) {
	                  // TODO Auto-generated catch block
	                  e.printStackTrace();
	              }
	          }
	          return false;

	      }
	      @Override
	      protected void onPostExecute(Boolean th){

	          if(th == true){
	              nDialog.dismiss();
	              new ProcessLogin().execute();
	          }
	          else{
	             nDialog.dismiss();
	             // loginErrorMsg.setText("Error in Network Connection");
	          }
	      }
	  }

	  /**
	   * Async Task to get and send data to My Sql database through JSON respone.
	   **/
	  private class ProcessLogin extends AsyncTask<String, String, JSONObject> {


	      private ProgressDialog pDialog;

	      String email,password;

	      @Override
	      protected void onPreExecute() {
	          super.onPreExecute();
	         // DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
	        //  HashMap<String,String> user = new HashMap<String, String>();
	        //  user = db.getUserDetails();
	         // inputEmail = (EditText) findViewById(R.id.email);
	         // inputPassword = (EditText) findViewById(R.id.pword);
	          email = user.get("email");
	          password = user.get("uname");
	      //    pDialog = new ProgressDialog(CheckMessageService.this);
	      //    pDialog.setTitle("Contacting Servers");
	      //    pDialog.setMessage("Logging in ...");
	       //   pDialog.setIndeterminate(false);
	      //    pDialog.setCancelable(true);
	       //   pDialog.show();
	      }

	      @Override
	      protected JSONObject doInBackground(String... args) {

	          UserFunctionsNumbers userFunction = new UserFunctionsNumbers();
	          JSONObject json = userFunction.loginUser(email, password);
	          return json;
	      }

	      @Override
	      protected void onPostExecute(JSONObject json) {
	          try {
	        	//  String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
	        	  String res = json.getString(ARRAY_NAME);
	        	  JSONObject jsonObject = new JSONObject(res);
	        	 // JSONObject jsontwo = new JSONObject(json);
	            // if (json.getString(KEY_SUCCESS) != null) {
	        	  JSONArray items = jsonObject.getJSONArray(ARRAY_NAME);
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
					//	Log.i(LOG, "===== inawork hadi hapa");
	              //   // String res = json.getString(KEY_SUCCESS);

	                //  if(Integer.parseInt(res) == 1){
	                  	// Toast.makeText(getApplicationContext(),
	                     //          "Message Equals 1", Toast.LENGTH_LONG).show();
	                  	
	      	 
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
	                 // }
	                  //	 else if (Integer.parseInt(res) == 2){
	                  		 
	                  
	                  		 //pDialog.dismiss();
	                //  	 }
	                //  else if(Integer.parseInt(res) == 3){
	               
	      	 

	                      pDialog.dismiss();
	                    //  loginErrorMsg.setText("Incorrect username/password");
	               //   }
	             }
	          } catch (JSONException e) {
	              e.printStackTrace();
	          }
	     }
	  }
	public void Dailingmainmethod() {
		
	     Map<String,String> params = new HashMap<String, String>();
	     params.put("pid",user.get("email"));
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/Testing/message/messagestatus.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    	//parseFlickrImageResponse(response);
		    	makeSampleHttpRequest2();
		        // JSON Parsing
		    	Toast.makeText(getActivity(),
						     "doing the dirty work", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(getActivity(),
							     " Check your internet connection ", Toast.LENGTH_SHORT).show();
					} else if( error instanceof ClientError) { 
					} else if( error instanceof ServerError) {
						Toast.makeText(getActivity(),
							     "Server error ", Toast.LENGTH_SHORT).show();
					} else if( error instanceof AuthFailureError) {
					} else if( error instanceof ParseError) {
					} else if( error instanceof NoConnectionError) {
						Toast.makeText(getActivity(),
									     "no internet connection ", Toast.LENGTH_SHORT).show();
					} else if( error instanceof TimeoutError) {
						Toast.makeText(getActivity(),
							      "connection timeout ", Toast.LENGTH_SHORT).show();
					
					}
				
		    }
		});
	//	mVolleyQueue.add(request);
	  //  makePostRequest();
	    
	
	}
	@Override
	public void onResume() {
	    super.onResume();
	//   getActivity(). registerReceiver(receiver, new IntentFilter(MyLikeService.NOTIFICATION));
	//   getActivity(). registerReceiver(receiver2, new IntentFilter(UnlikeService.NOTIFICATION));
	  }
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void onStop() {
		super.onStop();
		//getActivity().unregisterReceiver(receiver);
		//getActivity().unregisterReceiver(receiver2);
		// Keep the list of requests dispatched in a List<Request<T>> mRequestList;
		/*
		 for( Request<T> req : mRequestList) {
		 	req.cancel();
		 }
		 */
		//jsonObjRequest.cancel();
		//( or )
		mVolleyQueue2.cancelAll(TAG_REQUEST);
		
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
					  String name = jsonObj.getString(NAME);
	                    String secondname = jsonObj.getString(SECOND_NAME);
	                    String  email = jsonObj.getString(EMAIL);
	                    String  phone =jsonObj.getString(PHONE);
				//	model.setBirthdate(jsonObj.getString(BIRTH_DATE));
				//	model.setEventLocation(jsonObj.getString(EVENT_GOOGLE));
				//	model.setEventVenue(jsonObj.getString(EVENT_VENUE));
					//model.setEventTime(jsonObj.getString(EVENT_TIME));
					arrayOfList.add(model);
					DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
					 dbHandler.Add_Contact(new Contact(name+""+secondname,
			    			  phone, email));
					// mobile = model.getSecondName();
					//mobiletwo = arrayOfList.get(1);
					 mobile = secondname;
					 //String mobileon = arrayOfList.;
					Log.i(LOG, "===== inawork hadi hapa");
					Log.i(LOG, mobile);
					test.setText(mobile);
					//Toast.makeText(getApplicationCont(),
					//	     "json impatikana", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
