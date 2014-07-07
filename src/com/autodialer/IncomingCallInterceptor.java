package com.autodialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingCallInterceptor extends BroadcastReceiver {

   
    static boolean ring=false;
    static boolean callReceived=false;
    public static final String NOTIFICATION = "com.autodialer.IncomingCallInterceptor";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        
           // Get the current Phone State
          String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
          
          if(state==null)
              return;

          // If phone state "Rininging"
          if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
          {           
                    ring =true;
                   // Get the Caller's Phone Number
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // 4
                  //  msg += ". Incoming number is " + incomingNumber;
                   
                    Intent intentnew=new Intent(context,CheckContactExistService.class);
        	    
        	   
        	    	   intentnew.putExtra("PHONE", incomingNumber);
        	    	   context.startService(intentnew);	
            }



           // If incoming call is received
          if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
           {
                  callReceived=true;
            }


           // If phone is Idle
          if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
          {
                    // If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
                     if(ring==true&&callReceived==false)
                     {
                    	 Intent intenttwo = new Intent(NOTIFICATION);
      				   // intent.putExtra(FILEPATH, outputPath);
      				    intenttwo.putExtra("RESULT", 1);
      				  context.sendBroadcast(intenttwo);
                           ///   Toast.makeText(mContext, "It was A MISSED CALL from : "+callerPhoneNumber, Toast.LENGTH_LONG).show();
                     }
        }
    }  
}