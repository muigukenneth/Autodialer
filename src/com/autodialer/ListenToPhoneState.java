package com.autodialer;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ListenToPhoneState extends PhoneStateListener {

    public void onCallStateChanged(int state, String incomingNumber) {
        Log.i("telephony-example", "State changed: " + stateName(state));
        switch (state) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
        }
    }

    String stateName(int state) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE: return "Idle";
            case TelephonyManager.CALL_STATE_OFFHOOK: return "Off hook";
            case TelephonyManager.CALL_STATE_RINGING: return "Ringing";
        }
        return Integer.toString(state);
    }
}

