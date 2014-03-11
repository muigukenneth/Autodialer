package com.autodialer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.EditText;
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


public class CheckMessageService extends Service {
	/**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
	  private int result = Activity.RESULT_CANCELED;
	  public static final String FILEPATH = "filepathtwo";
	  public static final String RESULT = "result";
	  public static final String NOTIFICATION = "com.autodialer.CheckMessageService";
	    private static final int ONGOING_NOTIFICATION = 1;
	    private Notification notification;
	public CheckMessageService() {
		// super("Like Service");
	}
	
 // RequestQueue queue;
	
	
	
  @Override
  public void onCreate() {
  	super.onCreate();
  //  this.notification = new Notification(R.drawable.ic_launcher, getText(R.string.app_name), System.currentTimeMillis());
    //Intent notificationIntent = new Intent(this, DailingMainActivity.class);
    //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
      //      Intent.FLAG_ACTIVITY_SINGLE_TOP);

    //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    //this.notification.setLatestEventInfo(this, getText(R.string.app_name), "App is running to communicate with web", pendingIntent);

    //startForeground(ONGOING_NOTIFICATION, this.notification);
  	 new NetCheck().execute();		
				
    //  makePostRequest();
      
  }
  private class NetCheck extends AsyncTask<String,String,Boolean>
  {
      private ProgressDialog nDialog;

      @Override
      protected void onPreExecute(){
          super.onPreExecute();
        //  nDialog = new ProgressDialog(MessageTest.this);
        //  nDialog.setTitle("Checking Network");
        //  nDialog.setMessage("Loading..");
        //  nDialog.setIndeterminate(false);
        //  nDialog.setCancelable(true);
        //  nDialog.show();
      }
      /**
       * Gets current device state and checks for working internet connection by trying Google.
      **/
      @Override
      protected Boolean doInBackground(String... args){



          ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
            //  nDialog.dismiss();
              new ProcessLogin().execute();
          }
          else{
            //  nDialog.dismiss();
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
          DatabaseHandlerUser db = new DatabaseHandlerUser(getApplicationContext());
          HashMap<String,String> user = new HashMap<String, String>();
          user = db.getUserDetails();
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

          UserFunctionsMessage userFunction = new UserFunctionsMessage();
          JSONObject json = userFunction.loginUser(email, password);
          return json;
      }

      @Override
      protected void onPostExecute(JSONObject json) {
          try {
             if (json.getString(KEY_SUCCESS) != null) {

                  String res = json.getString(KEY_SUCCESS);
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
     			   Intent intentnoma=new Intent(CheckMessageService.this,CallingService.class);
     				CheckMessageService.this.startService(intentnoma);
     				StopService();
      	 
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
      	 

                     // pDialog.dismiss();
                    //  loginErrorMsg.setText("Incorrect username/password");
                  }
             }
          } catch (JSONException e) {
              e.printStackTrace();
          }
     }
  }
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
  //	return super.onStartCommand(intent, flags, startId);
	  return START_STICKY;
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
