package com.autodialer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class PopupActivity extends Activity {
	AlertDialog alert;
	AlertDialog.Builder builder;	
	TextView message;
	 String messagenew;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_activity);
		Bundle b = this.getIntent().getExtras();
		String messagenew = b.getString("MESSAGE");
		message=(TextView)findViewById(R.id.textmessage);
		
		 message.setText(messagenew);
		 final Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {
	            public void run() {
	                // TODO: Your application init goes here.
	               
	            	 PopupActivity.this.finish();
	            }
	        }, 3000);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	@Override
	public void onResume() {
	    super.onResume();
	  //  PopupActivity.this. registerReceiver(receiver, new IntentFilter(FirstPopupService.NOTIFICATION));
	}
	public void onStop() {
		super.onStop();
		//PopupActivity.this.unregisterReceiver(receiver);
	}
}
