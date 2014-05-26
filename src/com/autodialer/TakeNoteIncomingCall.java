/**
 * 
 */
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

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author User
 *
 */
public class TakeNoteIncomingCall extends Activity {
	 SharedPreferences myPrefsnow;
		EditText savenote;
		Button addnote;
		Button exit;
		 String id;
		 String phone;
		 String name;
		 TextView contact;
		 String dialogmessage;
		 String  errorimahappen;
		 ListView Contact_listview;
		 TextView showrecords;
		  private SmoothProgressBar mGoogleNow;
		// TextView calling;
		 
		
		 private RequestQueue mVolleyQueue2;
		 private SpeedScrollListener listenertwo;
		 private HistoryAdapter plusAdapter;
		 private HistoryAdapterLowerDevices lowerdevicesAdapter;
		 private final String TAG_REQUEST = "MY_TAG";
		 JsonObjectRequest jsonObjRequesttwo;
		 TextView emptyText;
		 ProgressBar loading;
		 int i;
		 int  intminus;
		 private HomeKeyLocker mHomeKeyLocker;
		 private RequestQueue mVolleyQueue;
		 LinearLayout Progressbar;
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
		//Button addnote;
			  DatabaseHandlerUser db;
DatabaseHandler dbreverse;
		 HashMap<String,String> user;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.takenoteincomingcall);
		
		 Intent intent = getIntent();
		 
		  Bundle bundle = intent.getExtras();
		  phone = bundle.getString("PHONE");
		  name = bundle.getString("NAME");
		 final DatabaseHandlerHistory dbHandler = new DatabaseHandlerHistory(this);
		 myPrefsnow = PreferenceManager.getDefaultSharedPreferences(this);
		 savenote = (EditText) findViewById(R.id.etEdit);
		 addnote=(Button)findViewById(R.id.btnadd);
		 exit=(Button)findViewById(R.id.btnexit);
	//	 calling= (TextView) findViewById(R.id.textViewcalling);
		  mGoogleNow = (SmoothProgressBar) findViewById(R.id.google_now);
		 contact=(TextView)findViewById(R.id.textViewcontact);
		 loading=(ProgressBar)findViewById(R.id.progress);
		 Contact_listview = (ListView)  findViewById(R.id.listcontacts);
 		 emptyText = (TextView) findViewById(R.id.emptyResults);
 		  Progressbar = (LinearLayout)findViewById(R.id.linlaHeaderProgress);
		  showrecords=(TextView)findViewById(R.id.loadingtext);
 		  listenertwo = new SpeedScrollListener();
 		 arrayOfListhistory = new ArrayList<HistoryParse>();
		 mVolleyQueue = Volley.newRequestQueue(getApplicationContext()); 
		 mVolleyQueue2 = Volley.newRequestQueue(getApplicationContext()); 
		db = new DatabaseHandlerUser(getApplicationContext());
		dbreverse = new DatabaseHandler(getApplicationContext());
		  this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		 user = new HashMap<String, String>();
		  user = db.getUserDetails();
		
		  // home key is locked since then
		 
		   Progressbar.setVisibility(View.VISIBLE);
		   mGoogleNow.progressiveStart();
		contact.setText("Name:"+name+"  Phone:"+phone);
			//db = new DatabaseHandler(this);
		//calling.setText("calling "+name+".......");
		  Intent intentnew=new Intent(TakeNoteIncomingCall.this,FirstPopupService.class);
          int visible = myPrefsnow.getInt("num", 1);
          String yourdata = dbreverse.getDetailsreverse(Integer.toString(visible));
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
          intentnew.putExtra("PHONE", phone);
          intentnew.putExtra("NAME", name);
			TakeNoteIncomingCall.this.startService(intentnew);	  
	     
	      
		 addnote.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	if(savenote.getText().toString().length() > 0) {
		        	     // editText is not empty
		        	 

		        	 Intent intentnew=new Intent(TakeNoteIncomingCall.this,CheckNoteidService.class);
		        	 TakeNoteIncomingCall.this.startService(intentnew);	 
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
		 exit.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 
   		        TakeNoteIncomingCall.this.finish();
   		        Intent openMainActivity= new Intent(TakeNoteIncomingCall.this, MainControl.class);
   		      //  openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
   		        startActivity(openMainActivity);
		        }
		    });
		        
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
	CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/salespacer/android_api/History/get_count.php", params,
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
					dialogmessage="Your phone doesnt have internet connection ";
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					//Toast.makeText(getApplicationContext(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
					dialogmessage="server error we are working on it ";
					
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
					dialogmessage="parse error we are working on it ";
				} else if( error instanceof NoConnectionError) {
					//Toast.makeText(getApplicationContext(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
					dialogmessage="Your phone doesnt have internet connection";
				} else if( error instanceof TimeoutError) {
					//Toast.makeText(getApplicationContext(),
						 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
					dialogmessage="Your phone doesnt have internet connection";
				}
				Toast.makeText(TakeNoteIncomingCall.this,
				     dialogmessage, Toast.LENGTH_SHORT).show();
				Progressbar.setVisibility(View.GONE);
				 mGoogleNow.progressiveStop();
				 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					    plusAdapter =  new HistoryAdapter(TakeNoteIncomingCall.this,listenertwo,
								arrayOfListhistory);
				        Contact_listview.setEmptyView(emptyText);
				       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
					    Contact_listview.setAdapter(plusAdapter);
					    }else{
					    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNoteIncomingCall.this,
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
		CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/salespacer/android_api/History/get_history.php", params,
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
		    		 errorimahappen=" Check your internet connection ";
					//	     " Check your internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof ClientError) { 
				} else if( error instanceof ServerError) {
					 errorimahappen=" Server error  ";
					//Toast.makeText(getActivity(),
						//     "Server error ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof AuthFailureError) {
				} else if( error instanceof ParseError) {
				} else if( error instanceof NoConnectionError) {
					 errorimahappen=" no internet connection  ";
				//	Toast.makeText(getActivity(),
					//			     "no internet connection ", Toast.LENGTH_SHORT).show();
				} else if( error instanceof TimeoutError) {
					 errorimahappen=" Check your internet connection ";
					//Toast.makeText(getActivity(),
						//      "connection is tooo slow ", Toast.LENGTH_SHORT).show();
				
				}
		    	 arrayOfListhistory.clear();
		    	 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					    plusAdapter =  new HistoryAdapter(TakeNoteIncomingCall.this,listenertwo,
								arrayOfListhistory);
				        Contact_listview.setEmptyView(emptyText);
				       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
					    Contact_listview.setAdapter(plusAdapter);
					    }else{
					    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNoteIncomingCall.this,
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
	     	   Progressbar.setVisibility(View.GONE);
	     		 mGoogleNow.progressiveStop();
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
						    plusAdapter =  new HistoryAdapter(TakeNoteIncomingCall.this,listenertwo,
									arrayOfListhistory);
					        Contact_listview.setEmptyView(emptyText);
					       // Contact_listview.setVisibility((plusAdapter.isEmpty())?View.GONE:View.VISIBLE); 
						    Contact_listview.setAdapter(plusAdapter);
						    }else{
						    	lowerdevicesAdapter =  new HistoryAdapterLowerDevices(TakeNoteIncomingCall.this,
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
		 Map<String,String> params = new HashMap<String, String>();
		  params.put("email",user.get("email"));
		  params.put("username",user.get("uname"));
		  params.put("phone",phone);
		  params.put("name",name);
		  params.put("note",savenote.getText().toString());
			 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
			// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
			CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/salespacer/android_api/History/save_history.php", params,
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
							 TakeNoteIncomingCall.this, 
						      "Note saved successfully", 
						      stylealert);
					 alertCrouton.setConfiguration(configalert);
					 alertCrouton.show();   	
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
						dialogmessage="No internet connection No popup will be displayed on the web";
					} else if( error instanceof ClientError) { 
					} else if( error instanceof ServerError) {
						//Toast.makeText(getApplicationContext(),
							//     "Server error ", Toast.LENGTH_SHORT).show();
						dialogmessage="server error we are working on it ";
						
					} else if( error instanceof AuthFailureError) {
					} else if( error instanceof ParseError) {
						dialogmessage="Parse Error save";
					} else if( error instanceof NoConnectionError) {
						//Toast.makeText(getApplicationContext(),
						//			     "no internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="No internet connection No popup will be displayed on the web";
					} else if( error instanceof TimeoutError) {
						//Toast.makeText(getApplicationContext(),
							 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="No internet connection No popup will be displayed on the web";
					}
			    	Toast.makeText(getApplicationContext(),
						     dialogmessage, Toast.LENGTH_SHORT).show();
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
	
	public void UpdateHistory(){
		 Map<String,String> params = new HashMap<String, String>();
		 params.put("email",user.get("email"));
		 params.put("note",savenote.getText().toString());
		  params.put("id",id);
		 
		  
			 //http://www.globegokartshows.co.ke/api/comments/addcomment.php
			// Create Request http://www.globegokartshows.co.ke/api/likes/eventaddlikes1.php
			CustomRequest request = new CustomRequest(Request.Method.POST, "http://www.globegokartshows.co.ke/salespacer/android_api/History/update_history.php", params,
					new Response.Listener<JSONObject>() {
			    @Override
			    public void onResponse(JSONObject response) {
			    	savenote.setText("");
			    	//dialogmessage="Popup created check your web and write a note";
			    	 loading.setVisibility(View.GONE);
			    	 mGoogleNow.progressiveStop();
			    	 GetCount();
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
							 TakeNoteIncomingCall.this, 
						      "Note updated successfully", 
						      stylealert);
					 alertCrouton.setConfiguration(configalert);
					 alertCrouton.show();
					 savenote.setText("");
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
						dialogmessage="No internet connection No popup will be displayed on the web";
					} else if( error instanceof ClientError) { 
					} else if( error instanceof ServerError) {
						//Toast.makeText(getApplicationContext(),
							//     "Server error ", Toast.LENGTH_SHORT).show();
						dialogmessage="server error we are working on it ";
						
					} else if( error instanceof AuthFailureError) {
					} else if( error instanceof ParseError) {
						dialogmessage="Parse Error update";
					} else if( error instanceof NoConnectionError) {
						//Toast.makeText(getApplicationContext(),
						//			     "no internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="No internet connection No popup will be displayed on the web";
					} else if( error instanceof TimeoutError) {
						//Toast.makeText(getApplicationContext(),
							 //     "unstable internet connection ", Toast.LENGTH_SHORT).show();
						dialogmessage="No internet connection No popup will be displayed on the web";
					}
			    
			    	Toast.makeText(getApplicationContext(),
							     dialogmessage, Toast.LENGTH_SHORT).show();
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
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		// TODO Auto-generated method stub
		 TakeNoteIncomingCall.this. registerReceiver(receivertwo, new IntentFilter(CheckNoteidService.NOTIFICATION));
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
		TakeNoteIncomingCall.this.unregisterReceiver(receivertwo);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub

	}

}
