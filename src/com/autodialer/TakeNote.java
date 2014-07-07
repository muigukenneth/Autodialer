package com.autodialer;



import io.github.homelocker.lib.HomeKeyLocker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;










import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
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
import com.autodialer.DailingMainActivity.ListenToPhoneState;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TakeNote extends SherlockActivity {
	 SharedPreferences myPrefsnow;
	EditText savenote;
	Button addnote;
	Button exit;
	 String id;
	 String phone;
	 String name;
	 Crouton  successCrouton;
	 Style 	styleretry;
	 TextView contact;
	 String dialogmessage;
	 String  errorimahappen;
	 ListView Contact_listview;
	 TextView showrecords;
	 Button answer;
	 Button reject;
	 private boolean   isShownothererrors =false;
	 boolean isShownretry =false;
	 DatabaseHandlerHistory dbtwo ;
	 TextView calling;
	 AudioManager audioManager;
	 TelephonyManager tManager;
	 ListenToPhoneState listener;
	 private RequestQueue mVolleyQueue2;
	 private SpeedScrollListener listenertwo;
	 private HistoryAdapter plusAdapter;
	 private HistoryAdapterLowerDevices lowerdevicesAdapter;
	 private final String TAG_REQUEST = "MY_TAG";
	 JsonObjectRequest jsonObjRequesttwo;
	  private SmoothProgressBar mGoogleNow;
	 TextView emptyText;
	 ProgressBar loading;
	 // flag for Internet connection status
	    Boolean isInternetPresent = false;
	     Boolean CONDITION=false;
	    // Connection detector class
	    ConnectionDetector cd;
	    String st;
	 CheckBox cb;
	 Context context;
	 private HomeKeyLocker mHomeKeyLocker;
	 private RequestQueue mVolleyQueue;
	 LinearLayout Progressbar;
	 ImageView loodspeaker;
	 ImageView mute;
	 ImageView  voicerecord;
	 private static final String ARRAY_NAME_HISTORY = "History";
	 private static final String ID_HISTORY = "Id";
	 private static final String NAME_HISTORY = "SalesName";
	 private static final String NOTE_HISTORY = "Note";
	 private static final String DATE_HISTORY = "Time";
	 List<HistoryParse> arrayOfListhistory;
	 private static final String LOG = "SALESPACER-DEBBUG";
	 private BroadcastReceiver receivertwo = new BroadcastReceiver() {
		 @Override
		    public void onReceive(Context context, Intent intent) {
		      Bundle bundle = intent.getExtras();
		      if (bundle != null) {
				    //    String string = bundle.getString(DownloadService.FILEPATH);
				        int resultCode = bundle.getInt("RESULT");
				         id = bundle.getString("ID");
				        String error = bundle.getString("ERROR");
				        if (resultCode == 1) {
				        	
				        	//Toast.makeText(getActivity(),
							 //    " quite busy ", Toast.LENGTH_SHORT).show();	
				        	
				        //	SaveHistory();	
				    		       
				        	//}
				        } else if(resultCode == 2) {
				        //	UpdateHistory();
				        	//Toast.makeText(getActivity(),
							  //  " not busy ", Toast.LENGTH_SHORT).show();
				        	
				    	 }else{
				    				 
				    		        		 }
		      
		 
		 }
		      
		 }   		       
		  };
	//Button addnote;
		  DatabaseHandlerUser db;
		  AudioManager audioManagermute;
	 HashMap<String,String> user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature((int)Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.take_note);
		// mHomeKeyLocker = new HomeKeyLocker();
		//getSupportActionBar().show();
		 Intent intent = getIntent();
		 
		  Bundle bundle = intent.getExtras();
		  phone = bundle.getString("PHONE");
		  name = bundle.getString("NAME");
		  dbtwo= new DatabaseHandlerHistory(TakeNote.this);
	
		 myPrefsnow = PreferenceManager.getDefaultSharedPreferences(this);
		 savenote = (EditText) findViewById(R.id.etEdit);
		 addnote=(Button)findViewById(R.id.btnadd);
		 exit=(Button)findViewById(R.id.btnexit);
		  mGoogleNow = (SmoothProgressBar) findViewById(R.id.google_now);
		 calling= (TextView) findViewById(R.id.textViewcalling);
	        reject= (Button)findViewById(R.id.declinetwo);
		 contact=(TextView)findViewById(R.id.textViewcontact);
		 loodspeaker=(ImageView)findViewById(R.id.imageViewloadspeaker);
		 mute=(ImageView)findViewById(R.id.imageViewmute);
		 voicerecord=(ImageView)findViewById(R.id.imageViewrecord);
		 loading=(ProgressBar)findViewById(R.id.progress);
		 Contact_listview = (ListView)  findViewById(R.id.listcontacts);
  		 emptyText = (TextView) findViewById(R.id.emptyResults);
  		cb  = (CheckBox) findViewById(R.id.cb);
  		  Progressbar = (LinearLayout)findViewById(R.id.linlaHeaderProgress);
		  showrecords=(TextView)findViewById(R.id.loadingtext);
		
		 // TakeNote.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
  		  listenertwo = new SpeedScrollListener();
  		 arrayOfListhistory = new ArrayList<HistoryParse>();
  		  cd = new ConnectionDetector(TakeNote.this.getApplicationContext());
		 mVolleyQueue = Volley.newRequestQueue(getApplicationContext()); 
		 mVolleyQueue2 = Volley.newRequestQueue(getApplicationContext()); 
		db = new DatabaseHandlerUser(getApplicationContext());
		//WindowManager.LayoutParams lp = getWindow().getAttributes();
		//lp.buttonBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
		//lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
		//getWindow().setAttributes(lp);
		 user = new HashMap<String, String>();
		  user = db.getUserDetails();
		 // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		//  imm.showSoftInputFromInputMethod(savenote.getWindowToken(), 0);
		  this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		//  mHomeKeyLocker.lock(TakeNote.this);
		  audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE); 
          audioManager.setMode(AudioManager.MODE_IN_CALL); 
          
      //    Intent selector = new Intent("android.intent.action.MAIN");
       //   selector.addCategory("android.intent.category.HOME");
       //   selector.setComponent(new ComponentName("android","com.android.internal.app.ResolverActivity"));
       //   startActivity(selector);
		  // home key is locked since then
		  tManager = (TelephonyManager) 
	 	            getSystemService(Context.TELEPHONY_SERVICE);
	 	          listener = new ListenToPhoneState();
	 	          tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE); 
	 	         audioManagermute = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	        	    audioManagermute.setMode(AudioManager.MODE_NORMAL);
	 	     //  mute.setEnabled(false);  
		   Progressbar.setVisibility(View.VISIBLE);
		contact.setText("Name:"+name+"  Phone:"+phone);
			//db = new DatabaseHandler(this);
		calling.setText("calling "+name+".......");
		 mute.setOnClickListener(new View.OnClickListener() {
			  
	          @Override
	          public void onClick(View v) {
	        	  boolean state = true;
	        	
	        	    if(state) //state-boolean 
	        	    {
	        	    	audioManagermute.setMicrophoneMute(true);
	        	    	 loodspeaker.setBackgroundColor(R.color.Blue);
	        	   	
	        	        state= false;
	        	    }
	        	    else
	        	    {
	        	   	   audioManagermute.setMicrophoneMute(false);
   	        	        audioManagermute.setMode(AudioManager.MODE_IN_CALL);
   	        	        loodspeaker.setBackgroundColor(R.color.black);
	        	    	 state = true;   
	        	    }
	          }
	      });    	 
		  loodspeaker.setOnClickListener(new View.OnClickListener() {
			  
	          @Override
	          public void onClick(View v) {
	        	 
	        	  if(	audioManager.isSpeakerphoneOn()){
		        		audioManager.setSpeakerphoneOn(false);	
		        		 loodspeaker.setBackgroundColor(R.color.black);
		        	}else{
		        		 
			              audioManager.setSpeakerphoneOn(true);
			              loodspeaker.setBackgroundColor(R.color.Blue);
		        	}
		        
	        	
	          }
	      });
	      
	      reject.setOnClickListener(new View.OnClickListener() {
	          @Override
	          public void onClick(View v) {
	        	  try {
	        	        //String serviceManagerName = "android.os.IServiceManager";
	        	        String serviceManagerName = "android.os.ServiceManager";
	        	        String serviceManagerNativeName = "android.os.ServiceManagerNative";
	        	        String telephonyName = "com.android.internal.telephony.ITelephony";

	        	        Class telephonyClass;
	        	        Class telephonyStubClass;
	        	        Class serviceManagerClass;
	        	        Class serviceManagerStubClass;
	        	        Class serviceManagerNativeClass;
	        	        Class serviceManagerNativeStubClass;

	        	        Method telephonyCall;
	        	        Method telephonyEndCall;
	        	        Method telephonyAnswerCall;
	        	        Method getDefault;

	        	        Method[] temps;
	        	        Constructor[] serviceManagerConstructor;

	        	        // Method getService;
	        	        Object telephonyObject;
	        	        Object serviceManagerObject;

	        	        telephonyClass = Class.forName(telephonyName);
	        	        telephonyStubClass = telephonyClass.getClasses()[0];
	        	        serviceManagerClass = Class.forName(serviceManagerName);
	        	        serviceManagerNativeClass = Class.forName(serviceManagerNativeName);

	        	        Method getService = // getDefaults[29];
	        	                serviceManagerClass.getMethod("getService", String.class);

	        	        Method tempInterfaceMethod = serviceManagerNativeClass.getMethod(
	        	                "asInterface", IBinder.class);

	        	        Binder tmpBinder = new Binder();
	        	        tmpBinder.attachInterface(null, "fake");

	        	        serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
	        	        IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
	        	        Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
	        			  
	        			  telephonyObject = serviceMethod.invoke(null, retbinder);
	        		        //telephonyCall = telephonyClass.getMethod("call", String.class);
	        		        telephonyEndCall = telephonyClass.getMethod("endCall");
	        		        //telephonyAnswerCall = telephonyClass.getMethod("answerRingingCall");
	        		      
	        		        telephonyEndCall.invoke(telephonyObject);
	        		    	if(	audioManager.isSpeakerphoneOn()){
	    		        		audioManager.setSpeakerphoneOn(false);	
	    		        	}
	        		    	 reject.setVisibility(View.GONE);
		        			  mute.setVisibility(View.GONE);
		        			  loodspeaker.setVisibility(View.GONE);
		        			 voicerecord.setVisibility(View.GONE);
		        			 calling.setVisibility(View.GONE);
		        			 CONDITION=true;
		        			 exit.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT ));
	        		      //  TakeNote.this.finish();
	        		       // Intent openMainActivity= new Intent(TakeNote.this, MainControl.class);
	        		       // openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	        		       // startActivity(openMainActivity);
	        		    } catch (Exception e) {
	        		        e.printStackTrace();
	        		     //   Log.e(TakeNote.this,
	        		        //        "FATAL ERROR: could not connect to telephony subsystem");
	        		      //  Log.error(TakeNote.this, "Exception object: " + e);
	        		}
	          }
	      });
	      
		 addnote.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	  isInternetPresent = cd.isConnectingToInternet();

		        	    // check for Internet status
		        	    if (isInternetPresent) {
		        	if(savenote.getText().toString().length() > 0) {
		        	     // editText is not empty
		        	 

		        	
		        		SaveHistory();
		        		
		        	 loading.setVisibility(View.VISIBLE);
		        	 mGoogleNow.progressiveStart();
		        	}
		        	// Toast.makeText(getApplicationContext(), "Button 1 pressed",  Toast.LENGTH_SHORT).show(); 
		        //	SharedPreferences.Editor editor = myPrefsnow.edit();
					//editor.putInt("num", 3);
					//editor.commit();  
		        
		        	// int text = contact_array_from_db.get(0).getID();
		        	// String mobile = contact_array_from_db.get(0).getPhoneNumber();
		        	// String name = contact_array_from_db.get(0).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	// callNext();
		        	//finish();
		        	    } else {
		        	    	dbtwo.Add_History(name, phone, savenote.getText().toString(), user.get("email"), user.get("uname"));
		        	    	savenote.setText("");
		        	        // Internet connection is not present
		        	        // Ask user to connect to Internet
		        	    	Showalert();
		        	    	
		        	    }
		        }
		    });
		 exit.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Intent intentnew=new Intent(TakeNote.this,CheckNoteidService.class);
		        	 //TakeNote.this.startService(intentnew);	 
		        	if(	audioManager.isSpeakerphoneOn()){
		        		audioManager.setSpeakerphoneOn(false);	
		        	}
		          //  tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		              if (tManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
		            	
		              }else{
		            	  try {
				            	//String serviceManagerName = "android.os.IServiceManager";
				        	        String serviceManagerName = "android.os.ServiceManager";
				        	        String serviceManagerNativeName = "android.os.ServiceManagerNative";
				        	        String telephonyName = "com.android.internal.telephony.ITelephony";

				        	        Class telephonyClass;
				        	        Class telephonyStubClass;
				        	        Class serviceManagerClass;
				        	        Class serviceManagerStubClass;
				        	        Class serviceManagerNativeClass;
				        	        Class serviceManagerNativeStubClass;

				        	        Method telephonyCall;
				        	        Method telephonyEndCall;
				        	        Method telephonyAnswerCall;
				        	        Method getDefault;

				        	        Method[] temps;
				        	        Constructor[] serviceManagerConstructor;

				        	        // Method getService;
				        	        Object telephonyObject;
				        	        Object serviceManagerObject;

				        	        telephonyClass = Class.forName(telephonyName);
				        	        telephonyStubClass = telephonyClass.getClasses()[0];
				        	        serviceManagerClass = Class.forName(serviceManagerName);
				        	        serviceManagerNativeClass = Class.forName(serviceManagerNativeName);

				        	        Method getService = // getDefaults[29];
				        	                serviceManagerClass.getMethod("getService", String.class);

				        	        Method tempInterfaceMethod = serviceManagerNativeClass.getMethod(
				        	                "asInterface", IBinder.class);

				        	        Binder tmpBinder = new Binder();
				        	        tmpBinder.attachInterface(null, "fake");

				        	        serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
				        	        IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
				        	        Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
				        			  
				        			  telephonyObject = serviceMethod.invoke(null, retbinder);
				        		        //telephonyCall = telephonyClass.getMethod("call", String.class);
				        		        telephonyEndCall = telephonyClass.getMethod("endCall");
				        		        //telephonyAnswerCall = telephonyClass.getMethod("answerRingingCall");
				        		      
				        		        telephonyEndCall.invoke(telephonyObject);
				            	  } catch (Exception e) {
				        		        e.printStackTrace();
				        		     //   Log.e(TakeNote.this,
				        		        //        "FATAL ERROR: could not connect to telephony subsystem");
				        		      //  Log.error(TakeNote.this, "Exception object: " + e);
				        		}
		              }
    		        TakeNote.this.finish();
    		        Intent openMainActivity= new Intent(TakeNote.this, MainControl.class);
    		        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    		        startActivity(openMainActivity);
		        }
		    });
		 mGoogleNow.progressiveStart();     
		 GetCount();
		  }
	public void GetCount(){
		

		//super.onCreate();
	//Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
	//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();



	Map<String,String> params = new HashMap<String, String>();

	params.put("phone",phone);


	//http://www.globegokartshows.co.ke/api/comments/addcomment.php
	//Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/get_count.php", params,
			new Response.Listener<JSONObject>() {
	   @Override
	   public void onResponse(JSONObject response) {
		 //  Toast.makeText(getApplicationContext(),
			//	   ""+response, Toast.LENGTH_SHORT).show();
		   String contactname;
		   String 	compare;
		try {
			contactname = response.getString("message");
			//int busy=Integer.parseInt(contactname);
		//	Toast.makeText(getApplicationContext(),
			//		  contactname   , Toast.LENGTH_SHORT).show();
			compare="nohistory";
					
			if(compare.equals(contactname)){
				//  result = Activity.RESULT_OK;
				
				 emptyText.setText("no history for this contact");
				 Progressbar.setVisibility(View.GONE);
				 mGoogleNow.progressiveStop();
				 
			}else{
				 showrecords.setText("getting "+contactname+" records please wait...");
				//Toast.makeText(getApplicationContext(),
				//		 contactname, Toast.LENGTH_SHORT).show();
				 // result = Activity.RESULT_OK;
				  Historymainmethod();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 //  parseHistoryResponserefresh(response);

	   	
	       // JSON Parsing
	   	//Toast.makeText(getApplicationContext(),
			//		     "status sent", Toast.LENGTH_SHORT).show();
	   	 // successfully finished
	   //  result = Activity.RESULT_OK;
	 // successfully finished
	   	
	   	
	    // result = Activity.RESULT_OK;
	 // Intent intent = new Intent(NOTIFICATION);
		   // intent.putExtra(FILEPATH, outputPath);
		 //   intent.putExtra(MESSAGE, dialogmessage);
		  //  intent.putExtra(RESULT, result);
		  //  sendBroadcast(intent);
	   //	StopService();
	   	// makeSampleHttpRequest();
	   	//  pDialog.dismiss();
	   }
	}, new Response.ErrorListener() {
	   @Override
	   public void onErrorResponse(VolleyError error) {
	   //String dialogmessage = null;
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
					 errorimahappen="No internet connection tap to Retry displaying history...." ;
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
					errorimahappen=" Server error  exit the app.try again later  ";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					 errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
					 errorimahappen="No internet connection tap to Retry displaying history...." ;
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
					 errorimahappen="Slow internet connection tap to Retry displaying history....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
				}
				//Toast.makeText(TakeNote.this,
				  //   dialogmessage, Toast.LENGTH_SHORT).show();
				 mGoogleNow.progressiveStop();
				Progressbar.setVisibility(View.GONE);
				 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					    plusAdapter =  new HistoryAdapter(TakeNote.this,listenertwo,
								arrayOfListhistory);
				        Contact_listview.setEmptyView(emptyText);
				       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
					    Contact_listview.setAdapter(plusAdapter);
					    }else{
					    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNote.this,
									arrayOfListhistory);
					        Contact_listview.setEmptyView(emptyText);
					       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
						    Contact_listview.setAdapter(lowerdevicesAdapter);	
					    }
	   }
	});
	//mVolleyQueue.add(request);
	// makePostRequest();
	//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
	request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	request.setTag(TAG_REQUEST);	
	mVolleyQueue2.add(request);
	}
	
	public void Historymainmethod() {
		
	    Map<String,String> params = new HashMap<String, String>();
	    params.put("pid", phone);
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/get_history.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    	//parseFlickrImageResponse(response);
		    //	makeSampleHttpRequest2();
		    	
		    	parseHistoryResponse(response);
		    	
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
		    		 errorimahappen="No internet connection tap to Retry displaying history...." ;
		    		 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error  exit the app.try again later  ";
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					 errorimahappen="No internet connection tap to Retry displaying history...." ;
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					 errorimahappen="Slow internet connection tap to Retry displaying history....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
		    	 arrayOfListhistory.clear();
		    	 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					    plusAdapter =  new HistoryAdapter(TakeNote.this,listenertwo,
								arrayOfListhistory);
				        Contact_listview.setEmptyView(emptyText);
				       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
					    Contact_listview.setAdapter(plusAdapter);
					    }else{
					    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNote.this,
									arrayOfListhistory);
					        Contact_listview.setEmptyView(emptyText);
					       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
						    Contact_listview.setAdapter(lowerdevicesAdapter);	
					    }
		    //	int heightInPx = getResources()
				//	      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
		    	// Style stylealert = new Style.Builder()
				//	 .setBackgroundColor(R.color.Red)
					      
					//      .setImageResource(R.drawable.exclamation)
					//      .setGravity(Gravity.CENTER)
					 //     .setTextColor(android.R.color.white)
					 //     .setHeight(heightInPx)
					//      .build();
		    	// Configuration configalert = new Configuration.Builder()
					//		  .setDuration(2000)
							//  .setInAnimation(android.R.anim.slide_in_left)
							 // .setOutAnimation(android.R.anim.slide_out_right)
							//  .build();	     
		    	 //  Crouton alertCrouton=Crouton.makeText(
					//	 getActivity(), 
					  //    errorimahappen, 
					  //    stylealert);
			//	 alertCrouton.setConfiguration(configalert);
				// alertCrouton.show();
		    	 mGoogleNow.progressiveStop();
	     	   Progressbar.setVisibility(View.GONE);
			//	stopLoadingAnim();
					// Contact_listview.setItemsCanFocus(false);
	 			 //   Contact_listview.setEmptyView(emptyText);
					//stopLoadingAnim();
				//	Toast.makeText(getActivity(),
					//	      "hakuna kitu ", Toast.LENGTH_SHORT).show();
					//Set_Referash_Data();
					//Progressbar.setVisibility(View.GONE);
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			//request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	 //  makePostRequest();
	   

	}


	private void parseHistoryResponse(JSONObject response) {//throws JSONException {
		
			try {
				
	            JSONArray items = response.getJSONArray(ARRAY_NAME_HISTORY);
				

	            arrayOfListhistory.clear();
				
				for(int index = 0 ; index < items.length(); index++) {
				
					JSONObject jsonObj = items.getJSONObject(index);
					
					//String farm = jsonObj.getString("username");
				//	String id = jsonObj.getString("post_id");
				//	String secret = jsonObj.getString("secret");
					//String server = jsonObj.getString("server");
					
					//String imageUrl = "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + "_t.jpg";
				//	String imageUrl =jsonObj.getString("title");
					
					HistoryParse model = new HistoryParse();
					//model.setImageUrl(imageUrl);
					//model.setTitle(jsonObj.getString("message"));
					model.setId(jsonObj.getInt(ID_HISTORY));
					model.setName(jsonObj.getString(NAME_HISTORY));
					model.setNote(jsonObj.getString(NOTE_HISTORY));
					model.setTime(jsonObj.getString(DATE_HISTORY));
					
					 // String name = jsonObj.getString(NAME);
	                 //   String secondname = jsonObj.getString(SECOND_NAME);
	                 //   String  email = jsonObj.getString(EMAIL);
	                 //   String  phone =jsonObj.getString(PHONE);
				//	model.setBirthdate(jsonObj.getString(BIRTH_DATE));
				//	model.setEventLocation(jsonObj.getString(EVENT_GOOGLE));
				//	model.setEventVenue(jsonObj.getString(EVENT_VENUE));
					//model.setEventTime(jsonObj.getString(EVENT_TIME));
	                    arrayOfListhistory.add(model);
					
					//dbHandler = new DatabaseHandler(getActivity());
					
	                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						    plusAdapter =  new HistoryAdapter(TakeNote.this,listenertwo,
									arrayOfListhistory);
					        Contact_listview.setEmptyView(emptyText);
					       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
						    Contact_listview.setAdapter(plusAdapter);
						    }else{
						    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNote.this,
										arrayOfListhistory);
						        Contact_listview.setEmptyView(emptyText);
						       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
							    Contact_listview.setAdapter(lowerdevicesAdapter);		
						    }
	                    mGoogleNow.progressiveStop();
	            	    Progressbar.setVisibility(View.GONE);
	         		//	 successCrouton.setConfiguration(configasuccess);
	         			// successCrouton.show();
					//.setVisibility(View.GONE);
					//	Set_Referash_Data();
						//stopLoadingAnim();
						//Crouton.hide(successCrouton);
						
					// mobile = model.getSecondName();
					//mobiletwo = arrayOfList.get(1);
					// mobile = secondname;
					 //String mobileon = arrayOfList.;
					Log.i(LOG, "===== incoming call history inawork");
				//	Log.i(LOG, mobile);
					//test.setText(mobile);
					//Toast.makeText(getApplicationCont(),
					//	     "json impatikana", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Showsuccess();
	}
	public void SaveHistory(){
		// dbtwo.removeAll();     
			
			
		
		 Map<String,String> params = new HashMap<String, String>();
		  params.put("email",user.get("email"));
		  params.put("username",user.get("uname"));
		  params.put("phone",phone);
		  params.put("name",name);
		  params.put("note",savenote.getText().toString());
		//  dbtwo.delete();
		
			 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
			// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
			CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/save_history.php", params,
					new Response.Listener<JSONObject>() {
			    @Override
			    public void onResponse(JSONObject response) {
			    	savenote.setText("");
			    	 loading.setVisibility(View.GONE);
			    	 mGoogleNow.progressiveStop();
			    	 GetCount();
			    	
			    	//dialogmessage="Popup created check your web and write a note";
			    	int heightInPx = getResources()
						      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
			    	 Style stylealert = new Style.Builder()
						 .setBackgroundColor(R.color.Green)
						      
						      .setImageResource(R.drawable.success_icon)
						      .setGravity(Gravity.CENTER)
						      .setTextColor(android.R.color.black)
						      .setHeight(heightInPx)
						      .build();
			    	 Configuration configalert = new Configuration.Builder()
								  .setDuration(2000)
								//  .setInAnimation(android.R.anim.slide_in_left)
								 // .setOutAnimation(android.R.anim.slide_out_right)
								  .build();	     
			    	   Crouton alertCrouton=Crouton.makeText(
							 TakeNote.this, 
						      "Note saved successfully", 
						      stylealert);
					 alertCrouton.setConfiguration(configalert);
					 alertCrouton.show();   
					// PostAllNotes();
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
						dialogmessage="No internet connection Saving Offline";
						  dbtwo.Add_History(name, phone, savenote.getText().toString(), user.get("email"), user.get("uname"));
						//  Toast.makeText(getApplicationContext(),
							//				     "Note inserted successfully ", Toast.LENGTH_SHORT).show();
					} else if( error instanceof ClientError) { 
					} else if( error instanceof ServerError) {
						  dbtwo.Add_History(name, phone, savenote.getText().toString(), user.get("email"), user.get("uname"));
						//Toast.makeText(getApplicationContext(),
							//     "Server error ", Toast.LENGTH_SHORT).show();
						dialogmessage="server error we are working on it ";
						
					} else if( error instanceof AuthFailureError) {
					} else if( error instanceof ParseError) {
						dialogmessage="Parse Error save";
					} else if( error instanceof NoConnectionError) {
						//Toast.makeText(getApplicationContext(),
						//			     "no internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="No internet connection Saving Offline";
						  dbtwo.Add_History(name, phone, savenote.getText().toString(), user.get("email"), user.get("uname"));
					} else if( error instanceof TimeoutError) {
						//Toast.makeText(getApplicationContext(),
							 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="Slow internet connection Saving Offline";
						  dbtwo.Add_History(name, phone, savenote.getText().toString(), user.get("email"), user.get("uname"));
					}
			    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
			    	 loading.setVisibility(View.GONE);
			    	 mGoogleNow.progressiveStop();
						//Intent intent = new Intent(NOTIFICATION);
					   // intent.putExtra(FILEPATH, outputPath);
					  //  intent.putExtra(RESULT, result);
					  //  sendBroadcast(intent);
						
			    }
			});
			//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
				request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
				request.setTag(TAG_REQUEST);	
			mVolleyQueue2.add(request);
		// makePostRequest();
		 
		}
	
	
	
	public void ShowRetryRequest(){
		 // mGoogleNow.progressiveStop();
		int heightInPx2 = getResources()
			      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
	  styleretry= new Style.Builder()
			 .setBackgroundColor(R.color.Yellow)
			      
			      .setImageResource(R.drawable.refresh_icon)
			      .setGravity(Gravity.CENTER_VERTICAL)
			      .setPaddingInPixels(10)
			      .setTextColor(android.R.color.black)
			      .setHeight(heightInPx2)
			      .build();
	 Configuration  configaretry = new Configuration.Builder()
					  .setDuration(Configuration.DURATION_INFINITE)
					// .setInAnimation(android.R.anim.slide_in_left)
					// .setOutAnimation(android.R.anim.slide_out_right)
					  .build();	 
	 
			successCrouton=Crouton.makeText(
		 TakeNote.this, 
			      errorimahappen, 
			      styleretry);
			  successCrouton.setConfiguration(configaretry);
			 // isShownothererrors = true;
			  successCrouton.setOnClickListener(new View.OnClickListener() {
				  @Override
				  public void onClick(View v) {
					  GetCount();
				    Crouton.hide(successCrouton);
				  }
				});

				 successCrouton.show();
				
		// previouscall.setEnabled(true);
		 //nextcall.setEnabled(true);
		 //nextcall.setBackgroundResource(R.drawable.buttonselect);
	}

	public class ListenToPhoneState extends PhoneStateListener {
		private boolean isPhoneCalling = false;

	  public void onCallStateChanged(int state, String incomingNumber)
	    {
	        super.onCallStateChanged(state, incomingNumber);
	        switch (state)
	        {
	        case TelephonyManager.CALL_STATE_IDLE:{
	        	if (isPhoneCalling) {
	        		try {
	        			if(	audioManager.isSpeakerphoneOn()){
			        		audioManager.setSpeakerphoneOn(false);	
			        	}
	        			  mute.setEnabled(true); 
	        			  reject.setVisibility(View.GONE);
	        			  mute.setVisibility(View.GONE);
	        			  loodspeaker.setVisibility(View.GONE);
	        			 voicerecord.setVisibility(View.GONE);
	        			 CONDITION=true;
	        			 calling.setVisibility(View.GONE);
	        			 exit.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
	        			// TakeNote.this.finish();
	     		       // Intent openMainActivity= new Intent(TakeNote.this, MainControl.class);
	     		       // openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	     		      //  startActivity(openMainActivity);	
	        		// GetCount();	
	        		} catch (Exception e) {
	                    Log.v("", "e: " + e.getMessage());
	                    e.printStackTrace();
	                }
	        		// Intent intentservice=new Intent(getActivity(),ClearBusyService.class);
	        	  	//	getActivity().startService(intentservice);		
	          //  Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",  Toast.LENGTH_LONG).show();
	        	  	 // Intent intentservicetwo=new Intent(getActivity(),LastStopService.class);
	        	  	//	getActivity().startService(intentservicetwo);
	        	isPhoneCalling = false;
				}
	            break;}
	        case TelephonyManager.CALL_STATE_OFFHOOK:{
	        	// This entry point is called, when ever the phone attempts to dial a number...
	        	// here check if history for this number is displayed, if so - display that activity with the history + entry field.
	        	// if the history for this phone number is not displayed anywhere, then create that activity...
	        	// [Name, phone, email for person to call]
	        	// [   -- entry field for typing what happens --- ]
	        	// History records:
	        	// [Person: Date: History record]
	        	// [Person: Date: History ...   ]
	        	// Call base: 
	        	
	        	 
	        	  mute.setEnabled(true);    		
				        		
				         
	        	//final Handler handler = new Handler();
		       // handler.postDelayed(new Runnable() {
		        //    public void run() {
		                // TODO: Your application init goes here.
		            	
		            //	activity();
	        	
		           // }
		      //  }, 3000);
	            //CALL_STATE_OFFHOOK;
	          //  Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",  Toast.LENGTH_LONG).show();
	            isPhoneCalling = true;
	            break;
	        }
	        case TelephonyManager.CALL_STATE_RINGING:{
	        	String IncomingTelephoneNumber = incomingNumber;
	        	//Bring Task to front with Android >= Honeycomb
        		//Intent intent = getIntent(getActivity(), MainControl.class);
	            //CALL_STATE_RINGING
	          //  Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",  Toast.LENGTH_LONG).show();
	            break;}
	        default:
	            break;
	        }
	    }

	  }

	  
	  public void Showalert(){
	    	int heightInPx = getResources()
				      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
	    	 Style stylealert = new Style.Builder()
				 .setBackgroundColor(R.color.Yellow)
				      
				      .setImageResource(R.drawable.exclamation)
				      .setGravity(Gravity.CENTER)
				      .setTextColor(android.R.color.black)
				      .setHeight(heightInPx)
				      .build();
	    	 Configuration configalert = new Configuration.Builder()
						  .setDuration(2000)
						//  .setInAnimation(android.R.anim.slide_in_left)
						 // .setOutAnimation(android.R.anim.slide_out_right)
						  .build();	     
	    	   Crouton alertCrouton=Crouton.makeText(
					 TakeNote.this, 
				      "No internet connection Saving Offline...", 
				      stylealert);
			 alertCrouton.setConfiguration(configalert);
			 alertCrouton.show();   	
	    }
	
	  @Override
		public void onResume() {
		    super.onResume();
		   
		 //   Intent intent=new Intent(MainControl.this,CheckMessoVolleyService.class);
		  //  MainControl.this.startService(intent);
		 //  TakeNote.this. registerReceiver(receivertwo, new IntentFilter(CheckNoteidService.NOTIFICATION));
		
		//  MainControl.this. registerReceiver(receiver3, new IntentFilter(GcmIntentService.NOTIFICATION));
	    }
	  public void onStop() {
			super.onStop();
			tManager.listen(listener, ListenToPhoneState.LISTEN_NONE);//Removing the Listner(PhoneStateMonitor) with the TelephonyManager using PhoneStateListener.LISTEN_NONE constant	
		//	TakeNote.this.unregisterReceiver(receivertwo);
			//MainControl.this.unregisterReceiver(receiver2);
		//	MainControl.this.unregisterReceiver(receiver3);
	    }
	  @Override
	  public void onBackPressed() {
		  // TODO Auto-generated method stub
		    if(CONDITION){
		      //  super.onBackPressed();
		        TakeNote.this.finish();
		        Intent openMainActivity= new Intent(TakeNote.this, MainControl.class);
		        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(openMainActivity);
		    }
	  }
	
	 
	
}
