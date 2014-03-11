package com.autodialer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MessageTest extends Activity {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  NetAsync(view);
        setContentView(R.layout.splash);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        Btnregister = (Button) findViewById(R.id.registerbtn);
        btnLogin = (Button) findViewById(R.id.login);
        passreset = (Button)findViewById(R.id.passres);
        loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);

        passreset.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
       // Intent myIntent = new Intent(view.getContext(), PasswordReset.class);
       // startActivityForResult(myIntent, 0);
       // finish();
        }});


        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            //    Intent myIntent = new Intent(view.getContext(), Register.class);
             //   startActivityForResult(myIntent, 0);
             //   finish();
             }});

/**
 * Login button click event
 * A Toast is set to alert when the Email and Password field is empty
 **/
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                    NetAsync(view);
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


/**
 * Async Task to check whether internet connection is working.
 **/

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(MessageTest.this);
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
                nDialog.dismiss();
                new ProcessLogin().execute();
            }
            else{
                nDialog.dismiss();
                loginErrorMsg.setText("Error in Network Connection");
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

            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.pword);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            pDialog = new ProgressDialog(MessageTest.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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

                    if(Integer.parseInt(res) == 1){
                    	 Toast.makeText(getApplicationContext(),
                                 "Message Equals 1", Toast.LENGTH_LONG).show();
                    	 pDialog.dismiss();
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
                    	 else if (Integer.parseInt(res) == 2){
                    		 Toast.makeText(getApplicationContext(),
                                     "Message Equals 2", Toast.LENGTH_LONG).show();	
                    		 pDialog.dismiss();
                    	 }
                    else if(Integer.parseInt(res) == 3){
                    	Toast.makeText(getApplicationContext(),
                                "Message Equals 3", Toast.LENGTH_LONG).show();

                        pDialog.dismiss();
                      //  loginErrorMsg.setText("Incorrect username/password");
                    }
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
    }
    public void NetAsync(View view){
        new NetCheck().execute();
    }
}