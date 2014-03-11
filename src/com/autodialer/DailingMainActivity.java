package com.autodialer;





import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;















import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuInflater;
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
import com.autodialer.TakeNote.ListenToPhoneState;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;









import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DailingMainActivity extends SherlockFragment {
	TextView login;
	boolean shouldExecuteOnResume;
	// Button add_btn;
	    ListView Contact_listview;
	    ArrayList<Contact> contact_data = new ArrayList<Contact>();
	    Contact_Adapter cAdapter;
	    DatabaseHandler db;
	    String Toast_msg;
	    SharedPreferences myPrefs;
	    View promptsView;
	    PendingIntent pendingIntent;
	    DatabaseHandler dbHandler;
//Button getcontacts;
Button call;
//Button  pickcontact;
Button nextcall;
ListView listtest;
ListenToPhoneState listener;
AlertDialog.Builder alertDialogBuilder;
AlertDialog alertDialog;
 EditText userInput;
 EditText edittextusername;
 EditText edittextemail;
 TextView yourTextView;
 LinearLayout Progressbar;
//Context ctx;
final Context  ctx = getActivity();
SharedPreferences myPrefsnow;
private SpeedScrollListener listenertwo;
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
	    
	    static final String TAG = "GCMDemo";
	    GoogleCloudMessaging gcm;
	    String msg;
	    Context context;
	    String regid;
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
	//shouldExecuteOnResume = false;
	   setRetainInstance(true);
	   setHasOptionsMenu(true);
	//setHasOptionsMenu(true);
   
	   shouldExecuteOnResume = false;
    View mLinearLayout = inflater.inflate(R.layout.activity_dailing_main, container, false);
    dbtwo= new DatabaseHandlerUser(getActivity());
    dbHandler=new DatabaseHandler(getActivity());
    user = new HashMap<String, String>();
     user = dbtwo.getUserDetails();
    login = (TextView) mLinearLayout.findViewById(R.id.textwelcome);
    login.setText("Welcome  "+user.get("uname"));
    pd = new ProgressDialog(getActivity());
    pd.setMessage("Registering your device to our servers just a moment");
    mVolleyQueue = Volley.newRequestQueue(getActivity()); 
   // checkPlayServices();
    context = getActivity().getApplicationContext();
	
	

    listenertwo = new SpeedScrollListener();
	 // listnew.setOnScrollListener(listener);
	//	setContentView(R.layout.activity_dailing_main);
		  yourTextView = (TextView) mLinearLayout.findViewById(R.id.textViewtest);
		  call=(Button)mLinearLayout.findViewById(R.id.buttoncall);
		  Progressbar = (LinearLayout)mLinearLayout.findViewById(R.id.linlaHeaderProgress);
		 call.setTextColor(Color.WHITE);
	   		call.setBackgroundResource(R.drawable.button_gradient);
		  nextcall=(Button)mLinearLayout.findViewById(R.id.buttonnextcall);
		  nextcall.setTextColor(Color.WHITE);
	   		nextcall.setBackgroundResource(R.drawable.button_gradient);
		  promptsView  = mLinearLayout.inflate(getActivity(), R.layout.prompt, null);
		  Progressbar.setVisibility(View.VISIBLE);
		  nextcall.setVisibility(View.GONE);
		  call.setVisibility(View.GONE);
		  myPrefsnow = PreferenceManager.getDefaultSharedPreferences(getActivity());
	//	 myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		
		int kujua = myPrefsnow.getInt("num", 1);
		if(kujua==1){
		 //sdb.open();
		// listtest=(ListView)findViewById(R.id.listkalitest);
			call.setVisibility(View.VISIBLE);
		}else{
			nextcall.setVisibility(View.GONE);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		    builder.setTitle("Hey Kenneth You did not finish your calls the last time out");
		    builder.setMessage("Do you want to continue where you left off?Or start all over again?");

		    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

		        public void onClick(DialogInterface dialog, int which) {
		            // Do nothing but close the dialog
		        	 nextcall.setVisibility(View.VISIBLE);
		            dialog.dismiss();
		        }

		    });

		    builder.setNegativeButton("Start all Over", new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit(); 
					 nextcall.setVisibility(View.GONE);
					 call.setVisibility(View.VISIBLE);
		            // Do nothing
		            dialog.dismiss();
		        }
		    });

		    AlertDialog alert = builder.create();
		    alert.show();	
		}
		
		 try {
			    Contact_listview = (ListView) mLinearLayout. findViewById(R.id.list);
			    Contact_listview.setItemsCanFocus(false);
			 ///   add_btn = (Button) mLinearLayout.findViewById(R.id.add_btn);
			   
			    Set_Referash_Data();

			} catch (Exception e) {
			    // TODO: handle exception
			    Log.e("some error", "" + e);
			}
			//add_btn.setOnClickListener(new View.OnClickListener() {

			//    @Override
			 //   public void onClick(View v) {
				// TODO Auto-generated method stub
			//	Intent add_user = new Intent(getActivity(),
			//		Add_Update_User.class);
			//	add_user.putExtra("called", "add");
			//	add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			//		| Intent.FLAG_ACTIVITY_NEW_TASK);
			//	startActivity(add_user);
				//getActivity().finish();
			 //   }
			//});

			 
		 // this opens the activity. note the  Intent.ACTION_GET_CONTENT
	    // and the intent.setType
	   // pickcontact=(Button)mLinearLayout.findViewById(R.id.buttonchoosecontacts);
	  //  pickcontact.setTextColor(Color.WHITE);
   		//pickcontact.setBackgroundResource(R.drawable.button_gradient);
	  //  pickcontact.setOnClickListener( new OnClickListener() {
	      //  @Override
	      //  public void onClick(View v) {
	            // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
	         //   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	            // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
	          //  intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
	          //  startActivityForResult(intent, 1);                
	      //  }
	   // });
	    nextcall.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	//callNext();
	        	 int visible = myPrefsnow.getInt("num", 1);
	     		// switch (visible)
	     	      if(visible==1)  {
	     	    	  call();
	     		// case 1:
	     			 //addnote.setVisibility(View.VISIBLE);
	     	        	//break;
	     	      }else if(visible==2){
	     	      //  case 2:
	     	        	callNext();
	     	        	//break;
	     	}else if(visible==3){ 
	     		callNext2();
	           //  case 3:
	             	// addnote3.setVisibility(View.VISIBLE);
	             	// addnote.setVisibility(View.GONE);
	     	    // break;
	     	}else if(visible==4){
	     		callNext3();
	          //  case 4:
	         	 //  addnote4.setVisibility(View.VISIBLE);
	         	  // addnote.setVisibility(View.GONE);
	     	   // break;
	     	}else if(visible==4){
	     		callNext4();
	          //  case 5:
	         	  // addnote5.setVisibility(View.VISIBLE);
	         	  // addnote.setVisibility(View.GONE);
	     	      //  break;
	     	}else if(visible==6){
	     		callNext5();
	           // case 6:
	         	  // addnote6.setVisibility(View.VISIBLE);
	         	  // addnote.setVisibility(View.GONE);
	     		 //   break;
	     	}else if(visible==7){
	     		callNext6();
	           // case 7:
	         	 //  addnote7.setVisibility(View.VISIBLE);
	         	  // addnote.setVisibility(View.GONE);
	     		    //    break;
	     	}else if(visible==8){
	     		callNext7();
	           // case 8:
	         	  // addnote8.setVisibility(View.VISIBLE);
	         	  // addnote.setVisibility(View.GONE);
	     		    //    break;
	     	}else if(visible==9){
	     		callNext8();
	          //  case 9:
	         	 //  addnote9.setVisibility(View.VISIBLE);
	         	 //  addnote.setVisibility(View.GONE);
	     		     //   break;
	     	}else if(visible==10){
	     		callNext9();
	     		
	          //  case 10:
	         	//   addnote10.setVisibility(View.VISIBLE);
	         	 //  addnote.setVisibility(View.GONE);
	     		       // break;
	     }
	        	// Toast.makeText(ctx,  "call state :" + , Toast.LENGTH_LONG).show(); 
	        }
	    });
	    call.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        
	        	call();	
	        	// Toast.makeText(ctx,  "call state :" + , Toast.LENGTH_LONG).show(); 
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
	    mVolleyQueue2 = Volley.newRequestQueue(getActivity()); 
	   // Dailingmainmethod();
	   // new NetCheck().execute();	
	    Dailingmainmethod();
	   // Intent intent=new Intent(getActivity(),CheckMessageService.class);
		//		getActivity().startService(intent);
	 final DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
	// get prompts.xml view
			//LayoutInflater li = LayoutInflater.from(ctx);
	// footerView 
			

			 alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			userInput = (EditText) promptsView
					.findViewById(R.id.DialogPhoneUserInput);
			edittextusername = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);
			edittextemail = (EditText) promptsView
					.findViewById(R.id.DialogEmail);
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Save",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    //	String name = myPrefs.getString("firstname", "wrong");
				    //	String phone = myPrefs.getString("number", "wrong");
						// String urlcomment1 = comment1;number
				    	  dbHandler.Add_Contact(new Contact(edittextusername.getText().toString(),
				    			  userInput.getText().toString(), edittextemail.getText().toString()));
				  		    Toast_msg = "Contact saved successfully";
				  		  Set_Referash_Data();
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			alertDialog = alertDialogBuilder.create();

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
				Progressbar.setVisibility(View.GONE);
	    }
	});
	mVolleyQueue2.add(request);
 //  makePostRequest();
   

}
public void makeSampleHttpRequest2() {
	
	//dbHandler.removeAll();
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
				//dbHandler.delete();
				//dbHandler.removeAll();
				
				
				//plusAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
				showToast("JSON parse error");
			}
			//Toast.makeText(getActivity(),
				//     "doing the dirty work", Toast.LENGTH_SHORT).show();
			 
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
			Set_Referash_Data();
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
				
				//dbHandler = new DatabaseHandler(getActivity());
				
				dbHandler.Add_Contact(new Contact(name+""+secondname,
		    			  phone, email));
				 Progressbar.setVisibility(View.GONE);
					Set_Referash_Data();
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
	  public void call() {
	     
	        
	        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
	        	 if(contact_array_from_db.size() > 0){
	        		   try {
	        				Intent intentnew=new Intent(getActivity(),FirstPopupService.class);
	        				getActivity().startService(intentnew);	
	        	 int text = contact_array_from_db.get(0).getID();
	        	 String mobile = contact_array_from_db.get(0).getPhoneNumber();
	            Intent callIntent = new Intent(Intent.ACTION_CALL);
	            callIntent.setData(Uri.parse("tel:"+mobile));
	            startActivity(callIntent);

	            TelephonyManager tManager = (TelephonyManager) 
	              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
	            listener = new ListenToPhoneState();
	            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	        } catch (ActivityNotFoundException activityException) {
	            Log.e("telephony-example", "Call failed", activityException);
	        }
	        	 } else{
			    Toast.makeText(getActivity(), "No Numbers to call/No numbers from server",  Toast.LENGTH_LONG).show(); 
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				//	finish();
		 }
	        	 }
	  
	  public void callNext() {
			
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 1){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	Intent intentnew=new Intent(getActivity(),SecondPopupService.class);
    				getActivity().startService(intentnew);	
		   		 String mobile = contact_array_from_db.get(1).getPhoneNumber();
		        	
		        //	 int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				    Toast.makeText(getActivity(), "All calls finished",  Toast.LENGTH_LONG).show(); 
					 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 1);
						editor.commit();  
					//	finish();
			 }
		    }
		 private void callNext2() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 2){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	Intent intentnew=new Intent(getActivity(),ThirdPopupService.class);
    				getActivity().startService(intentnew);	
		   		 String mobile = contact_array_from_db.get(2).getPhoneNumber();
		        	
		        //	 int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
		 
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				   // finish();
			 }
		    }
		 private void callNext3() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 3){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	Intent intentnew=new Intent(getActivity(),FourthPopupService.class);
    				getActivity().startService(intentnew);	
		   		 String mobile = contact_array_from_db.get(3).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
		 
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
				  //  finish();
			 }
		    }
		 private void callNext4() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 4){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(4).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				   // finish();
			 }
		    }
		 private void callNext5() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 5){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(5).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
				  //  finish();
			 }
		    }
		 private void callNext6() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 6){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(6).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				  //  finish();
			 }
		    }
		 private void callNext7() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 7){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(7).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				 //   finish();
			 }
		    }
		 private void callNext8() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 8){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(8).getPhoneNumber();
		        	
		        //	 int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				  //  finish();
			 }
		    }
		 private void callNext9() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 9){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(9).getPhoneNumber();
		        	
		        //	 int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
				  //  finish();
			 }
		    }
		 private void callNext10() {
				
			 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
			 if(contact_array_from_db.size() > 10){
			// int identify = contact_array_from_db.get(6).getID();
			// if( identify ==2){
		        try {
		        	
		   		 String mobile = contact_array_from_db.get(10).getPhoneNumber();
		        	
		        	// int phone2 = contact_array_from_db.get(2).getID();
		        //	 if (text == 2){
		        		
		 	            Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	            callIntent.setData(Uri.parse("tel:"+mobile));
		 	            startActivity(callIntent); 
		        		 
		        	// }  if (phone2 ==3){
		        		// String mobile = contact_array_from_db.get(2).getPhoneNumber();
			 	       //     Intent callIntent = new Intent(Intent.ACTION_CALL);
			 	        //    callIntent.setData(Uri.parse("tel:"+mobile));
			 	        //    startActivity(callIntent); 
		        	// }//
		        	

		            TelephonyManager tManager = (TelephonyManager) 
		              getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		            listener = new ListenToPhoneState();
		            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		        } catch (ActivityNotFoundException activityException) {
		            Log.e("telephony-example", "Call failed", activityException);
		        }
			// }else{
			//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
			// }
			 }else{
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
				    Toast.makeText(getActivity(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
				   // finish();
			 }
		    }
	  public class ListenToPhoneState extends PhoneStateListener {
			private boolean isPhoneCalling = false;

		  public void onCallStateChanged(int state, String incomingNumber)
		    {
		        super.onCallStateChanged(state, incomingNumber);
		        switch (state)
		        {
		        case TelephonyManager.CALL_STATE_IDLE:
		        	if (isPhoneCalling) {
		        		// int visible = myPrefsnow.getInt("num", 1);
		        		
							// if(visible==1)  {
									// case 1:
								 
								        	//break;
								  //    }else if(visible==2){
								      //  case 2:
								        
								        	//break;
								//}else if(visible==3){ 
							      //  case 3:
							        	
								    // break;
							//	}else if(visible==4){
							     //  case 4:
							    	  
								   // break;
							//	}else if(visible==5){
							     //  case 5:
							    	  
								      //  break;
							//	}else if(visible==6){
							      // case 6:
							    	  
							//	}else if(visible==7){
							      // case 7:
							    	  
							//	}else if(visible==8){
							      // case 8:
							    	
								//}else if(visible==9){
							     //  
								//}else if(visible==10){
							     // 
							//}
		            //CALL_STATE_IDLE;
		          //  Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",  Toast.LENGTH_LONG).show();
		            Intent intent = new Intent(getActivity(), TakeNote.class);
		           // intent.putExtra("contact", (objBean.getName()));
	                // Pass all data country
	              //  intent.putExtra("phone", (objBean.getBirthdate()));
	                // Pass all data population
	              //  intent.putExtra("email",(objBean.getGender()));
		               startActivity(intent);
		        	isPhoneCalling = false;
					}
		            break;
		        case TelephonyManager.CALL_STATE_OFFHOOK:
		            //CALL_STATE_OFFHOOK;
		          //  Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",  Toast.LENGTH_LONG).show();
		            isPhoneCalling = true;
		            break;
		        case TelephonyManager.CALL_STATE_RINGING:
		            //CALL_STATE_RINGING
		          //  Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",  Toast.LENGTH_LONG).show();
		            break;
		        default:
		            break;
		        }
		    }

		  }
		
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (data != null) {
	        Uri uri = data.getData();

	        if (uri != null) {
	            Cursor c = null;
	            try {
	                c = getActivity().getContentResolver().query(uri, new String[]{ 
	                            ContactsContract.CommonDataKinds.Phone.NUMBER,  
	                            ContactsContract.CommonDataKinds.Phone.TYPE,
	                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
	                            ContactsContract.CommonDataKinds.Email.DATA,
	                            ContactsContract.CommonDataKinds.Email.TYPE },
	                        null, null, null);

	                if (c != null && c.moveToFirst()) {
	                    String number = c.getString(0);
	                    String name = c.getString(2);
	                //    String email = c.getString(4);
	                    String email="not yet implimented enter manually";
	                    int type = c.getInt(1);
	                   // retriveEmail();
	                    
	                    showSelectedNumber(type, number,name,email);
	                }
	            } finally {
	                if (c != null) {
	                    c.close();
	                }
	            }
	        }
	    }
	}

	public void showSelectedNumber(int type, String number ,String name,String email ) {
	    Toast.makeText(getActivity(),name+ type + ": " + number+email, Toast.LENGTH_LONG).show();     
	 // show it
	 		alertDialog.show();
	 		userInput.setText(number);
	 		edittextusername.setText(name);
	 		edittextemail.setText(email);
	 	//	 SharedPreferences.Editor editor = myPrefs.edit();
			//	editor.putString("firstname", name);
			//	editor.putString("number", number);
			//	editor.commit();   
	}
	private void RegisterBackground(){
	
       // return msg;	
	}
	
	
	 public void Set_Referash_Data() {
			contact_data.clear();
			db = new DatabaseHandler(getActivity());
			ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

			for (int i = 0; i < contact_array_from_db.size(); i++) {

			    int tidno = contact_array_from_db.get(i).getID();
			    String name = contact_array_from_db.get(i).getName();
			    String mobile = contact_array_from_db.get(i).getPhoneNumber();
			    String email = contact_array_from_db.get(i).getEmail();
			    Contact cnt = new Contact();
			    cnt.setID(tidno);
			    cnt.setName(name);
			    cnt.setEmail(email);
			    cnt.setPhoneNumber(mobile);
			  //  int text = contact_array_from_db.get(1).getID();
			   // String noma=text.toString();
			//    yourTextView.setText(""+text);
			    contact_data.add(cnt);
			}
			db.close();
			cAdapter = new Contact_Adapter(getActivity(), R.layout.listview_row,
				contact_data);
			Contact_listview.setAdapter(cAdapter);
			cAdapter.notifyDataSetChanged();
		    }

		    public void Show_Toast(String msg) {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
		    }

		    @Override
		    public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			if(shouldExecuteOnResume){
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
			int kujua = myPrefsnow.getInt("num", 1);
			if(kujua==1){
				
				call.setVisibility(View.VISIBLE);
				nextcall.setVisibility(View.GONE);
			}else{
				nextcall.setVisibility(View.VISIBLE);
				call.setVisibility(View.GONE);
			}
			//checkPlayServices(); 
			Set_Referash_Data();
			 } else{
	     		   shouldExecuteOnResume = true;
	     		  } 

		    }
		  
			public void onDestroy() {
				super.onDestroy();
			//dbHandler.delete();
			}
			
			public void onStop() {
				super.onStop();
				//dbHandler.removeAll();
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
		    public class Contact_Adapter extends ArrayAdapter<Contact> {
			Activity activity;
			int layoutResourceId;
			Contact user;
			ArrayList<Contact> data = new ArrayList<Contact>();

			public Contact_Adapter(Activity act, int layoutResourceId,
				ArrayList<Contact> data) {
			    super(act, layoutResourceId, data);
			    this.layoutResourceId = layoutResourceId;
			    this.activity = act;
			    this.data = data;
			    notifyDataSetChanged();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
			    View row = convertView;
			    UserHolder holder = null;

			    if (row == null) {
				LayoutInflater inflater = LayoutInflater.from(activity);

				row = inflater.inflate(layoutResourceId, parent, false);
				holder = new UserHolder();
				holder.name = (TextView) row.findViewById(R.id.user_name_txt);
				holder.email = (TextView) row.findViewById(R.id.user_email_txt);
				holder.number = (TextView) row.findViewById(R.id.user_mob_txt);
				holder.edit = (Button) row.findViewById(R.id.btn_update);
				holder.delete = (Button) row.findViewById(R.id.btn_delete);
				row.setTag(holder);
			    } else {
				holder = (UserHolder) row.getTag();
			    }
			    user = data.get(position);
			    holder.edit.setTag(user.getID());
			    holder.delete.setTag(user.getID());
			    holder.name.setText(user.getName());
			    holder.email.setText(user.getEmail());
			    holder.number.setText(user.getPhoneNumber());

			    holder.edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    Log.i("Edit Button Clicked", "**********");

				    Intent update_user = new Intent(activity,
					    Add_Update_User.class);
				    update_user.putExtra("called", "update");
				    update_user.putExtra("USER_ID", v.getTag().toString());
				    activity.startActivity(update_user);

				}
			    });
			    holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
				    // TODO Auto-generated method stub

				    // show a message while loader is loading

				    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				    adb.setTitle("Delete?");
				    adb.setMessage("Are you sure you want to delete ");
				    final int user_id = Integer.parseInt(v.getTag().toString());
				    adb.setNegativeButton("Cancel", null);
				    adb.setPositiveButton("Ok",
					    new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
							int which) {
						    // MyDataObject.remove(positionToRemove);
						    DatabaseHandler dBHandler = new DatabaseHandler(
							    activity.getApplicationContext());
						    dBHandler.Delete_Contact(user_id);
						    DailingMainActivity.this.onResume();

						}
					    });
				    adb.show();
				}

			    });
			    return row;

			}

			class UserHolder {
			    TextView name;
			    TextView email;
			    TextView number;
			    Button edit;
			    Button delete;
			}

		    }
		   
			
		

	//@Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
     //   super.onCreateOptionsMenu(menu, inflater);
     //   inflater.inflate(R.menu.dailing_main, menu);
      //  Log.i("menu", "creating menu");
		//getMenuInflater().inflate(R.menu.dailing_main, menu);
		//return true;
//	}

}
