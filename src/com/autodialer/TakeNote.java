package com.autodialer;



import java.util.ArrayList;






import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TakeNote extends Activity {
	 SharedPreferences myPrefsnow;
	EditText savenote;
	Button addnote;
	Button addnote2;
	Button addnote3;
	Button addnote4;
	Button addnote5;
	Button addnote6;
	Button addnote7;
	Button addnote8;
	Button addnote9;
	Button addnote10;
	//Button addnote;
	 DatabaseHandler db;
	 ListenToPhoneState listener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_note);
		 final DatabaseHandlerHistory dbHandler = new DatabaseHandlerHistory(this);
		 myPrefsnow = PreferenceManager.getDefaultSharedPreferences(this);
		 savenote = (EditText) findViewById(R.id.animalName);
		 addnote=(Button)findViewById(R.id.btnadd);
		 addnote2=(Button)findViewById(R.id.btnaddtwo);
		 addnote3=(Button)findViewById(R.id.btnaddthree);
		 addnote4=(Button)findViewById(R.id.btnaddfour);
		 addnote5=(Button)findViewById(R.id.btnaddfive);
		 addnote6=(Button)findViewById(R.id.btnaddsix);
		 addnote7=(Button)findViewById(R.id.btnaddseven);
		 addnote8=(Button)findViewById(R.id.btnaddeight);
		 addnote9=(Button)findViewById(R.id.btnaddnine);
		 addnote10=(Button)findViewById(R.id.btnaddten);
		 addnote.setVisibility(View.GONE);
		 addnote2.setVisibility(View.GONE);
		 addnote3.setVisibility(View.GONE);
		 addnote4.setVisibility(View.GONE);
		 addnote5.setVisibility(View.GONE);
		 addnote6.setVisibility(View.GONE);
		 addnote7.setVisibility(View.GONE);
		 addnote8.setVisibility(View.GONE);
		 addnote9.setVisibility(View.GONE);
		 addnote10.setVisibility(View.GONE);
		 int visible = myPrefsnow.getInt("num", 1);
		// switch (visible)
	      if(visible==1)  {
		// case 1:
			 addnote.setVisibility(View.VISIBLE);
	        	//break;
	      }else if(visible==2){
	      //  case 2:
	        	 addnote2.setVisibility(View.VISIBLE);
	        	 addnote.setVisibility(View.GONE);
	        	//break;
	}else if(visible==3){ 
      //  case 3:
        	 addnote3.setVisibility(View.VISIBLE);
        	 addnote.setVisibility(View.GONE);
	    // break;
	}else if(visible==4){
     //  case 4:
    	   addnote4.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
	   // break;
	}else if(visible==5){
     //  case 5:
    	   addnote5.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
	      //  break;
	}else if(visible==6){
      // case 6:
    	   addnote6.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
		 //   break;
	}else if(visible==7){
      // case 7:
    	   addnote7.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
		    //    break;
	}else if(visible==8){
      // case 8:
    	   addnote8.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
		    //    break;
	}else if(visible==9){
     //  case 9:
    	   addnote9.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
		     //   break;
	}else if(visible==10){
     //  case 10:
    	   addnote10.setVisibility(View.VISIBLE);
    	   addnote.setVisibility(View.GONE);
		       // break;
}
		
			db = new DatabaseHandler(this);
		 addnote.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Toast.makeText(getApplicationContext(), "Button 1 pressed",  Toast.LENGTH_SHORT).show(); 
		        	SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 2);
					editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	// int text = contact_array_from_db.get(0).getID();
		        	// String mobile = contact_array_from_db.get(0).getPhoneNumber();
		        	// String name = contact_array_from_db.get(0).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext();
		        	//finish();
		        }
		    });
		 addnote2.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        //	 Toast.makeText(getApplicationContext(), "Button 2 pressed",  Toast.LENGTH_SHORT).show(); 
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 3);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 String mobile = contact_array_from_db.get(1).getPhoneNumber();
		        	 String name = contact_array_from_db.get(1).getName();
		        	 //addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext2();
		        //	finish();
		        }
		    });
		 addnote3.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Toast.makeText(getApplicationContext(), "Button 3 pressed",  Toast.LENGTH_SHORT).show();
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 4);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 String mobile = contact_array_from_db.get(2).getPhoneNumber();
		        	 String name = contact_array_from_db.get(2).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext3();
		        	//finish();
		        }
		    });
		 addnote4.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Toast.makeText(getApplicationContext(), "Button 4 pressed",  Toast.LENGTH_SHORT).show();
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 5);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 String mobile = contact_array_from_db.get(3).getPhoneNumber();
		        	 String name = contact_array_from_db.get(3).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext4();
		        //	finish();
		        }
		    });
		 addnote5.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Toast.makeText(getApplicationContext(), "Button 5 pressed",  Toast.LENGTH_LONG).show();
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 6);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 String mobile = contact_array_from_db.get(4).getPhoneNumber();
		        	 String name = contact_array_from_db.get(4).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext5();
		        	//finish();
		        }
		    });
		 addnote6.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	// Toast.makeText(getApplicationContext(), "Button 6 pressed",  Toast.LENGTH_LONG).show();
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 7);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 String mobile = contact_array_from_db.get(5).getPhoneNumber();
		        	 String name = contact_array_from_db.get(5).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext6();
		        	//finish();
		        }
		    });
		 addnote7.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 8);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	// int text = contact_array_from_db.get(0).getID();
		        	// String mobile = contact_array_from_db.get(6).getPhoneNumber();
		        	// String name = contact_array_from_db.get(6).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext7();
		        	finish();
		        }
		    });
		 addnote8.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 9);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        //	 int text = contact_array_from_db.get(0).getID();
		        	// String mobile = contact_array_from_db.get(7).getPhoneNumber();
		        	// String name = contact_array_from_db.get(7).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext8();
		        	finish();
		        }
		    });
		 addnote9.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 10);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 //String mobile = contact_array_from_db.get(8).getPhoneNumber();
		        	// String name = contact_array_from_db.get(8).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext9();
		        	finish();
		        }
		    });
		 addnote10.setOnClickListener( new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 SharedPreferences.Editor editor = myPrefsnow.edit();
						editor.putInt("num", 1);
						editor.commit();  
		        	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		        	 int text = contact_array_from_db.get(0).getID();
		        	 //String mobile = contact_array_from_db.get(9).getPhoneNumber();
		        	// String name = contact_array_from_db.get(9).getName();
		        	// addnote.setVisibility(View.VISIBLE);
		        	// dbHandler.Add_History(new History(name,
			    		//	  mobile,savenote.getText().toString(), "muigukenneth@gmail.com"));
		        	// Toast.makeText(TakeNote.this,  "Note saved successfully"  , Toast.LENGTH_LONG).show();
		        	 callNext10();
		        	finish();
		        }
		    });
	}
	 private void callNext() {
		
		 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		 if(contact_array_from_db.size() >1){
		// int identify = contact_array_from_db.get(6).getID();
		// if( identify ==2){
	        try {
	        	Intent intentnew=new Intent(TakeNote.this,SecondPopupService.class);
				TakeNote.this.startService(intentnew);	
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
	              getSystemService(Context.TELEPHONY_SERVICE);
	            listener = new ListenToPhoneState();
	            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	        } catch (ActivityNotFoundException activityException) {
	            Log.e("telephony-example", "Call failed", activityException);
	        }
		// }else{
		//	    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
		// }
		 }else{
			    Toast.makeText(getApplicationContext(), "All calls finished",  Toast.LENGTH_LONG).show(); 
				 SharedPreferences.Editor editor = myPrefsnow.edit();
					editor.putInt("num", 1);
					editor.commit();  
					finish();
		 }
	    }
	 private void callNext2() {
			
		 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		 if(contact_array_from_db.size() > 2){
		// int identify = contact_array_from_db.get(6).getID();
		// if( identify ==2){
	        try {
	        	Intent intentnew=new Intent(TakeNote.this,ThirdPopupService.class);
	        	TakeNote.this.startService(intentnew);	
	        	
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
	 
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
		 }
	    }
	 private void callNext3() {
			
		 ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		 if(contact_array_from_db.size() > 3){
		// int identify = contact_array_from_db.get(6).getID();
		// if( identify ==2){
	        try {
	        	Intent intentnew=new Intent(TakeNote.this,FourthPopupService.class);
				TakeNote.this.startService(intentnew);	
	        	
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
	 
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show(); 
			    finish();
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
	              getSystemService(Context.TELEPHONY_SERVICE);
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
			    Toast.makeText(getApplicationContext(), "All Auto dailed calls finished",  Toast.LENGTH_LONG).show();
			    finish();
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
		        		
		        		
						// String urlcomment1 = comment1;number
		            //CALL_STATE_IDLE;
		          //  Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",  Toast.LENGTH_SHORT).show();
		            Intent intent = new Intent(TakeNote.this, TakeNote.class);
		            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		           // intent.putExtra("contact", (objBean.getName()));
	                // Pass all data country
	              //  intent.putExtra("phone", (objBean.getBirthdate()));
	                // Pass all data population
	              //  intent.putExtra("email",(objBean.getGender()));
		               startActivity(intent);
		               finish();
		               
		               
		               
		        	isPhoneCalling = false;
					}
		            break;
		        case TelephonyManager.CALL_STATE_OFFHOOK:
		            //CALL_STATE_OFFHOOK;
		         //   Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",  Toast.LENGTH_SHORT).show();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_note, menu);
		return true;
	}

}
