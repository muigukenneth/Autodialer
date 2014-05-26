/**
 * 
 */
package com.autodialer;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
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
import com.android.internal.telephony.ITelephony;
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
import android.app.Activity;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author User
 *
 */
public class IncomingCall extends SherlockActivity{
TextView serchinghistory;
TextView meso;
ListView Contact_listview;
TextView showrecords;
String  errorimahappen;
Boolean CONDITION=false;
Button hangup;
private boolean   isShownothererrors =false;
private RequestQueue mVolleyQueue2;
private SpeedScrollListener listenertwo;
private HistoryAdapter plusAdapter;
private final String TAG_REQUEST = "MY_TAG";
private final String TAG = "MY_TAG_ANSWER";
JsonObjectRequest jsonObjRequesttwo;
TextView emptyText;
DatabaseHandlerUser db;
DatabaseHandlerHistory dbtwo ;
Context context;
EditText savenote;
Button addnote;
Button exit;
Boolean isInternetPresent = false;
ConnectionDetector cd;
private HistoryAdapterLowerDevices lowerdevicesAdapter;
TelephonyManager tManager;
ListenToPhoneState listener;
LinearLayout Progressbar;
private static final String ARRAY_NAME_HISTORY = "History";
private static final String ID_HISTORY = "Id";
private static final String NAME_HISTORY = "SalesName";
private static final String NOTE_HISTORY = "Note";
private static final String DATE_HISTORY = "Time";
List<HistoryParse> arrayOfListhistory;
private static final String LOG = "SALESPACER-DEBBUG";
String messagenew;
String contactname;
Button answer;
Button reject;

Crouton  successCrouton;
Style 	styleretry;
private SmoothProgressBar mGoogleNow;
AudioManager audioManager;
ImageView loodspeaker;
ImageView mute;
ImageView  voicerecord;
ProgressBar loading;
HashMap<String,String> user;
private HomeKeyLocker mHomeKeyLocker;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	//	getSupportActionBar().show();
		// TODO Auto-generated method stub
		setContentView(R.layout.incoming_call);
		 mHomeKeyLocker = new HomeKeyLocker();
		Bundle b = this.getIntent().getExtras();
		 messagenew= b.getString("MESSAGE");
		 contactname= b.getString("NAME");
		  context = getApplicationContext();

		serchinghistory=(TextView)findViewById(R.id.textsearchhistory);
		//meso=(TextView)findViewById(R.id.textsearching);
		 answer = (Button)findViewById(R.id.answer);
	        reject= (Button)findViewById(R.id.decline);
	        hangup= (Button)findViewById(R.id.hangup);
	        loading=(ProgressBar)findViewById(R.id.progress);
	        mGoogleNow = (SmoothProgressBar) findViewById(R.id.google_now);
	        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE); 
            audioManager.setMode(AudioManager.MODE_IN_CALL); 
            audioManager.setSpeakerphoneOn(true);	
		serchinghistory.setText(contactname+" is calling...");
		Contact_listview = (ListView)  findViewById(R.id.listcontacts);
		 savenote = (EditText) findViewById(R.id.etEdit);
		 addnote=(Button)findViewById(R.id.btnadd);
		 exit=(Button)findViewById(R.id.btnexit);
  		 emptyText = (TextView) findViewById(R.id.emptyResults);
  		 loodspeaker=(ImageView)findViewById(R.id.imageViewloadspeaker);
  		 mute=(ImageView)findViewById(R.id.imageViewmute);
		 voicerecord=(ImageView)findViewById(R.id.imageViewrecord);
  		  Progressbar = (LinearLayout)findViewById(R.id.linlaHeaderProgress);
		  showrecords=(TextView)findViewById(R.id.loadingtext);
  		  listenertwo = new SpeedScrollListener();
  		 arrayOfListhistory = new ArrayList<HistoryParse>();
 		db = new DatabaseHandlerUser(IncomingCall.this);
 	    mVolleyQueue2 = Volley.newRequestQueue(IncomingCall.this);  
 	   tManager = (TelephonyManager) 
 	            getSystemService(Context.TELEPHONY_SERVICE);
 	          listener = new ListenToPhoneState();
 	         cd = new ConnectionDetector(IncomingCall.this.getApplicationContext());  
 	   mHomeKeyLocker.lock(IncomingCall.this);
 	
 	  // Load preferences
     //  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
     //  BluetoothHeadset bh = null;
     //  if (prefs.getBoolean("headset_only", false)) {
     //          bh = new BluetoothHeadset(IncomingCall.this, null);
     //  }
    // Check headset status right before picking up the call
    //   if (prefs.getBoolean("headset_only", false) && bh != null) {
      //         if (bh.getState() != BluetoothHeadset.STATE_CONNECTED) {
      //                 bh.close();
        //               return;
         //      }
         //      bh.close();
    //   }
//
 	  user = new HashMap<String, String>();
	  user = db.getUserDetails();
	  dbtwo= new DatabaseHandlerHistory(IncomingCall.this);
		  // home key is locked since then
 	 // WindowManager.LayoutParams lp = getWindow().getAttributes();
	//	lp.buttonBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
	//	lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
	//	getWindow().setAttributes(lp);
 	   Progressbar.setVisibility(View.VISIBLE);
 		 mGoogleNow.progressiveStart();
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
		//final Handler handler = new Handler();
      //  handler.postDelayed(new Runnable() {
         //   public void run() {
                // TODO: Your application init goes here.
 	  answer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	//  TelephonyManager tm = (TelephonyManager) context
               //       .getSystemService(Context.TELEPHONY_SERVICE);
        	  // Make sure the phone is still ringing
              tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
              if (tManager.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
                 
              

              // Answer the phone
              try {
                      answerPhoneAidl(context);
              }
              catch (Exception e) {
                      e.printStackTrace();
                      Log.d("AutoAnswer","Error trying to answer using telephony service.  Falling back to headset.");
                      answerPhoneHeadsethook(context);
              }
              }
          	serchinghistory.setText("Talking with "+contactname);
        	  answer.setVisibility(View.GONE);
    		    reject.setVisibility(View.GONE);
    		    hangup.setVisibility(View.VISIBLE);
    		    mHomeKeyLocker.unlock();
    		    IncomingCall.this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
  		    answer.setVisibility(View.GONE);
		    reject.setVisibility(View.GONE);
		    mute.setVisibility(View.GONE);
		    serchinghistory.setVisibility(View.GONE);
		    voicerecord.setVisibility(View.GONE);
		    loodspeaker.setVisibility(View.GONE);
		    exit.setVisibility(View.VISIBLE);
		    exit.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		    mHomeKeyLocker.unlock();
		    IncomingCall.this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  		  //    IncomingCall.this.finish();
      	//	Intent intent = new Intent(IncomingCall.this, TakeNoteIncomingCall.class);
      		  //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      		    
      		  //  intent.putExtra("PHONE",messagenew);
      		    // Pass all data country
      		 //   intent.putExtra("NAME", contactname );
      		    // Pass all data population
      		  //  intent.putExtra("email",(objBean.getGender()));
      		    //  startActivity(intent); 
        	  } catch (Exception e) {
  		        e.printStackTrace();
  		     //   Log.e(TakeNote.this,
  		        //        "FATAL ERROR: could not connect to telephony subsystem");
  		      //  Log.error(TakeNote.this, "Exception object: " + e);
  		}
          }
      });
      exit.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	 Intent intentnew=new Intent(IncomingCall.this,CheckNoteidService.class);
	        	 IncomingCall.this.startService(intentnew);	 
	        	 if(	audioManager.isSpeakerphoneOn()){
		        		audioManager.setSpeakerphoneOn(false);	
		        	}
	        	   IncomingCall.this.finish();
   		        Intent openMainActivity= new Intent(IncomingCall.this, MainControl.class);
   		        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
   		        startActivity(openMainActivity);
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
	        	    	dbtwo.Add_History(contactname, messagenew, savenote.getText().toString(), user.get("email"), user.get("uname"));
	        	    	savenote.setText("");
	        	        // Internet connection is not present
	        	        // Ask user to connect to Internet
	        	    	Showalert();
	        	    	
	        	    }
	        }
	    });
      hangup.setOnClickListener(new View.OnClickListener() {
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
  		    mute.setVisibility(View.GONE);
		    voicerecord.setVisibility(View.GONE);
		    loodspeaker.setVisibility(View.GONE);
		    serchinghistory.setVisibility(View.GONE);
		    hangup.setVisibility(View.GONE);
		    exit.setVisibility(View.VISIBLE);
		    
  		   //   IncomingCall.this.finish();
      		//Intent intent = new Intent(IncomingCall.this, TakeNoteIncomingCall.class);
      		  //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      		    
      		  //  intent.putExtra("PHONE",messagenew);
      		    // Pass all data country
      		  //  intent.putExtra("NAME", contactname );
      		    // Pass all data population
      		  //  intent.putExtra("email",(objBean.getGender()));
      		   //   startActivity(intent); 
        	  } catch (Exception e) {
  		        e.printStackTrace();
  		     //   Log.e(TakeNote.this,
  		        //        "FATAL ERROR: could not connect to telephony subsystem");
  		      //  Log.error(TakeNote.this, "Exception object: " + e);
  		}
          }
      });
             
          //  }
      //  }, 4000);
 	  GetCount();
	}
	
	
	public void GetCount(){

		//super.onCreate();
	//Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
	//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();



	Map<String,String> params = new HashMap<String, String>();

	params.put("phone",messagenew);


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
				  // IncomingCall.this.finish();
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
	   String dialogmessage = null;
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
				 mGoogleNow.progressiveStop();
				
			
				
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
	    params.put("pid", messagenew);
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
					    plusAdapter =  new HistoryAdapter(IncomingCall.this,listenertwo,
								arrayOfListhistory);
				        Contact_listview.setEmptyView(emptyText);
				       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
					    Contact_listview.setAdapter(plusAdapter);
					    }else{
					    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(IncomingCall.this,
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
			request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
						    plusAdapter =  new HistoryAdapter(IncomingCall.this,listenertwo,
									arrayOfListhistory);
					        Contact_listview.setEmptyView(emptyText);
					       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
						    Contact_listview.setAdapter(plusAdapter);
						    }else{
						    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(IncomingCall.this,
										arrayOfListhistory);
						        Contact_listview.setEmptyView(emptyText);
						       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
							    Contact_listview.setAdapter(lowerdevicesAdapter);
						    }
	            	    Progressbar.setVisibility(View.GONE);
	            		 mGoogleNow.progressiveStop();
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
		  params.put("phone",messagenew);
		  params.put("name",contactname);
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
							 IncomingCall.this, 
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
			    		errorimahappen="No internet connection Saving Offline";
						  dbtwo.Add_History(contactname, messagenew, savenote.getText().toString(), user.get("email"), user.get("uname"));
						//  Toast.makeText(getApplicationContext(),
							//				     "Note inserted successfully ", Toast.LENGTH_SHORT).show();
					} else if( error instanceof ClientError) { 
					} else if( error instanceof ServerError) {
						//Toast.makeText(getApplicationContext(),
							//     "Server error ", Toast.LENGTH_SHORT).show();
						errorimahappen="server error we are working on it ";
						
					} else if( error instanceof AuthFailureError) {
					} else if( error instanceof ParseError) {
						errorimahappen="Parse Error we are working on it";
					} else if( error instanceof NoConnectionError) {
						//Toast.makeText(getApplicationContext(),
						//			     "no internet connection ", Toast.LENGTH_SHORT).show();
						errorimahappen="No internet connection Saving Offline";
						  dbtwo.Add_History(contactname, messagenew, savenote.getText().toString(), user.get("email"), user.get("uname"));
					} else if( error instanceof TimeoutError) {
						//Toast.makeText(getApplicationContext(),
							 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
						errorimahappen="Slow internet connection Saving Offline";
						  dbtwo.Add_History(contactname, messagenew, savenote.getText().toString(), user.get("email"), user.get("uname"));
					}
			    	//Toast.makeText(getApplicationContext(),
					//	     dialogmessage, Toast.LENGTH_SHORT).show();
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
			      .getDimensionPixelOffset(R.dimen.custom_crouton_height2);
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
		 IncomingCall.this, 
			      errorimahappen, 
			      styleretry);
			  successCrouton.setConfiguration(configaretry);
			//  isShownothererrors = true;
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
					 IncomingCall.this, 
				      "No internet connection Saving Offline...", 
				      stylealert);
			 alertCrouton.setConfiguration(configalert);
			 alertCrouton.show();   	
	    }
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	 
	        Log.d("Focus debug", "Focus changed !");
	 
		if(!hasFocus) {
			Log.d("Focus debug", "Lost focus !");
	 
			Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
			sendBroadcast(closeDialog);
		}
	}
	  @Override
	  public void onBackPressed() {
		  if(CONDITION){
		      //  super.onBackPressed();
		        IncomingCall.this.finish();
		        Intent openMainActivity= new Intent(IncomingCall.this, MainControl.class);
		        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(openMainActivity);
		    }
	  }
	  private void answerPhoneHeadsethook(Context context) {
          // Simulate a press of the headset button to pick up the call
          Intent buttonDown = new Intent(Intent.ACTION_MEDIA_BUTTON);            
          buttonDown.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
          context.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");

          // froyo and beyond trigger on buttonUp instead of buttonDown
          Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);              
          buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
          context.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
  }

  @SuppressWarnings("unchecked")
  private void answerPhoneAidl(Context context) throws Exception {
          // Set up communication with the telephony service (thanks to Tedd's Droid Tools!)
         // TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
          Class c = Class.forName(tManager.getClass().getName());
          Method m = c.getDeclaredMethod("getITelephony");
          m.setAccessible(true);
          ITelephony telephonyService;
          telephonyService = (ITelephony)m.invoke(tManager);

          // Silence the ringer and answer the call!
          telephonyService.silenceRinger();
          telephonyService.answerRingingCall();
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
		        			
		        			mute.setVisibility(View.GONE);
		        		    voicerecord.setVisibility(View.GONE);
		        		    loodspeaker.setVisibility(View.GONE);
		        			  answer.setVisibility(View.GONE);
		        			    serchinghistory.setVisibility(View.GONE);
		        			    reject.setVisibility(View.GONE);
		        			    hangup.setVisibility(View.GONE);
		        			    exit.setVisibility(View.VISIBLE);
		        			
		        			    exit.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		        			    mHomeKeyLocker.unlock();
		            		    IncomingCall.this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		            		    CONDITION=true;
		        			// IncomingCall.this.finish();
			        	//	Intent intent = new Intent(IncomingCall.this, TakeNoteIncomingCall.class);
			        		  //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        		    
			        		 //   intent.putExtra("PHONE",messagenew);
			        		    // Pass all data country
			        		 //   intent.putExtra("NAME", contactname );
			        		    // Pass all data population
			        		  //  intent.putExtra("email",(objBean.getGender()));
			        		  //    startActivity(intent); 
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
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		 tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onResume();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub
		tManager.listen(listener, ListenToPhoneState.LISTEN_NONE);//Removing the Listner(PhoneStateMonitor) with the TelephonyManager using PhoneStateListener.LISTEN_NONE constant	
	}

}
