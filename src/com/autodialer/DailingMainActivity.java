package com.autodialer;





import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import net.simonvt.menudrawer.MenuDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;































































































import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.autodialer.R.string;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;









import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseViews;
import com.espian.showcaseview.ShowcaseViews.ItemViewProperties;
import com.espian.showcaseview.targets.ActionItemTarget;
import com.espian.showcaseview.targets.ActionViewTarget;
import com.espian.showcaseview.targets.ViewTarget;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.inscription.ChangeLogDialog;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import android.R.color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DailingMainActivity extends SherlockFragment  {
	 private BroadcastReceiver receiver = new BroadcastReceiver() {
		 @Override
		    public void onReceive(Context context, Intent intent) {
		      Bundle bundle = intent.getExtras();
		      if (bundle != null) {
				    //    String string = bundle.getString(DownloadService.FILEPATH);
				        int resultCode = bundle.getInt(GcmIntentService.RESULT);
				        String message = bundle.getString("message");
				        if (resultCode == Activity.RESULT_OK) {
				        	try {
								json = new JSONObject(message);
								String contactname = json.getString("name");
								String contactphone = json.getString("deal");
								String contactid = json.getString("valid");
								 namedisplay.setText(contactname);
								 phonedisplay.setText(contactphone);
								 String yourdata = db.getDetails(contactid);
									
								  if(yourdata!=null)
								  {
								 int i=Integer.parseInt(yourdata);
								 id=i;
								//int intminus=i-1;
								SharedPreferences.Editor editor = myPrefsnow.edit();
								editor.putInt("num", i);
								editor.commit(); 
								 GetCount();
								//Toast.makeText(MainControl.this,
								 //   ""+intminus, Toast.LENGTH_LONG).show();
								  }
								  // stopyalast.setText(yourdata);
								   //emaildisplay.setText(""+Integer.parseInt(yourdata));
								   
								
								// stopyalast.setText(contactid);	
								 
								 buildercall = new AlertDialog.Builder(getActivity());
							        buildercall.setMessage("Call for contact Name:"+contactname+" Phone:"+ contactphone +" initiated from web?");
							        buildercall.setCancelable(true);
							        buildercall.setPositiveButton("Call ",
							                new DialogInterface.OnClickListener() {
							            public void onClick(DialogInterface dialog, int id) {
							            	//FragmentManager fm = getSupportFragmentManager();
							            	
							            	//if you added fragment via layout xml
							            //	DailingMainActivity fragment = (DailingMainActivity)fm.findFragmentByTag("FRAGMENT_TAG");
							            	//fragment.call();
							            	call();	
							                dialog.cancel();
							            	
							            }
							        });
							        buildercall.setNegativeButton("Cancel",
							                new DialogInterface.OnClickListener() {
							            public void onClick(DialogInterface dialog, int id) {
							                dialog.cancel();
							            }
							        });
							        alertcall = buildercall.create();
						    		alertcall.show();		 
									
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
				    			   
				    				
				    		       
				        	//}
				        } else {	        			 
				    		        		 }
		      
		 
		 }
		      
		 }   		       
		  };
		  private BroadcastReceiver receivertwo = new BroadcastReceiver() {
				 @Override
				    public void onReceive(Context context, Intent intent) {
				      Bundle bundle = intent.getExtras();
				      if (bundle != null) {
						    //    String string = bundle.getString(DownloadService.FILEPATH);
						        int resultCode = bundle.getInt("RESULT");
						        String username = bundle.getString("USERNAME");
						        String error = bundle.getString("ERROR");
						        if (resultCode == 1) {
						        	
						        	//Toast.makeText(getActivity(),
									 //    " quite busy ", Toast.LENGTH_SHORT).show();	
						        	call();	   
						    				
						    		       
						        	//}
						        } else if(resultCode == 2) {
						        	//Toast.makeText(getActivity(),
									  //  " not busy ", Toast.LENGTH_SHORT).show();
						        	int heightInPx = getResources()
										      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
							    	 Style stylealert = new Style.Builder()
										 .setBackgroundColor(R.color.Yellow)
										      
										      .setImageResource(R.drawable.warning)
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
											 getActivity(), 
										      "Phone Number is Busy by User "+ username+".Please try again later", 
										      stylealert);
									 alertCrouton.setConfiguration(configalert);
									 alertCrouton.show();   	
						    	 }else{
						    		 int heightInPx = getResources()
										      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
							    	 Style stylealert = new Style.Builder()
										 .setBackgroundColor(R.color.Red)
										      
										      .setImageResource(R.drawable.exclamation)
										      .setGravity(Gravity.CENTER)
										      .setTextColor(android.R.color.white)
										      .setHeight(heightInPx)
										      .build();
							    	 Configuration configalert = new Configuration.Builder()
												  .setDuration(3000)
												//  .setInAnimation(android.R.anim.slide_in_left)
												 // .setOutAnimation(android.R.anim.slide_out_right)
												  .build();	     
							    	   Crouton alertCrouton=Crouton.makeText(
											 getActivity(), 
										      error, 
										      stylealert);
									 alertCrouton.setConfiguration(configalert);
									 alertCrouton.show();   	  			 
						    		        		 }
				      
				 
				 }
				      
				 }   		       
				  };
				  private BroadcastReceiver receiverthree = new BroadcastReceiver() {
						 @Override
						    public void onReceive(Context context, Intent intent) {
						      Bundle bundle = intent.getExtras();
						      if (bundle != null) {
								    //    String string = bundle.getString(DownloadService.FILEPATH);
								        int resultCode = bundle.getInt("RESULT");
								         idnoma = bundle.getString("ID");
								        String error = bundle.getString("ERROR");
								        if (resultCode == 1) {
								        	
								        	//Toast.makeText(getActivity(),
											 //    " quite busy ", Toast.LENGTH_SHORT).show();	
								        	
								        	SaveHistory();	
								    		       
								        	//}
								        } else if(resultCode == 2) {
								        	UpdateHistory();
								        	//Toast.makeText(getActivity(),
											  //  " not busy ", Toast.LENGTH_SHORT).show();
								        	
								    	 }else{
								    				 
								    		        		 }
						      
						 
						 }
						      
						 }   		       
						  };
	 private static final float SHOWCASE_KITTEN_SCALE = 1.2f;
	    private static final float SHOWCASE_LIKE_SCALE = 0.5f;
	    public static final float SHOWCASE_SPINNER_SCALE = 0.5f;
	    public static final float SHOWCASE_OVERFLOW_ITEM_SCALE = 1f;
	    ShowcaseView.ConfigOptions mOptions = new ShowcaseView.ConfigOptions();
	    ShowcaseViews mViews;
	    ShowcaseView sv;
	    //crouton notifation declaration
	    String dialogmessage;
	    EditText savenote;
		Button addnote;
		//initialize the call number in listner
		String IncomingTelephoneNumber;
	     Configuration configainfo;
	   String idnoma;
	     int i;
		  int intminus;
	    Style styleinfo;
	    //crouton toast
	     Crouton  successCrouton;
	    private SmoothProgressBar mGoogleNow;
	    Crouton infoCrouton;
	    String  errorimahappen;
	    Style 	styleretry;
	    // flag for Internet connection status
	    Boolean isInternetPresent = false;
	     
	    // Connection detector class
	    ConnectionDetector cd;
	    private boolean isShown = false;
	    boolean   isShownretry =false;
	    private boolean   isShownothererrors =false;
	TextView login;
	boolean shouldExecuteOnResume;
	// Button add_btn;
	ListView Contact_listview;
	TextView emptyText;
	    ArrayList<Contact> contact_data ;
		ArrayList<Contact> contact_array_from_db ;
	    ContactsAdapter cAdapter;
	    DatabaseHandler db;
	    String Toast_msg;
	    SharedPreferences myPrefs;
	    View promptsView;
	    PendingIntent pendingIntent;
	    DatabaseHandler dbHandler;
	  String lastopint;
//Button getcontacts;
Button call;
Button  previouscall;
Button nextcall;
ListView listtest;
LinearLayout Progressbar;
TextView showrecords;
AlertDialog.Builder alertDialogBuilder;
AlertDialog alertDialog;
 EditText userInput;
 EditText edittextusername;
 EditText edittextemail;
 TextView yourTextView;
 TextView namedisplay;
 TextView phonedisplay;
 //TextView emaildisplay;
 TextView stopyalast;
 int laststop;
 int id;
 ProgressBar loading;
//Context ctx;
final Context  ctx = getActivity();
SharedPreferences myPrefsnow;
private SpeedScrollListener listenertwo;
private static final String LOG = "AUTO-DAILER-DEBBUG";
ArrayList<ItemParse> arrayOfList;
  private static final String ARRAY_NAME = "contacts";
	private static final String ID = "id";
	private static final String NAME = "contactname";
	//event venue
	// static  String PROFILE = "event image";
	private static final String PHONE = "contactphone";
	//private static final String EMAIL = "Savedemail";
	private static final String SECOND_NAME = "Secondname";
	private RequestQueue mVolleyQueue2;
	private DailingNumbersAdapter plusAdapter2;
	private HistoryAdapter plusAdapter;
	private HistoryAdapterLowerDevices lowerdevicesAdapter;
	private final String TAG_REQUEST = "MY_TAG";
	JsonObjectRequest jsonObjRequesttwo;
	 HashMap<String,String> user;
	 DatabaseHandlerUser dbtwo ;
		private RequestQueue mVolleyQueue;
		ProgressDialog pd;
	//	  private final String TAG_REQUEST = "MY_TAG";
	 // push  notification messages come here
	 public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	   // please enter your sender id
	    String SENDER_ID = "115389726962";
	    JSONObject json;
	    static final String TAG = "GCMDemo";
	    GoogleCloudMessaging gcm;
	    String msg;
	    Context context;
	    String regid;
	    TelephonyManager tManager;
	    ListenToPhoneState listener;
	    AlertDialog alertcall;
	    AlertDialog.Builder buildercall;
	    private static final String LOGREFRESH = "NAIROBI-EVENTS-REFRESH";
	    private MenuItem mRefreshMenu;
	    private static final String ARRAY_NAME_HISTORY = "History";
		private static final String ID_HISTORY = "Id";
		private static final String NAME_HISTORY = "SalesName";
		private static final String NOTE_HISTORY = "Note";
		private static final String DATE_HISTORY = "Time";
		List<HistoryParse> arrayOfListhistory;
		 private void startLoadingAnim() {
		        if (mRefreshMenu != null) {
		            Log.i(LOGREFRESH, "===== start loading");
		            ImageView iv = (ImageView) mRefreshMenu.getActionView();
		            Animation rotation = AnimationUtils.loadAnimation(getActivity(),
		                    R.animator.refresh_rotate);
		            rotation.setRepeatCount(Animation.INFINITE);
		            iv.startAnimation(rotation);
		        }
		    }

		    private void stopLoadingAnim() {
		        if (mRefreshMenu != null) {
		            Log.i(LOGREFRESH, "===== stop loading");
		            ImageView iv = (ImageView) mRefreshMenu.getActionView();
		            iv.setImageResource(R.drawable.ic_action_refresh);
		            iv.clearAnimation();
		        }
		    }
		   
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
	  ViewGroup viewGroup = (ViewGroup) container;
	//shouldExecuteOnResume = false;
	   setRetainInstance(true);
	   setHasOptionsMenu(true);
	//setHasOptionsMenu(true);
   
	   shouldExecuteOnResume = false;
    View mLinearLayout = inflater.inflate(R.layout.activity_dailing_main, container, false);
    //Drawable background = this.getResources().getDrawable(R.color.Blue);
	// ActionBar bar = getSherlockActivity().getSupportActionBar(); 
	// bar.setBackgroundDrawable(background);
   
    dbtwo= new DatabaseHandlerUser(getActivity());
    dbHandler=new DatabaseHandler(getActivity());
    user = new HashMap<String, String>();
     user = dbtwo.getUserDetails();
    login = (TextView) mLinearLayout.findViewById(R.id.textwelcome);
    savenote = (EditText) mLinearLayout.findViewById(R.id.etEdit);
    loading=(ProgressBar)mLinearLayout.findViewById(R.id.progress);
	 addnote=(Button)mLinearLayout.findViewById(R.id.btnadd);
    namedisplay = (TextView) mLinearLayout.findViewById(R.id.example_itemname);
    phonedisplay = (TextView) mLinearLayout.findViewById(R.id.example_itemphone);
   // emaildisplay = (TextView) mLinearLayout.findViewById(R.id.example_itememail);
    stopyalast = (TextView) mLinearLayout.findViewById(R.id.textlaststop);

    tManager = (TelephonyManager) 
            getActivity().getSystemService(Context.TELEPHONY_SERVICE);
          listener = new ListenToPhoneState();
          tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE); 
    login.setText(user.get("uname"));
    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
      //   
    pd = new ProgressDialog(getActivity());
    pd.setMessage("Registering your device to our servers just a moment");
   // mVolleyQueue = Volley.newRequestQueue(getActivity()); 
   // checkPlayServices();
    context = getActivity().getApplicationContext();
   // getActivity(). registerReceiver(receiver, new IntentFilter(GcmIntentService.NOTIFICATION));
    // creating connection detector class instance
    cd = new ConnectionDetector(getActivity().getApplicationContext());
    // Create reciever object
    // Now find the PullToRefreshLayout to setup
    mGoogleNow = (SmoothProgressBar) mLinearLayout.findViewById(R.id.google_now);
   
	 // listnew.setOnScrollListener(listener);
	//	setContentView(R.layout.activity_dailing_main);
		  //yourTextView = (TextView) mLinearLayout.findViewById(R.id.textViewtest);
		  call=(Button)mLinearLayout.findViewById(R.id.buttoncallstart);
		  previouscall=(Button)mLinearLayout.findViewById(R.id.buttoncallprevious);
		  nextcall=(Button)mLinearLayout.findViewById(R.id.buttoncallnext);
		  
		  Progressbar = (LinearLayout)mLinearLayout.findViewById(R.id.linlaHeaderProgress);
		  showrecords=(TextView)mLinearLayout.findViewById(R.id.loadingtext);
		// call.setTextColor(Color.WHITE);
	   	//	call.setBackgroundResource(R.drawable.button_gradient);
	   		Contact_listview = (ListView) mLinearLayout. findViewById(R.id.listcontacts);
	   		 emptyText = (TextView) mLinearLayout.findViewById(R.id.emptyResults);
	   	    mGoogleNow.progressiveStop();
		//  nextcall=(Button)mLinearLayout.findViewById(R.id.buttonnextcall);
	   	  myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  promptsView  = mLinearLayout.inflate(getActivity(), R.layout.prompt, null);
		  listenertwo = new SpeedScrollListener();
		  Contact_listview.setOnScrollListener(listenertwo);
		  //Dailingmainmethod();
		  Progressbar.setVisibility(View.VISIBLE);
		//Launch change log dialog
			final ChangeLogDialog changeLogDialog = new ChangeLogDialog(getActivity());
			changeLogDialog.setStyle("h1 { margin-left: 10px; font-size: 12pt; color: #006b9a; margin-bottom: 0px;}"
	                + "li { margin-left: 0px; font-size: 12pt; padding-top: 10px; }"
	                + "ul { padding-left: 30px; margin-top: 0px; }"
	                + ".summary { margin-left: 10px; font-size: 10pt; color: #006b9a; margin-top: 5px; display: block; clear: left; }"
	                + ".date { margin-left: 10px; font-size: 10pt; color: #006b9a; margin-top: 5px; display: block; }");
		
		//  call.setVisibility(View.GONE);
			SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
			boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN4CATELOG", true);
			if (isFirstRun)
			{
				changeLogDialog.show();
			    // Code to run once
			    SharedPreferences.Editor editor = wmbPreference.edit();
			    editor.putBoolean("FIRSTRUN4CATELOG", false);
			    editor.commit();
			}

		 addnote.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	if(savenote.getText().toString().length() > 0) {
		        	     // editText is not empty
		        	 Intent intentnew=new Intent(getActivity(),CheckNoteidService.class);
		        	 getActivity().startService(intentnew);	 
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
		        }
		    });
		
		 
	   
	        	// Toast.makeText(ctx,  "call state :" + , Toast.LENGTH_LONG).show(); 
		 nextcall.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		          // String number=String.valueOf(phonedisplay.getText().toString());
		        	//int kuty=(Integer.parseInt(number)+1);
		        	int kuty=(id+1);
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", kuty);
						editor.commit(); 
						
						Getcontactdisplay();
					  //  Historymainmethod();
						//Toast.makeText(getActivity(),
							//     "next contact is "+String.valueOf(kuty), Toast.LENGTH_SHORT).show();
		        }  	 
		    });
		 previouscall.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	int kuty=(id-1);
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", kuty);
						editor.commit(); 
						
						Getcontactbackwarddisplay();
					  //  Historymainmethod();
		        }  	 
		    });
	    call.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	 isInternetPresent = cd.isConnectingToInternet();

	        	    // check for Internet status
	        	    if (isInternetPresent) {
	        	    	if(phonedisplay==null){
	        	        // Internet Connectio
	        	    	// Intent intentnew=new Intent(getActivity(),CheckBusyService.class);
	      	           // Bundle extras = intentnew.getExtras();
	      	          //  intentnew.putExtra("ID",  stopyalast.getText().toString());
	      	           // intentnew.putExtra("PHONE", phonedisplay.getText().toString());
	      	          //  intentnew.putExtra("NAME", namedisplay.getText().toString());
	      			//	getActivity().startService(intentnew);	
	        	    	}else{
	        	    	call();
	        	    	}
	        	    }else {
	        	        // Internet connection is not present
	        	        // Ask user to connect to Internet
	        	    	Showalert();
	        	    }
	        }  	 
	    });
	   
	    
		return mLinearLayout;
//	}
//});
//}
	    
	}
@Override
public void onActivityCreated(Bundle savedInstanceState) {
	 super.onActivityCreated(savedInstanceState);
	 
	 arrayOfList = new ArrayList<ItemParse>();
	 contact_data = new ArrayList<Contact>();
	 arrayOfListhistory = new ArrayList<HistoryParse>();
		db = new DatabaseHandler(getActivity());
	    mVolleyQueue2 = Volley.newRequestQueue(getActivity());   
	   // Dailingmainmethod();
	    Dailingmainmethod();
	  
	
		        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
		        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN4TUTORIAL", true);
		        if (isFirstRun)
		        {
		        	 mOptions.block = false;
				        mOptions.hideOnClickOutside = false;
				        mOptions.shotType = ShowcaseView.TYPE_NO_LIMIT;
				     
				        mViews = new ShowcaseViews(getSherlockActivity(),
				                new ShowcaseViews.OnShowcaseAcknowledged() {
				            @Override
				            public void onShowCaseAcknowledged(ShowcaseView showcaseView) {
				               Toast.makeText(getActivity(), "tutorial finished you are ready to go", Toast.LENGTH_SHORT).show();
				            }
				        });
				        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.buttoncallstart,
				                R.string.showcase_autodail_title,
				                R.string.showcase_autodail_message,
				                SHOWCASE_LIKE_SCALE,mOptions));
				        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.buttoncallprevious,
				                R.string.showcase_dailprevious_title,
				                R.string.showcase_dailprevious_message,
				                SHOWCASE_LIKE_SCALE,mOptions));
				        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.buttoncallnext,
				                R.string.showcase_dailnext_title,
				                R.string.showcase_dailnext_message,
				                SHOWCASE_LIKE_SCALE,mOptions));
				        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.example_itemphone,
				                R.string.showcase_listview_title,
				                R.string.showcase_listview_message,
				                SHOWCASE_KITTEN_SCALE,mOptions));
				        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.listcontacts,
				                R.string.showcase_history_title,
				                R.string.showcase_history_message,
				                SHOWCASE_KITTEN_SCALE,mOptions));
				        ShowcaseView.ConfigOptions options = new ShowcaseView.ConfigOptions();
				        options.shotType = ShowcaseView.TYPE_NO_LIMIT;
				      //  options.showcaseId = 1234;
				     
				        ShowcaseView.ConfigOptions configOptions = new ShowcaseView.ConfigOptions();
				        configOptions.fadeInDuration = 700;
				        configOptions.fadeOutDuration = 700;
				        configOptions.block = true;
				        //mViews.addView(new ItemViewProperties(ItemViewProperties.ID_SPINNER, R.string.showcase_refresh_title, R.string.showcase_refresh_message, ShowcaseView.ITEM_SPINNER, SHOWCASE_SPINNER_SCALE, configOptions));
				        mViews.addView(new ItemViewProperties(ItemViewProperties.ID_OVERFLOW, R.string.showcase_drawer_title, R.string.showcase_drawer_message, ShowcaseView.ITEM_ACTION_OVERFLOW, SHOWCASE_OVERFLOW_ITEM_SCALE,mOptions));
				        mViews.addView(new ItemViewProperties(R.id.action_refresh, R.string.showcase_refresh_title, R.string.showcase_refresh_message, ShowcaseView.ITEM_ACTION_ITEM, SHOWCASE_SPINNER_SCALE,mOptions ));
		        	  mViews.show(); 
		            // Code to run once
		            SharedPreferences.Editor editor = wmbPreference.edit();
		            editor.putBoolean("FIRSTRUN4TUTORIAL", false);
		            editor.commit();
		        }
			         
		     
}
public void Getcontactdisplayfirst() {
	 isInternetPresent = cd.isConnectingToInternet();

	    // check for Internet status
	    if (isInternetPresent) {
	    	  Intent intentservice=new Intent(getActivity(),LastStopService.class);
	    		getActivity().startService(intentservice);	
	    		 if(isShownothererrors){
					// isShownothererrors = false;
	    			 Crouton.hide(successCrouton);
				}else{
					
				}
	    		 
	        // Internet Connection is Present
	        // make HTTP requests
ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
int laststopn = myPrefsnow.getInt("num", 1);
laststop=laststopn-1;
if(!(laststop==0)){
if(contact_array_from_db.size() > laststop){
	
	  id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	 nextcall.setEnabled(true);
		previouscall.setEnabled(true);
	 GetCountRefresh();
	 
	 
}else{
	//Toast.makeText(getActivity(),
	//     " ", Toast.LENGTH_SHORT).show();
}
}else{
	id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	 nextcall.setEnabled(true);
	 nextcall.setBackgroundResource(R.drawable.buttonselect);
	 previouscall.setEnabled(false);
	 previouscall.setBackgroundColor(Color.parseColor("#808080"));
	 GetCountRefresh();
} 
	  
}else {
    // Internet connection is not present
    // Ask user to connect to Internet
	Showalert();
}

}
public void Getcontactdisplayfirstrefresh() {
	 isInternetPresent = cd.isConnectingToInternet();

	    // check for Internet status
	    if (isInternetPresent) {
	    	  Intent intentservice=new Intent(getActivity(),LastStopService.class);
	    		getActivity().startService(intentservice);	
	    		stopLoadingAnim();
					// isShownothererrors = false;
	    			 Crouton.hide(successCrouton);
	    		Crouton.cancelAllCroutons();
	        // Internet Connection is Present
	        // make HTTP requests
ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
int laststopn = myPrefsnow.getInt("num", 1);
laststop=laststopn-1;
if(!(laststop==0)){
if(contact_array_from_db.size() > laststop){
	
	  id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	 nextcall.setEnabled(true);
		previouscall.setEnabled(true);
	 GetCountRefresh();
	 
}else{
	//Toast.makeText(getActivity(),
	//     " ", Toast.LENGTH_SHORT).show();
}
}else{
	id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	 nextcall.setEnabled(true);
	 nextcall.setBackgroundResource(R.drawable.buttonselect);
	 previouscall.setEnabled(false);
	 previouscall.setBackgroundColor(Color.parseColor("#808080"));
	 GetCountRefresh();
} 
	  
}else {
   // Internet connection is not present
   // Ask user to connect to Internet
	Showalert();
}

}
public void Getcontactdisplay() {
	 // get Internet status
    isInternetPresent = cd.isConnectingToInternet();

    // check for Internet status
    if (isInternetPresent) {
        // Internet Connection is Present
        // make HTTP requests
    	 
ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

int kuverify=laststop+1;
if(contact_array_from_db.size() > kuverify){
	 Progressbar.setVisibility(View.VISIBLE);
	  mGoogleNow.progressiveStart();;
	  showrecords.setText("loading records...");
	myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
	int laststopn = myPrefsnow.getInt("num", 0);
	laststop=laststopn-1;
	  id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	
	 nextcall.setEnabled(true);
	 nextcall.setBackgroundResource(R.drawable.buttonselect);
	 previouscall.setEnabled(true);
	 previouscall.setBackgroundResource(R.drawable.buttonselect);
	 GetCount();
	   
		
	  
			
}else{
	//Toast.makeText(getActivity(),
	 //    "calls finished ", Toast.LENGTH_SHORT).show();
	SharedPreferences.Editor editor = myPrefsnow.edit();
	editor.putInt("num", 1);
	editor.commit(); 
	nextcall.setEnabled(false);
	nextcall.setBackgroundColor(Color.parseColor("#808080"));
}
    } else {
        // Internet connection is not present
        // Ask user to connect to Internet
    	Showalert();
    }
}
public void Getcontactbackwarddisplay() {
	 // get Internet status
    isInternetPresent = cd.isConnectingToInternet();

    // check for Internet status
    if (isInternetPresent) {
        // Internet Connection is Present
        // make HTTP requests
     
    	 
ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

int kuverify=laststop-1;
if(!(laststop==0)){
if(contact_array_from_db.size() > kuverify){
	 Progressbar.setVisibility(View.VISIBLE);
	  mGoogleNow.progressiveStart();
	  showrecords.setText("loading records...");
	myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
	int laststopn = myPrefsnow.getInt("num", 0);
	laststop=laststopn-1;
	  id = contact_array_from_db.get(laststop).getID();
	 String mobile = contact_array_from_db.get(laststop).getPhoneNumber();
	 String name = contact_array_from_db.get(laststop).getName();
	 String email = contact_array_from_db.get(laststop).getEmail();
	 namedisplay.setText(name);
	 phonedisplay.setText(mobile);
	// emaildisplay.setText(email);
	 stopyalast.setText(String.valueOf(id));
	 nextcall.setEnabled(true);
	 nextcall.setBackgroundResource(R.drawable.buttonselect);
	 GetCount();
	 
	 
}else{
	Toast.makeText(getActivity(),
	     "no more calls ", Toast.LENGTH_SHORT).show();
	 previouscall.setEnabled(false);
}
}else{
	SharedPreferences.Editor editor = myPrefsnow.edit();
	editor.putInt("num", 1);
	editor.commit(); 
	 previouscall.setEnabled(false);
	 previouscall.setBackgroundColor(Color.parseColor("#808080"));
}
    } else {
        // Internet connection is not present
        // Ask user to connect to Internet
    	Showalert();
    }
}
public int convertDpToPixel(float dp) {
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    float px = dp * (metrics.densityDpi / 160f);
    return (int) px;
}

public void Dailingmainmethod() {
	 mGoogleNow.progressiveStart();
	  startLoadingAnim();
    Map<String,String> params = new HashMap<String, String>();
    params.put("pid",user.get("email"));
	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/get_contacts/getcontacts.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	//parseFlickrImageResponse(response);
	    //	makeSampleHttpRequest2();
	    	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
       	 if(contact_array_from_db.size() >= 0){
       		   try {
	    	dbHandler.delete();
       		  } catch (ActivityNotFoundException activityException) {
  	            Log.e("delete previous data", "No deleted", activityException);
  	        }
  	        	 } else{
  			  //  Toast.makeText(getActivity(), "No Deleting is happening",  Toast.LENGTH_LONG).show(); 
  				// SharedPreferences.Editor editor = myPrefsnow.edit();
  				//	editor.putInt("num", 1);
  				//	editor.commit();  
  				//	finish();
  		 }
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
					 errorimahappen="No internet connection Retrying...." ;
				
					 Dailingmainmethodrefresh();
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error  exit the app.try again later  ";
					
					
					//Toast.makeT
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					 errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					
					Dailingmainmethodrefresh();
					 errorimahappen=" No internet connection Retrying....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					 errorimahappen="Slow internet connection Retrying....  ";
					
					 Dailingmainmethodrefresh();
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					 
					//Toast.makeT
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
				nextcall.setEnabled(false);
				previouscall.setEnabled(false);
				  Progressbar.setVisibility(View.GONE);
				  mGoogleNow.progressiveStop();
				stopLoadingAnim();
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


private void parseFlickrImageResponse(JSONObject response) {//throws JSONException {
	
	//if(response.has("nyc")) {
		try {
			//dbHandler.delete();
			//JSONObject photos = response.getJSONObject("success");
			// Get the data array containing posts
            JSONArray items = response.getJSONArray(ARRAY_NAME);
			//JSONArray items = photos.getJSONArray("posts");

			arrayOfList.clear();
			
			for(int index = 0 ; index < items.length(); index++) {
			
				JSONObject jsonObj = items.getJSONObject(index);
				
				
				
				ItemParse model = new ItemParse();
				
				model.setId(jsonObj.getInt(ID));
				model.setName(jsonObj.getString(NAME));
				//model.setSecondName(jsonObj.getString(SECOND_NAME));
				//model.setEmail(jsonObj.getString(EMAIL));
				model.setPhone(jsonObj.getInt(PHONE));
				  String name = jsonObj.getString(NAME);
                   // String secondname = jsonObj.getString(SECOND_NAME);
                  //  String  email = jsonObj.getString(EMAIL);
                    String  phone =jsonObj.getString(PHONE);
               
                    String id = Integer.toString(jsonObj.getInt(ID));
			
				arrayOfList.add(model);
				
				//dbHandler = new DatabaseHandler(getActivity());
				
				dbHandler.Add_Contact(new Contact(name,
		    			  phone, id));
				 
				Getcontactdisplayfirst();
			//	Crouton.showText(
				//	      getActivity(), 
				//	      "Contacts have been downloaded", 
					//      Style.CONFIRM);
				// Progressbar.setVisibility(View.GONE);
				//	Set_Referash_Data();
					//stopLoadingAnim();
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
public void Dailingmainmethodrefresh() {
	 mGoogleNow.progressiveStart();
	  startLoadingAnim();
   Map<String,String> params = new HashMap<String, String>();
   params.put("pid",user.get("email"));
	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/get_contacts/getcontacts.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	//parseFlickrImageResponse(response);
	    //	makeSampleHttpRequest2();
	    	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
      	 if(contact_array_from_db.size() >= 0){
      		   try {
	    	dbHandler.delete();
      		  } catch (ActivityNotFoundException activityException) {
 	            Log.e("delete previous data", "No deleted", activityException);
 	        }
 	        	 } else{
 			  //  Toast.makeText(getActivity(), "No Deleting is happening",  Toast.LENGTH_LONG).show(); 
 				// SharedPreferences.Editor editor = myPrefsnow.edit();
 				//	editor.putInt("num", 1);
 				//	editor.commit();  
 				//	finish();
 		 }
	    	getcontactrefresh(response);
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
					 stopLoadingAnim();
					 Dailingmainmethod();
					 errorimahappen="No internet connection Retrying...." ;
					
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error  exit the app.try again later  ";
					
					
					//Toast.makeT
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					 errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					Dailingmainmethod();
					stopLoadingAnim();
					 errorimahappen=" No internet connection Retrying....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					stopLoadingAnim();
					Dailingmainmethod();
					 errorimahappen="Slow internet connection Retrying....  ";
					
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					 
					//Toast.makeT
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
				nextcall.setEnabled(false);
				previouscall.setEnabled(false);
				  Progressbar.setVisibility(View.GONE);
				  mGoogleNow.progressiveStop();
				stopLoadingAnim();
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


private void getcontactrefresh(JSONObject response) {//throws JSONException {
	
	//if(response.has("nyc")) {
		try {
			//dbHandler.delete();
			//JSONObject photos = response.getJSONObject("success");
			// Get the data array containing posts
           JSONArray items = response.getJSONArray(ARRAY_NAME);
			//JSONArray items = photos.getJSONArray("posts");

			arrayOfList.clear();
			
			for(int index = 0 ; index < items.length(); index++) {
			
				JSONObject jsonObj = items.getJSONObject(index);
				
				
				
				ItemParse model = new ItemParse();
				
				model.setId(jsonObj.getInt(ID));
				model.setName(jsonObj.getString(NAME));
				//model.setSecondName(jsonObj.getString(SECOND_NAME));
				//model.setEmail(jsonObj.getString(EMAIL));
				model.setPhone(jsonObj.getInt(PHONE));
				  String name = jsonObj.getString(NAME);
                  // String secondname = jsonObj.getString(SECOND_NAME);
                 //  String  email = jsonObj.getString(EMAIL);
                   String  phone =jsonObj.getString(PHONE);
              
                   String id = Integer.toString(jsonObj.getInt(ID));
			
				arrayOfList.add(model);
				
				//dbHandler = new DatabaseHandler(getActivity());
				
				dbHandler.Add_Contact(new Contact(name,
		    			  phone, id));
				 
				Getcontactdisplayfirstrefresh();
			//	Crouton.showText(
				//	      getActivity(), 
				//	      "Contacts have been downloaded", 
					//      Style.CONFIRM);
				// Progressbar.setVisibility(View.GONE);
				//	Set_Referash_Data();
					//stopLoadingAnim();
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
public void Historymainmethod() {
	
    Map<String,String> params = new HashMap<String, String>();
    params.put("pid", phonedisplay.getText().toString());
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
	    	

	    		   GetCountRetry();
	    		 errorimahappen="No internet connection Retrying...." ;
	    		 if(isShownothererrors){
					 isShownothererrors = false;	
				}else{
					ShowRetryRequest();	
				}
				//Toast.makeT
				//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof ClientError) { 
			} else if( error instanceof ServerError) {
				 errorimahappen=" Server error exit the app try again later  ";
				//Toast.makeText(getActivity(),
					//     "Server error ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof AuthFailureError) {
			} else if( error instanceof ParseError) {
				 errorimahappen="fatal error we are fixing it.... ";
			} else if( error instanceof NoConnectionError) {
				 

				   GetCountRetry();
				 errorimahappen="No internet connection Retrying...." ;
				 if(isShownothererrors){
					 isShownothererrors = false;	
				}else{
					ShowRetryRequest();	
				}
				//Toast.makeT
			//	Toast.makeText(getActivity(),
				//			     "no internet connection ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof TimeoutError) {
			

				   GetCountRetry();
				 errorimahappen="Slow internet connection Retrying....  ";
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
	    		    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
	    					arrayOfListhistory);
	    	        Contact_listview.setEmptyView(emptyText);
	    	       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
	    		    Contact_listview.setAdapter(plusAdapter);
	    		    }else{
	    		    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
			stopLoadingAnim();
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
                	    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
                				arrayOfListhistory);
                        Contact_listview.setEmptyView(emptyText);
                       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
                	    Contact_listview.setAdapter(plusAdapter);
                	    }else{
                	    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
					stopLoadingAnim();
					//Crouton.hide(successCrouton);
					
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
		
		if(isShown){
			 isShown = false;	
		}else{
			Showsuccess();	
		}
}

public void Historymainmethodrefresh() {
	
    Map<String,String> params = new HashMap<String, String>();
    params.put("pid", phonedisplay.getText().toString());
	 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
	// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/get_history.php", params,
			new Response.Listener<JSONObject>() {
	    @Override
	    public void onResponse(JSONObject response) {
	    	//parseFlickrImageResponse(response);
	    //	makeSampleHttpRequest2();
	    	
	    	parseHistoryResponserefresh(response);
	    	
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
	    	

	    		   GetCountRetry();
	    		 errorimahappen="No internet connection Retrying...." ;
	    		 if(isShownothererrors){
					 isShownothererrors = false;	
				}else{
					ShowRetryRequest();	
				}
				//Toast.makeT
				//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof ClientError) { 
			} else if( error instanceof ServerError) {
				 errorimahappen=" Server error exit the app try again later  ";
				//Toast.makeText(getActivity(),
					//     "Server error ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof AuthFailureError) {
			} else if( error instanceof ParseError) {
				 errorimahappen="fatal error we are fixing it.... ";
			} else if( error instanceof NoConnectionError) {
				

				   GetCountRetry();
				 errorimahappen="No internet connection Retrying...." ;
				 if(isShownothererrors){
					 isShownothererrors = false;	
				}else{
					ShowRetryRequest();	
				}
				//Toast.makeT
			//	Toast.makeText(getActivity(),
				//			     "no internet connection ", Toast.LENGTH_SHORT).show();
			} else if( error instanceof TimeoutError) {
				

				   GetCountRetry();
				 errorimahappen="Slow internet connection Retrying....  ";
				 if(isShownothererrors){
					 isShownothererrors = false;	
				}else{
					ShowRetryRequest();	
				}
				//Toast.makeText(getActivity(),
					//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
			
			}
			
	    	  Progressbar.setVisibility(View.GONE);
	    	  mGoogleNow.progressiveStop();
	    //	int heightInPx = getResources()
			//	      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
	    	// Style stylealert = new Style.Builder()
			//	 .setBackgroundColor(R.color.Red)
				      
			//	      .setImageResource(R.drawable.exclamation)
			//	      .setGravity(Gravity.CENTER)
				//      .setTextColor(android.R.color.white)
			//	      .setHeight(heightInPx)
			//	      .build();
	    	// Configuration configalert = new Configuration.Builder()
			//			  .setDuration(2000)
						//  .setInAnimation(android.R.anim.slide_in_left)
						 // .setOutAnimation(android.R.anim.slide_out_right)
					//	  .build();	     
	    	//   Crouton alertCrouton=Crouton.makeText(
				//	 getActivity(), 
				//      errorimahappen, 
				//      stylealert);
			// alertCrouton.setConfiguration(configalert);
			// alertCrouton.show();
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
	//	request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		request.setTag(TAG_REQUEST);	
	mVolleyQueue2.add(request);
 //  makePostRequest();
   

}


private void parseHistoryResponserefresh(JSONObject response) {//throws JSONException {
	
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
                	    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
                				arrayOfListhistory);
                        Contact_listview.setEmptyView(emptyText);
                       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
                	    Contact_listview.setAdapter(plusAdapter);
                	    }else{
                	    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
					stopLoadingAnim();
					//Crouton.hide(successCrouton);
					
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
		
	//	Showsuccess();
}
public void GetCount(){

	//super.onCreate();
//Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();



Map<String,String> params = new HashMap<String, String>();

params.put("phone",phonedisplay.getText().toString());


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
			
			 emptyText.setText("no history for this contact be the first to enter");
			 Progressbar.setVisibility(View.GONE);
			  Historymainmethod();
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
	
	   GetCountRetry();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
		//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof ClientError) { 
	} else if( error instanceof ServerError) {
		   
		 errorimahappen=" Server error exit the app try again later  ";
		//Toast.makeText(getActivity(),
			//     "Server error ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof AuthFailureError) {
	} else if( error instanceof ParseError) {
		 errorimahappen="fatal error we are fixing it.... ";
	} else if( error instanceof NoConnectionError) {
		
		   GetCountRetry();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
	//	Toast.makeText(getActivity(),
		//			     "no internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof TimeoutError) {
	
		   GetCountRetry();
		 errorimahappen="Slow internet connection Retrying....  ";
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeText(getActivity(),
			//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
	
	}
			//Toast.makeText(getActivity(),
			  //   dialogmessage, Toast.LENGTH_SHORT).show();
						 mGoogleNow.progressiveStop();
			Progressbar.setVisibility(View.GONE);
			 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
							arrayOfListhistory);
			        Contact_listview.setEmptyView(emptyText);
			       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
				    Contact_listview.setAdapter(plusAdapter);
				    }else{
				    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
//request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
request.setTag(TAG_REQUEST);	
mVolleyQueue2.add(request);
}
public void GetCountRefresh(){
	 startLoadingAnim();
	//super.onCreate();
//Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();



Map<String,String> params = new HashMap<String, String>();

params.put("phone",phonedisplay.getText().toString());


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
			Progressbar.setVisibility(View.GONE);
			 emptyText.setText("no history for this contact be the first to enter");
			 Historymainmethodrefresh(); 
		}else{
			 showrecords.setText("getting "+contactname+" records please wait...");
			//Toast.makeText(getApplicationContext(),
			//		 contactname, Toast.LENGTH_SHORT).show();
			 // result = Activity.RESULT_OK;
			Historymainmethodrefresh();  
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	//   parseHistoryResponserefresh(response);

   	
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
   
   	// pDialog.dismiss();
       // Error handling
   	// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
			// For AuthFailure, you can re login with user credentials.
			// For ClientError, 400 & 401, Errors happening on client side when sending api request.
			// In this case you can check how client is forming the api and debug accordingly.
			// For ServerError 5xx, you can do retry or handle accordingly.
   if( error instanceof NetworkError) {
	

	   GetCountRetry();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
		//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof ClientError) { 
	} else if( error instanceof ServerError) {
		 errorimahappen=" Server error exit the app try again later  ";
		//Toast.makeText(getActivity(),
			//     "Server error ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof AuthFailureError) {
	} else if( error instanceof ParseError) {
		 errorimahappen="fatal error we are fixing it.... ";
	} else if( error instanceof NoConnectionError) {
	
		GetCountRetry();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
	//	Toast.makeText(getActivity(),
		//			     "no internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof TimeoutError) {
		  
		GetCountRetry();
		 errorimahappen="Slow internet connection Retrying....  ";
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeText(getActivity(),
			//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
	
	}
		
			 mGoogleNow.progressiveStop();
			
			Progressbar.setVisibility(View.GONE);
			 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
							arrayOfListhistory);
			        Contact_listview.setEmptyView(emptyText);
			       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
				    Contact_listview.setAdapter(plusAdapter);
				    }else{
				    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
//request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
request.setTag(TAG_REQUEST);	
mVolleyQueue2.add(request);
}
public void GetCountRetry(){

	//super.onCreate();
//Toast.makeText(getApplicationContext(), "The new Service was Created", Toast.LENGTH_SHORT).show();
//Toast.makeText(getApplicationContext(), " Service Started", Toast.LENGTH_LONG).show();



Map<String,String> params = new HashMap<String, String>();

params.put("phone",phonedisplay.getText().toString());


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
			Progressbar.setVisibility(View.GONE);
			 emptyText.setText("no history for this contact be the first to enter");
			 Crouton.hide(successCrouton);
			 stopLoadingAnim();
	    		Crouton.cancelAllCroutons();
			 Historymainmethodrefresh(); 
		}else{
			 showrecords.setText("getting "+contactname+" records please wait...");
			//Toast.makeText(getApplicationContext(),
			//		 contactname, Toast.LENGTH_SHORT).show();
			 // result = Activity.RESULT_OK;
			 Crouton.hide(successCrouton);
			 stopLoadingAnim();
	    		Crouton.cancelAllCroutons();
			Historymainmethodrefresh();  
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	//   parseHistoryResponserefresh(response);

   	
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

   	// pDialog.dismiss();
       // Error handling
   	// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
			// For AuthFailure, you can re login with user credentials.
			// For ClientError, 400 & 401, Errors happening on client side when sending api request.
			// In this case you can check how client is forming the api and debug accordingly.
			// For ServerError 5xx, you can do retry or handle accordingly.
   if( error instanceof NetworkError) {
	  
	   GetCountRefresh();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
		//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof ClientError) { 
	} else if( error instanceof ServerError) {
		 errorimahappen=" Server error exit the app try again later  ";
		//Toast.makeText(getActivity(),
			//     "Server error ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof AuthFailureError) {
	} else if( error instanceof ParseError) {
		 errorimahappen="fatal error we are fixing it.... ";
	} else if( error instanceof NoConnectionError) {
		
		 GetCountRefresh();
		 errorimahappen="No internet connection Retrying...." ;
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeT
	//	Toast.makeText(getActivity(),
		//			     "no internet connection ", Toast.LENGTH_SHORT).show();
	} else if( error instanceof TimeoutError) {
		
		 GetCountRefresh();
		 errorimahappen="Slow internet connection Retrying....  ";
		 if(isShownothererrors){
			 isShownothererrors = false;	
		}else{
			ShowRetryRequest();	
		}
		//Toast.makeText(getActivity(),
			//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
	
	}
		//	Toast.makeText(getActivity(),
			//     dialogmessage, Toast.LENGTH_SHORT).show();
			 mGoogleNow.progressiveStop();
			
			Progressbar.setVisibility(View.GONE);
			 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				    plusAdapter =  new HistoryAdapter(getActivity(),listenertwo,
							arrayOfListhistory);
			        Contact_listview.setEmptyView(emptyText);
			       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
				    Contact_listview.setAdapter(plusAdapter);
				    }else{
				    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(getActivity(),
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
//request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
request.setTag(TAG_REQUEST);	
mVolleyQueue2.add(request);
}
public void SaveHistory(){
	 Map<String,String> params = new HashMap<String, String>();
	  params.put("email",user.get("email"));
	  params.put("username",user.get("uname"));
	  params.put("phone",phonedisplay.getText().toString());
	  params.put("name",namedisplay.getText().toString());
	  params.put("note",savenote.getText().toString());
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/save_history.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    	 loading.setVisibility(View.GONE);
		    	 mGoogleNow.progressiveStop();
		    	 savenote.setText("");
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
						 getActivity(), 
					      "Note saved successfully", 
					      stylealert);
				 alertCrouton.setConfiguration(configalert);
				 alertCrouton.show();   	
				GetCountRefresh();
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
		    		 errorimahappen="No internet connection Retrying...." ;
		    		 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeT
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error exit the app try again later  ";
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					 errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					 errorimahappen="No internet connection Retrying...." ;
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					 errorimahappen="Slow internet connection Retrying....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
		    	 mGoogleNow.progressiveStop();
		    	//mPullToRefreshLayout.setRefreshComplete();
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

public void UpdateHistory(){
	 Map<String,String> params = new HashMap<String, String>();
	 params.put("email",user.get("email"));
	 params.put("note",savenote.getText().toString());
	  params.put("id",idnoma);
	 
	  
		 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
		// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://dash.yt/salespacer/android_api/History/update_history.php", params,
				new Response.Listener<JSONObject>() {
		    @Override
		    public void onResponse(JSONObject response) {
		    	//dialogmessage="Popup created check your web and write a note";
		    	savenote.setText("");
		    	 mGoogleNow.progressiveStop();
		    	 loading.setVisibility(View.GONE);
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
						 getActivity(), 
					      "Note updated successfully", 
					      stylealert);
				 alertCrouton.setConfiguration(configalert);
				 alertCrouton.show();  
				 GetCountRefresh();
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
		    		 errorimahappen="No internet connection Retrying...." ;
		    		 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeT
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error exit the app try again later  ";
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					 errorimahappen="fatal error we are fixing it.... ";
				} else if( error instanceof NoConnectionError) {
					 errorimahappen="No internet connection Retrying...." ;
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeT
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					 errorimahappen="Slow internet connection Retrying....  ";
					 if(isShownothererrors){
						 isShownothererrors = false;	
					}else{
						ShowRetryRequest();	
					}
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
		    	 mGoogleNow.progressiveStop();
					//Intent intent = new Intent(NOTIFICATION);
				   // intent.putExtra(FILEPATH, outputPath);
				  //  intent.putExtra(RESULT, result);
				  //  sendBroadcast(intent);
					
		    }
		});
		//Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
			request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			request.setTag(TAG_REQUEST);	
		mVolleyQueue2.add(request);
	// makePostRequest();
	 
	}
	  public void call() {
	     
	        
		  try {
	        	
	          
				 Intent callIntent = new Intent(Intent.ACTION_CALL);
	            callIntent.setData(Uri.parse("tel:"+phonedisplay.getText().toString()));
	            startActivity(callIntent);
	            final Handler handler = new Handler();
		        handler.postDelayed(new Runnable() {
		            public void run() {
		                // TODO: Your application init goes here.
		            	
		            }
		        }, 3000);
	        } catch (ActivityNotFoundException activityException) {
	            Log.e("telephony-example", "Call failed", activityException);
	        }
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
		
	  private static Intent getIntent(Context context, Class<?> cls) {
		    Intent intent = new Intent(context, cls);
		    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		    return intent;
		}

	  
		    public void Show_Toast(String msg) {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
		    }
		    public void Showalert(){
		    	int heightInPx = getResources()
					      .getDimensionPixelOffset(R.dimen.custom_crouton_height);
		    	 Style stylealert = new Style.Builder()
					 .setBackgroundColor(R.color.Red)
					      
					      .setImageResource(R.drawable.exclamation)
					      .setGravity(Gravity.CENTER)
					      .setTextColor(android.R.color.white)
					      .setHeight(heightInPx)
					      .build();
		    	 Configuration configalert = new Configuration.Builder()
							  .setDuration(2000)
							//  .setInAnimation(android.R.anim.slide_in_left)
							 // .setOutAnimation(android.R.anim.slide_out_right)
							  .build();	     
		    	   Crouton alertCrouton=Crouton.makeText(
						 getActivity(), 
					      "No internet connection", 
					      stylealert);
				 alertCrouton.setConfiguration(configalert);
				 alertCrouton.show();   	
		    }
public void Showsuccess(){
	int heightInPx2 = getResources()
		      .getDimensionPixelOffset(R.dimen.custom_crouton_height2);
 Style 	stylesuccess = new Style.Builder()
		 .setBackgroundColor(R.color.Green)
		      
		      .setImageResource(R.drawable.success_icon)
		      .setGravity(Gravity.CENTER)
		      .setTextColor(android.R.color.white)
		      .setHeight(heightInPx2)
		      .build();
 Configuration  configasuccess = new Configuration.Builder()
				  .setDuration(2000)
				// .setInAnimation(android.R.anim.slide_in_left)
				// .setOutAnimation(android.R.anim.slide_out_right)
				  .build();	     
		  Crouton  successCrouton=Crouton.makeText(
	 getActivity(), 
		      "History of contact displayed", 
		      stylesuccess);
		  successCrouton.setConfiguration(configasuccess);
		  isShown = true;
			 successCrouton.show();
			
	 previouscall.setEnabled(true);
	 nextcall.setEnabled(true);
	 nextcall.setBackgroundResource(R.drawable.buttonselect);
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
	 getActivity(), 
		      errorimahappen, 
		      styleretry);
		  successCrouton.setConfiguration(configaretry);
		  isShownretry = true;
		  successCrouton.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
			    Crouton.hide(successCrouton);
			  }
			});

			 successCrouton.show();
			
	// previouscall.setEnabled(true);
	 //nextcall.setEnabled(true);
	 //nextcall.setBackgroundResource(R.drawable.buttonselect);
}

public void activity(){
	
}
// Create reciever object
private BroadcastReceiver the_receiver = new OutgoingCall();
         
// Set When broadcast event will fire.
private IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
            
         

         // Reciever class which extends BroadcastReceiver
         public class OutgoingCall extends BroadcastReceiver {
     
                public void onReceive(Context context, Intent intent) {
                	  Log.d("APP", "ACTION:" + intent.getAction());

                	    if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
                	        final String originalNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                	        Log.d("APP", "outgoing,ringing:" + originalNumber);
                	     //   Toast.makeText(getActivity(),
       						//	 originalNumber, Toast.LENGTH_LONG).show();
                	    String  input= phonedisplay.getText().toString();
                	    input = input.trim();
                	        if (PhoneNumberUtils.compare(context,originalNumber,input)) {
        		        	    //they are the same do whatever you want!
                	        	  Intent intentnew=new Intent(getActivity(),FirstPopupService.class);
                		            int visible = myPrefsnow.getInt("num", 1);
                		            String yourdata = db.getDetailsreverse(Integer.toString(visible));
                		            if(visible>=1){
                		      	  if(yourdata!=null)
                		      	  {
                		      	i=Integer.parseInt(yourdata);
                		      	 intminus=i;
                		      		
                		      	//SharedPreferences.Editor editor = myPrefsnow.edit();
                		      	//editor.putInt("num", intminus);
                		      	//editor.commit(); 
                		      	//Toast.makeText(MainControl.this,
                		      	 //   ""+intminus, Toast.LENGTH_LONG).show();
                		      	  }
                		            }else{
                		            	intminus=1;
                		            	SharedPreferences.Editor editor = myPrefsnow.edit();
                		        		editor.putInt("num", 1);
                		        		editor.commit(); 
                		            }
                		           // Bundle extras = intentnew.getExtras();
                		            intentnew.putExtra("ID",  Integer.toString(intminus));
                		            intentnew.putExtra("PHONE", phonedisplay.getText().toString());
                		            intentnew.putExtra("NAME", namedisplay.getText().toString());
                					getActivity().startService(intentnew);	  
        		        	try {
        	                    //This will simulate pressing the Home button
        	                    //Thus Minimizing the In Call Screen
        		        		
        		        		final Handler handler = new Handler();
    					        handler.postDelayed(new Runnable() {
    					           public void run() {
        					        	  // Toast.makeText(getActivity(),
        			     					//		 "inarun tena", Toast.LENGTH_LONG).show();
        					                // TODO: Your application init goes here.
        					        	   Intent startMain = new Intent(Intent.ACTION_MAIN);
        					        		startMain.addCategory(Intent.CATEGORY_HOME);
        					        		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        					        		getActivity().startActivity(startMain);
        					        		
        					        		
        					        		Intent intentnoma = new Intent(getActivity(), TakeNote.class);
        					        		  //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        					        		    
        					        		    intentnoma.putExtra("PHONE",phonedisplay.getText().toString());
        					        		    // Pass all data country
        					        		    intentnoma.putExtra("NAME", namedisplay.getText().toString() );
        					        		    // Pass all data population
        					        		  //  intent.putExtra("email",(objBean.getGender()));
        					        		      startActivity(intentnoma); 
        					       
    					           }
    					        }, 2000);
        		        	} catch (Exception e) {
        	                    Log.v("", "e: " + e.getMessage());
        	                    e.printStackTrace();
        	                }
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
                     // Phone call state change then this method will automaticaly called
                	    }
                }
          }


		    @Override
		    public void onResume() {
		    	// TODO Auto-generated method stub
				super.onResume();
				 
		    	
			 getActivity(). registerReceiver(receiver, new IntentFilter(GcmIntentService.NOTIFICATION));
			 getActivity(). registerReceiver(receivertwo, new IntentFilter(CheckBusyService.NOTIFICATION));
			 getActivity(). registerReceiver(receiverthree, new IntentFilter(CheckNoteidService.NOTIFICATION));
			// Register new broadcast receiver
	         getActivity().registerReceiver(the_receiver, filter);
			 //tManager.listen(listener, ListenToPhoneState.LISTEN_CALL_STATE);
			if(shouldExecuteOnResume){
				GetCountRefresh();
				// Historymainmethodrefresh();
				// Intent myIntent = new Intent(getActivity(), CheckMessoVolleyService.class);
				// pendingIntent = PendingIntent.getService(getActivity(), 0, myIntent, 0);
			     //   Intent intent=new Intent(MainControl.this,CheckMessoVolleyService.class);
					//MainControl.this.startService(intent);
					//  AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
				//	Calendar calendar = Calendar.getInstance();
			     //   calendar.setTimeInMillis(System.currentTimeMillis());
			     //   calendar.add(Calendar.SECOND, 10);
					//alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
			     //   alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),3000, pendingIntent);
				//dbHandler.removeAll();
			//makeSampleHttpRequest2();
			//int kujua = myPrefsnow.getInt("num", 0);
			//if(kujua==0){
				
			//	call.setVisibility(View.VISIBLE);
			//	
			//}else{
				// getActivity(). registerReceiver(receiver, new IntentFilter(GcmIntentService.NOTIFICATION));
			//	call.setVisibility(View.GONE);
			//}
			//checkPlayServices(); 
			//Set_Referash_Data();
			 } else{
	     		   shouldExecuteOnResume = true;
	     		  } 
			
		    }
		  
			public void onDestroy() {
				super.onDestroy();
				tManager.listen(listener, ListenToPhoneState.LISTEN_NONE);//Removing the Listner(PhoneStateMonitor) with the TelephonyManager using PhoneStateListener.LISTEN_NONE constant	
				//int intminus=i-1;
				// getActivity().unregisterReceiver(the_receiver);
				//SharedPreferences.Editor editor = myPrefsnow.edit();
				//editor.putInt("num", id);
				//editor.commit(); 
			//	Crouton.cancelAllCroutons();
				Crouton.cancelAllCroutons();
			
			//dbHandler.delete();
			}
			public void onPause() {
				// TODO Auto-generated method stub
				
				super.onPause();
				
			}
			public void onStop() {
				super.onStop();
				Crouton.cancelAllCroutons();
				getActivity().unregisterReceiver(receiver);
				getActivity().unregisterReceiver(receivertwo);
				getActivity().unregisterReceiver(receiverthree);
				getActivity().unregisterReceiver(the_receiver);
				mVolleyQueue2.cancelAll(TAG_REQUEST);
				//tManager.listen(listener, ListenToPhoneState.LISTEN_NONE);//Removing the Listner(PhoneStateMonitor) with the TelephonyManager using PhoneStateListener.LISTEN_NONE constant	
			//	tManager.listen(listener, ListenToPhoneState.LISTEN_NONE);//Removing the Listner(PhoneStateMonitor) with the TelephonyManager using PhoneStateListener.LISTEN_NONE constant	
			}
		   
			
		    @Override
		    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		        // TODO Auto-generated method stub
		        super.onCreateOptionsMenu(menu, inflater);
		        inflater.inflate(R.menu.dailing_main, menu);
		        
		        Log.i("menu", "creating menu");
		        mRefreshMenu = menu.findItem(R.id.action_refresh);
		        ImageView iv = (ImageView) mRefreshMenu.getActionView();
		        LayoutInflater inflatertwo = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        iv = (ImageView) inflatertwo.inflate(R.layout.actionbar_refresh, null);
		        iv.setId(R.id.action_refresh);
		        iv.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		            	mVolleyQueue2.cancelAll(TAG_REQUEST);
		            	
		              //  mBase.removeAllViews();
		                stopLoadingAnim();
		                startLoadingAnim();
		                mGoogleNow.progressiveStart();
		                Dailingmainmethod();
		                Historymainmethodrefresh();
		               // cAdapter.notifyDataSetChanged();
		              //  onResume();
		            //    Set_Referash_Data();
		              //  refreshDatas();
		            }
		        });
		        mRefreshMenu.setActionView(iv);
		        startLoadingAnim();
		       
		      //  inflater.inflate(R.menu.firstfrag, menu);
		    }
		  
}
