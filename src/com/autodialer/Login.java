package com.autodialer;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


















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
import com.autodialer.MainControl.RegisterBackground;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.todddavies.components.progressbar.ProgressWheel;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


















import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends Activity {

    Button btnLogin;
    Button Btnregister;
    Button passreset;
    EditText inputEmail;
    EditText inputPassword;
    private TextView loginErrorMsg;
    /**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    //tag to cancel all queues
    private final String TAG_REQUEST = "MY_TAG";
     // RequestQueue queue;
 	private RequestQueue mVolleyQueue2;	
 	//array names
 	 private static final String ARRAY_NAME = "userdetails";
	  private static final String FIRSTNAME = "firstname";
	  private static final String LASTNAME = "lastname";
	  private static final String EMAIL = "email";
	  private static final String CREATIONTIME = "Time";
	  ProgressWheel pw;
	  //dialog box for loading
	  private ProgressDialog pDialog;
	  LinearLayout spinner;
	  //email and password 
	  String email,password;
	  
	  public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	    static final String TAG = "GCMDemo";
	    GoogleCloudMessaging gcm;
	    Context context;
	    String regid;
	    String SENDER_ID = "115389726962";
	    TextView subheading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        Btnregister = (Button) findViewById(R.id.registerbtn);
        btnLogin = (Button) findViewById(R.id.login);
        passreset = (Button)findViewById(R.id.passres);
        spinner =(LinearLayout)findViewById(R.id.Progressbarnew);
        loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);
        subheading = (TextView) findViewById(R.id.subheading);
      pw = (ProgressWheel) findViewById(R.id.pw_spinner);
        mVolleyQueue2 = Volley.newRequestQueue(Login.this); 
        context = getApplicationContext();
        pDialog = new ProgressDialog(Login.this);
        passreset.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
        	Uri uri = Uri.parse("http://www.globegokartshows.co.ke/salespacer/reset.php");
        	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        	startActivity(intent);
       // Intent myIntent = new Intent(view.getContext(), PasswordReset.class);
       // startActivityForResult(myIntent, 0);
       // finish();
        }});


        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Uri uri = Uri.parse("http://dash.yt/salespacer/registration.php");
            	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	startActivity(intent);
            //    Intent myIntent = new Intent(view.getContext(), Register.class);
             //   startActivityForResult(myIntent, 0);
             //   finish();
             }});
        if(checkPlayServices()){
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			//mDisplay.setText(regid);
        }

/**
 * Login button click event
 * A Toast is set to alert when the Email and Password field is empty
 **/
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                	InputMethodManager imm = (InputMethodManager)getSystemService(
                		      Context.INPUT_METHOD_SERVICE);
                		imm.hideSoftInputFromWindow(inputPassword.getWindowToken(), 0);
                		imm.hideSoftInputFromWindow(inputEmail.getWindowToken(), 0);
                	 Dailingmainmethod();
                	 spinner.setVisibility(View.VISIBLE);
                	 pw.spin();
                		subheading.setText("logging you in please wait...");
                	// subheading.setVisibility(View.GONE);
                	 inputEmail.setVisibility(View.GONE);
                	 inputPassword.setVisibility(View.GONE);
                	 Btnregister.setVisibility(View.GONE);
                	 btnLogin.setVisibility(View.GONE);
                	 passreset.setVisibility(View.GONE);
                	 loginErrorMsg.setVisibility(View.GONE);
                	//  pDialog.setTitle("Contacting Servers");
                    //  pDialog.setMessage("Logging in ...");
                     // pDialog.setIndeterminate(false);
                    //  pDialog.setCancelable(true);
                    //  pDialog.show();

                   // NetAsync(view);
                }
                else if ( ( !inputEmail.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    class RegisterBackground extends AsyncTask<String,String,String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String msg = "";
			try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);
                msg = "Dvice registered, registration ID=" + regid;
                Log.d("111", msg);
                sendRegistrationIdToBackend();
                
                // Persist the regID - no need to register again.
               storeRegistrationId(context, regid);
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }
		
		@Override
        protected void onPostExecute(String msg) {
          //  mDisplay.append(msg + "\n");
			pw.stopSpinning();
            Intent myIntent = new Intent(Login.this, MainControl.class);
            startActivityForResult(myIntent, 0);
          //   finish();
   	   finish();
			Toast.makeText(Login.this,
				      "your device has successfully been registered to our servers", Toast.LENGTH_SHORT).show();
        }
		private void sendRegistrationIdToBackend() {
		      // Your implementation here.
			DatabaseHandlerUser db = new DatabaseHandlerUser(Login.this);
			  HashMap<String,String> user = new HashMap<String, String>();
		       user = db.getUserDetails();
				String url = "http://dash.yt/salespacer/android_api/get_device_registration/getdevice.php";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("pid",user.get("email")));
	            params.add(new BasicNameValuePair("regid", regid));
	           DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            try {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            try {
					HttpResponse httpResponse = httpClient.execute(httpPost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
			
			
			
		    }
    private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
    }
private boolean checkPlayServices() {
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS) {
        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
            Log.i(TAG, "This device is not supported.");
            finish();
        }
        return false;
    }
    return true;
}
private String getRegistrationId(Context context) {
    final SharedPreferences prefs = getGCMPreferences(context);
    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
    if (registrationId.isEmpty()) {
        Log.i(TAG, "Registration not found.");
        return "";
    }
    
    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
    int currentVersion = getAppVersion(context);
    if (registeredVersion != currentVersion) {
        Log.i(TAG, "App version changed.");
        return "";
    }
    return registrationId;
}

private SharedPreferences getGCMPreferences(Context context) {
    
    return getSharedPreferences(MainControl.class.getSimpleName(),
            Context.MODE_PRIVATE);
}

private static int getAppVersion(Context context) {
    try {
        PackageInfo packageInfo = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    } catch (NameNotFoundException e) {
        // should never happen
        throw new RuntimeException("Could not get package name: " + e);
    }
}


    public void Dailingmainmethod() {
    	DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
      //  HashMap<String,String> user = new HashMap<String, String>();
       // user = db.getUserDetails();
        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        Map<String,String> params = new HashMap<String, String>();
        params.put("email",email);
        params.put("password",password);
    	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
    	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
    	CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/salespacer/login_android.php", params,
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
    					Toast.makeText(Login.this,
    						     " Check your internet connection ", Toast.LENGTH_SHORT).show();
    					 loginErrorMsg.setText("error in loggin in no internet connection");
    				} else if( error instanceof ClientError) { 
    				} else if( error instanceof ServerError) {
    					Toast.makeText(Login.this,
    						     "Server error ", Toast.LENGTH_SHORT).show();
    				} else if( error instanceof AuthFailureError) {
    					 loginErrorMsg.setText("error in loggin in incorrect username/password ");
    				} else if( error instanceof ParseError) {
    					 loginErrorMsg.setText("error in loggin in incorrect username/password ");
    				} else if( error instanceof NoConnectionError) {
    					Toast.makeText(Login.this,
    								     "no internet connection ", Toast.LENGTH_SHORT).show();
    					 loginErrorMsg.setText("error in loggin in no internet connection");
    					//handle all errors as fast as possible
    				} else if( error instanceof TimeoutError) {
    					//Toast.makeText(Login.this,
    						 //     "connection timeout ", Toast.LENGTH_SHORT).show();
    					   loginErrorMsg.setText("error in loggin in no internet connection");
    				
    				}
    				// pDialog.dismiss();
    				pw.stopSpinning();
    				subheading.setText("Please Login to get started");
    				 spinner.setVisibility(View.GONE);
    				 inputEmail.setVisibility(View.VISIBLE);
    				subheading.setVisibility(View.VISIBLE);
                	 inputPassword.setVisibility(View.VISIBLE);
                	 btnLogin.setVisibility(View.VISIBLE);
                	 Btnregister.setVisibility(View.VISIBLE);
                	 passreset.setVisibility(View.VISIBLE);
                	 loginErrorMsg.setVisibility(View.VISIBLE);
                   //  loginErrorMsg.setText("Incorrect username/password");
      			 // shutdownService();
    			//	Progressbar.setVisibility(View.GONE);
    	    }
    	});
    	mVolleyQueue2.add(request);
     //  makePostRequest();
       

    }
    private void parseFlickrImageResponse(JSONObject response) {//throws JSONException {
    	pw.setText("Loading User Space..." );
    	// pDialog.setMessage("Loading User Space");
       //  pDialog.setTitle("Getting Data");
    	//if(response.has("nyc")) {
    		try {
    			//dbHandler.delete();
    			//JSONObject photos = response.getJSONObject("success");
    			// Get the data array containing posts
                JSONArray items = response.getJSONArray(ARRAY_NAME);
    			//JSONArray items = photos.getJSONArray("posts");

    			// arrayOfListnew.clear();
    			
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
    				  String firstname = jsonObj.getString(FIRSTNAME);
    				  String lastname = jsonObj.getString(LASTNAME);
    				  String email = jsonObj.getString(EMAIL);
    				  String time = jsonObj.getString(CREATIONTIME);
    				  DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
    				  db.addUser("Null","Null",email,firstname+" "+lastname,"Null",time);
    				 // pDialog.dismiss();
    				//  pw.spin();
    				  new RegisterBackground().execute();
    				  pw.setText("Registering your Device..." );
                  	Intent intent=new Intent(Login.this,MyLikeService.class);
                  	Login.this.startService(intent);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    }

}