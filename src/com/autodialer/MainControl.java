package com.autodialer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;



















import com.actionbarsherlock.app.SherlockFragmentActivity;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;







public class MainControl extends SherlockFragmentActivity   {
	private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
    private MenuDrawer mMenuDrawer;
    UserFunctions userFunctions;
    private int mActiveViewId;
    public TextView accountdetails;
    public TextView maindialing;
    public TextView organisegroups;
    PendingIntent pendingIntent;
    //AlarmManager alarmManager;
    AlertDialog alert11;
    AlertDialog.Builder builder1;
    AlertDialog.Builder builder;
	  AlertDialog alert;
    Fragment dailingfragment = new DialFragment();
    Fragment account=new AccountFragment();
    Fragment caltest=new DailingMainActivity();
    Boolean kenne;
    boolean shouldExecuteOnResume;
    JSONObject json;
    
    // push  notification messages come here
	 public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	   // please enter your sender id
	    String SENDER_ID = "115389726962";
	    
	    static final String TAG = "GCMDemo";
	    GoogleCloudMessaging gcm;
	    String msg;
	    Context context;
	    String regid;
		private RequestQueue mVolleyQueue;
		ProgressDialog pd;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
		 @Override
		    public void onReceive(Context context, Intent intent) {
		      Bundle bundle = intent.getExtras();
		      if (bundle != null) {
		    //    String string = bundle.getString(DownloadService.FILEPATH);
		        int resultCode = bundle.getInt(MyLikeService.RESULT);
		        if (resultCode == Activity.RESULT_OK) {
		        	
		        	//  Toast.makeText(MainControl.this, "login status sent",
				       //       Toast.LENGTH_LONG).show();
		        //  Toast.makeText(getActivity(),
		        //      "refreshing ",
		          //    Toast.LENGTH_LONG).show();
		        //  textView.setText("Download done");
		        } else {
		         // Toast.makeText(MainControl.this, "login status not sent",
		          //    Toast.LENGTH_LONG).show();
		        
	                
		        //  textView.setText("Download failed");
		        }
		      }
		    }
		  };
		  private BroadcastReceiver receiver2 = new BroadcastReceiver() {
				 @Override
				    public void onReceive(Context context, Intent intent) {
				      Bundle bundle = intent.getExtras();
				      if (bundle != null) {
				    //    String string = bundle.getString(DownloadService.FILEPATH);
				        int resultCode = bundle.getInt(CheckOnlineService.RESULT);
				        if (resultCode == Activity.RESULT_OK) {
				        	
				        //	  Toast.makeText(MainControl.this, "online status sent",
						  //            Toast.LENGTH_LONG).show();
				        //  Toast.makeText(getActivity(),
				        //      "refreshing ",
				          //    Toast.LENGTH_LONG).show();
				        //  textView.setText("Download done");
				        } else {
				        //  Toast.makeText(MainControl.this, "online status not sent",
				        //      Toast.LENGTH_LONG).show();
				        
			                
				        //  textView.setText("Download failed");
				        }
				      }
				    }
				  };
				  
				  private BroadcastReceiver receiver3 = new BroadcastReceiver() {
						 @Override
						    public void onReceive(Context context, Intent intent) {
						      Bundle bundle = intent.getExtras();
						      if (bundle != null) {
								    //    String string = bundle.getString(DownloadService.FILEPATH);
								        int resultCode = bundle.getInt(GcmIntentService.RESULT);
								        if (resultCode == Activity.RESULT_OK) {
								        	
								        	//  Toast.makeText(MainControl.this, "login status sent",
										       //       Toast.LENGTH_LONG).show();
								        //  Toast.makeText(getActivity(),
								        //      "refreshing ",
								        	 alert11.show();  
								          //    Toast.LENGTH_LONG).show();
								        //  textView.setText("Download done");
								        } else {
								         // Toast.makeText(MainControl.this, "login status not sent",
								          //    Toast.LENGTH_LONG).show();
								        	alert.show(); 
							                
								        //  textView.setText("Download failed");
								        }
								      }
						    }
						  };
    @Override
    public void onCreate(Bundle inState) {
        super.onCreate(inState);
        if (inState != null) {
            mActiveViewId = inState.getInt(STATE_ACTIVE_VIEW_ID);
        }
        userFunctions = new UserFunctions();
       if( userFunctions.isUserLoggedIn(getApplicationContext())){
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT,
        		MenuDrawer.MENU_DRAG_WINDOW);
       // FadingActionBarHelper helper = new FadingActionBarHelper()
      // .actionBarBackground(R.drawable.ab_background)
      //  .headerLayout(R.layout.header)
       // .contentLayout(R.layout.activity_windowsample);
   // setContentView(helper.createView(this));
  //  helper.initActionBar(this);
        shouldExecuteOnResume = false;
        mMenuDrawer.setContentView(R.layout.main_control);
        mMenuDrawer.setMenuView(R.layout.menu_scrollview);
        mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mMenuDrawer.setDrawerIndicatorEnabled(true);
        mMenuDrawer.setActiveView(maindialing);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        builder = new AlertDialog.Builder(MainControl.this);
	     builder.setMessage("Ooops !Something fishey happened Check your Internet connection and Refresh!")
	            .setCancelable(false)
	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                     //do things
	                }
	            });
	   alert = builder.create();
	   
		
      builder1 = new AlertDialog.Builder(MainControl.this);
       builder1.setMessage("Calls initiated from web?");
       builder1.setCancelable(true);
       builder1.setPositiveButton("Call ",
               new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
           	FragmentManager fm = getSupportFragmentManager();

           	//if you added fragment via layout xml
           	DailingMainActivity fragment = (DailingMainActivity)fm.findFragmentByTag("FRAGMENT_TAG");
           	fragment.call();
           //	call();	
               dialog.cancel();
           }
       });
       builder1.setNegativeButton("Cancel",
               new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               dialog.cancel();
           }
       });
       alert11 = builder1.create();
   	
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
	      if (bundle != null) {
        
        
        String message = bundle.getString("message");
		try {
			json = new JSONObject(message);
			String stime = json.getString("name");
			 int i=Integer.parseInt(stime);
			 if(i == 1){
				 builder = new AlertDialog.Builder(MainControl.this);
			     builder.setMessage("Ooops !Something fishey happened Check your Internet connection and Refresh!")
			            .setCancelable(false)
			            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                     //do things
			                }
			            });
			   alert = builder.create();
			   
				
		       builder1 = new AlertDialog.Builder(MainControl.this);
		        builder1.setMessage("Calls initiated from web?");
		        builder1.setCancelable(true);
		        builder1.setPositiveButton("Call ",
		                new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	FragmentManager fm = getSupportFragmentManager();

		            	//if you added fragment via layout xml
		            	DailingMainActivity fragment = (DailingMainActivity)fm.findFragmentByTag("FRAGMENT_TAG");
		            	fragment.call();
		            //	call();	
		                dialog.cancel();
		            }
		        });
		        builder1.setNegativeButton("Cancel",
		                new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		            }
		        });
		        alert11 = builder1.create();
	        	 alert11.show();  
			 }else{
				 alert.show(); 
			 }
		//	name.setText(stime);
			
			String slecturename = json.getString("deal");
			//deal.setText(slecturename);
			
			String sroom = json.getString("valid");
			//valid.setText(sroom);
			
			String sfaculty = json.getString("address");
			//address.setText(sfaculty);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        }else{
        	
        }
	   //   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	    //  StrictMode.setThreadPolicy(policy); 
	      pd = new ProgressDialog(MainControl.this);
	      pd.setMessage("Registering your device to our servers just a moment");
	      mVolleyQueue = Volley.newRequestQueue(MainControl.this); 
	      context = getApplicationContext();
	      context = getApplicationContext();
			if(checkPlayServices()){
				gcm = GoogleCloudMessaging.getInstance(this);
				regid = getRegistrationId(context);
				//mDisplay.setText(regid);
				if(regid.isEmpty()){
					new RegisterBackground().execute();
					pd.show();
				}
				
			}
       // Intent myIntent = new Intent(MainControl.this, CheckMessoVolleyService.class);
       // pendingIntent = PendingIntent.getService(MainControl.this, 0, myIntent, 0);
     //   Intent intent=new Intent(MainControl.this,CheckMessoVolleyService.class);
		//MainControl.this.startService(intent);
		 // AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		//Calendar calendar = Calendar.getInstance();
      //  calendar.setTimeInMillis(System.currentTimeMillis());
       // calendar.add(Calendar.SECOND, 10);
		//alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
       // alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),3000, pendingIntent);
		
        Intent intentonline=new Intent(MainControl.this,CheckOnlineService.class);
		MainControl.this.startService(intentonline);	
        maindialing=(TextView)findViewById(R.id.listsizzlingevent);
        maindialing.setTextColor(getResources().getColor(R.color.LightGreen));
        mMenuDrawer.setActiveView( maindialing);
        FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.contentframe, caltest,"FRAGMENT_TAG");
		transaction.commit();
		maindialing .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	maindialing.setTextColor(getResources().getColor(R.color.LightGreen));
            	  accountdetails.setTextColor(getResources().getColor(R.color.White));
            	  organisegroups.setTextColor(getResources().getColor(R.color.White));
            	mMenuDrawer.setActiveView(v);
            	mMenuDrawer.closeMenu();
                mActiveViewId = v.getId();
                FragmentManager fm = getSupportFragmentManager();
        		FragmentTransaction transaction = fm.beginTransaction();
        		transaction.replace(R.id.contentframe, caltest,"FRAGMENT_TAG");
        		//transaction.addToBackStack("FRAGMENT_TAG").commit(); 
        		transaction.commit();
            }
        });
		 accountdetails=(TextView)findViewById(R.id.listaccount);
		 accountdetails .setOnClickListener(new View.OnClickListener() {
	           public void onClick(View v) {
	               // Perform action on click   
	        	   maindialing.setTextColor(getResources().getColor(R.color.White));
	        	   organisegroups.setTextColor(getResources().getColor(R.color.White));
	        	   accountdetails.setTextColor(getResources().getColor(R.color.LightGreen));
	           	mMenuDrawer.setActiveView(v);
	           	mMenuDrawer.closeMenu();
	               mActiveViewId = v.getId();
	              // findViewById(R.id.frag_ptr_list).setVisibility(View.GONE);
	               FragmentManager fm = getSupportFragmentManager();
	   			FragmentTransaction transaction = fm.beginTransaction();
	   			transaction.replace(R.id.contentframe, account);
	   			
	   			transaction.commit();
	               // currentContext.startActivity(activityChangeIntent);

	              
	           }
		 });
		 organisegroups=(TextView)findViewById(R.id.listpostedevents);
		 organisegroups .setOnClickListener(new View.OnClickListener() {
	           public void onClick(View v) {
	               // Perform action on click   
	        //	   accountdetails.setTextColor(getResources().getColor(R.color.White));
	        	//   maindialing.setTextColor(getResources().getColor(R.color.White));
	        	//   organisegroups.setTextColor(getResources().getColor(R.color.LightGreen));
	          // 	mMenuDrawer.setActiveView(v);
	          // 	mMenuDrawer.closeMenu();
	          //     mActiveViewId = v.getId();
	              // findViewById(R.id.frag_ptr_list).setVisibility(View.GONE);
	          //     FragmentManager fm = getSupportFragmentManager();
	   		//	FragmentTransaction transaction = fm.beginTransaction();
	   			//transaction.replace(R.id.contentframe, dailingfragment);
	   		//	transaction.commit();
	               // currentContext.startActivity(activityChangeIntent);

	              
	           }
		 });
		TextView activeView = (TextView) findViewById(mActiveViewId);
        if (activeView != null) {
            mMenuDrawer.setActiveView(activeView);
           // mMenuDrawer.setActiveView(sizzlingevents);
           // mContentTextView.setText("Active item: " + activeView.getText());
        }

        // This will animate the drawer open and closed until the user manually drags it. Usually this would only be
        // called on first launch.
        mMenuDrawer.peekDrawer();
       }  else{
    	   Intent myIntent = new Intent(MainControl.this, Login.class);
             startActivityForResult(myIntent, 0);
           //   finish();
    	   finish();
    	   
       }
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
			pd.dismiss(); 
			Toast.makeText(MainControl.this,
				      "your device has successfully been registered to our servers", Toast.LENGTH_SHORT).show();
        }
		private void sendRegistrationIdToBackend() {
		      // Your implementation here.
			DatabaseHandlerUser db = new DatabaseHandlerUser(MainControl.this);
			  HashMap<String,String> user = new HashMap<String, String>();
		       user = db.getUserDetails();
				String url = "http://globegokartshows.co.ke/Testing/server/getdevice.php";
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

    @Override
	public void onResume() {
	    super.onResume();
	    checkPlayServices();
		if(shouldExecuteOnResume){
				
		}
	 //   Intent intent=new Intent(MainControl.this,CheckMessoVolleyService.class);
	  //  MainControl.this.startService(intent);
	   MainControl.this. registerReceiver(receiver, new IntentFilter(MyLikeService.NOTIFICATION));
	   MainControl.this. registerReceiver(receiver2, new IntentFilter(CheckOnlineService.NOTIFICATION));
	  MainControl.this. registerReceiver(receiver3, new IntentFilter(CheckMessoVolleyService.NOTIFICATION));
    }
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
    }
    public void onStop() {
		super.onStop();
		MainControl.this.unregisterReceiver(receiver);
		MainControl.this.unregisterReceiver(receiver2);
		MainControl.this.unregisterReceiver(receiver3);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
        outState.putInt(STATE_ACTIVE_VIEW_ID, mActiveViewId);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
       switch (item.getItemId()) {
            case android.R.id.home:
               mMenuDrawer.toggleMenu();
                return true;
        }
 
       return super.onOptionsItemSelected(item);
   }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
        Intent intentonline=new Intent(MainControl.this,CheckOfflineStatus.class);
		MainControl.this.startService(intentonline);	
		
      //  WindowSample.this.finish();
        super.onBackPressed();
    }
}
