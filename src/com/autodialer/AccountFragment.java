package com.autodialer;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;









import java.util.ArrayList;
import java.util.HashMap;

import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment  {
	Button profilepic;
Button btnLogout;
	ImageView profile;
	TextView textviewusername;
	TextView textviewemail;
	 DatabaseHandler db;
	   DatabaseHandler dbHandler;
	   HashMap<String,String> user;
		 DatabaseHandlerUser dbtwo ;
		 private static final float SHOWCASE_KITTEN_SCALE = 0.5f;
		    private static final float SHOWCASE_LIKE_SCALE = 1.2f;
		    ShowcaseView.ConfigOptions mOptions = new ShowcaseView.ConfigOptions();
		    ShowcaseViews mViews;
		    ShowcaseView sv;
	  private BroadcastReceiver receiver2 = new BroadcastReceiver() {
		  
		  @Override
		    public void onReceive(Context context, Intent intent) {
		      Bundle bundle = intent.getExtras();
		      if (bundle != null) {
		    //    String string = bundle.getString(DownloadService.FILEPATH);
		        int resultCode = bundle.getInt(UpdateService.RESULT);
		        if (resultCode == Activity.RESULT_OK) {
		        	
		        	 Toast.makeText(getActivity(), " logged out successful",
				              Toast.LENGTH_LONG).show(); 

		                UserFunctions logout = new UserFunctions();
		                logout.logoutUser(getActivity());
		                Intent login = new Intent(getActivity(), Login.class);
		                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(login);
		        	  getActivity().finish();
		        //  Toast.makeText(getActivity(),
		        //      "refreshing ",
		          //    Toast.LENGTH_LONG).show();
		        //  textView.setText("Download done");
		        } else {
		          Toast.makeText(getActivity(), "error on loggin out",
		              Toast.LENGTH_LONG).show();
		         
	                
		        //  textView.setText("Download failed");
		        }
		      }
		    }
		  };
	
	    private Bundle mArguments;

	    public static final String ARG_IMAGE_RES = "image_source";
	    public static final String ARG_ACTION_BG_RES = "image_action_bs_res";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	setHasOptionsMenu(true);
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
       // View mLinearLayout = mFadingHelper.createView(inflater);
    //    if (mArguments != null){
    ////        ImageView img = (ImageView) mLinearLayout.findViewById(R.id.image_header);
      //      img.setImageResource(mArguments.getInt(ARG_IMAGE_RES));
       // }
        LinearLayout mLinearLayout =  (LinearLayout)inflater.inflate(R.layout.account,container, false);
        dbtwo= new DatabaseHandlerUser(getActivity());
        dbHandler=new DatabaseHandler(getActivity());
        user = new HashMap<String, String>();
        user = dbtwo.getUserDetails();
      //  profilepic =(Button)mLinearLayout.findViewById(R.id.buttonchangeprofile);
      //  fblogout =(Button)mLinearLayout.findViewById(R.id.buttonfblogout);
        btnLogout = (Button) mLinearLayout.findViewById(R.id.logout);
     //   profile =(ImageView)mLinearLayout.findViewById(R.id.imageViewprofilepicture);
        textviewusername = (TextView) mLinearLayout.findViewById(R.id.textViewusername);
        textviewemail = (TextView) mLinearLayout.findViewById(R.id.textViewemail);
        textviewusername.setText("Username: "+user.get("uname"));
        textviewemail .setText("Email Address:"+user.get("email"));
       // profilepic.setText("Change Profile Picture");
     //   profilepic.setTextColor(Color.WHITE);
     //   profilepic.setBackgroundResource(R.drawable.button_gradient);
        btnLogout.setText("Logout");
        btnLogout.setTextColor(Color.WHITE);
        btnLogout.setBackgroundResource(R.drawable.button_gradient);
       // fblogout.setText("Logout via Facebook");
       // fblogout.setTextColor(Color.WHITE);
      //  fblogout.setBackgroundResource(R.drawable.button_gradient);
        db = new DatabaseHandler(getActivity());
        dbHandler=new DatabaseHandler(getActivity());
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
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
            	SharedPreferences preferences = getActivity().getPreferences(0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.clear();
                editor.commit();
                Intent intent=new Intent(getActivity(),UpdateService.class);
    			getActivity().startService(intent);	
    			// Intent intentonline=new Intent(getActivity(),ClearRegistrationService.class);
    			// getActivity().startService(intentonline);
              
            }
        });    
        return mLinearLayout;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		 SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
		 boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUNACTIVITY", true);
		 if (isFirstRun)
		 {
			 mOptions.block = false;
		        mOptions.hideOnClickOutside = false;

		        mViews = new ShowcaseViews(getActivity(),
		                new ShowcaseViews.OnShowcaseAcknowledged() {
		            @Override
		            public void onShowCaseAcknowledged(ShowcaseView showcaseView) {
		                Toast.makeText(getActivity(), "quick tutorial finished", Toast.LENGTH_SHORT).show();
		            }
		        });
		        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.logout,
		                R.string.showcase_login_title,
		                R.string.showcase_login_message,
		                SHOWCASE_KITTEN_SCALE));
		        mViews.addView( new ShowcaseViews.ItemViewProperties(R.id.textViewusername,
		                R.string.showcase_account_title,
		                R.string.showcase_account_message,
		                SHOWCASE_LIKE_SCALE));
		        mViews.show();
		     // Code to run once
		     SharedPreferences.Editor editor = wmbPreference.edit();
		     editor.putBoolean("FIRSTRUNACTIVITY", false);
		     editor.commit();
		 }
		  
	}
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);

	    //    mArguments = getArguments();
	     //   int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.drawable.ab_background_light;

	       // mFadingHelper = new FadingActionBarHelper()
	         //   .actionBarBackground(actionBarBg)
	          //  .headerLayout(R.layout.header)
	          //  .contentLayout(R.layout.account)
	           // .lightActionBar(actionBarBg == R.drawable.ab_background_light);
	     //   mFadingHelper.initActionBar(activity);
	     
	    }
		@Override
		public void onResume() {
			//new MyTaskRefresh().execute(rssFeed);
	    //	objAdapter.clear();
		  //onResume happens after onStart and onActivityCreate
			// new MyTask().execute(rssFeed);
			 getActivity(). registerReceiver(receiver2, new IntentFilter(UpdateService.NOTIFICATION));	
		  super.onResume() ; 
		}
		public void onStop() {
			super.onStop();
			
			getActivity().unregisterReceiver(receiver2);
		}
	public Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xffff0000;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);

	    paint.setAntiAlias(true);
	    paint.setDither(true);
	    paint.setFilterBitmap(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawOval(rectF, paint);

	    paint.setColor(Color.BLUE);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth((float) 4);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
	}
}
