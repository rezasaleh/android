package com.example.mohammadreza_assignment.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {

    // Get the name of the class
    private static final String TAG = PhoneCallReceiver.class.getSimpleName();

    // 3- Define required attributes
    private static int lastState = TelephonyManager.CALL_STATE_IDLE; // States: IDLE, OFFHOOK, RINGING
    private static Date callStartTime;   // Call date
    private static boolean isIncoming;   // Is current call an incoming call
    private static String savedNumber;   // Save the number

    // 2- Override onReceive() to listen to specific intent
    // Determine and save current state of the phone
    // OS will pass an intent to this method which contains broadcast event information
    @Override
    public void onReceive(Context context, Intent intent) {

        // 2-1- getAction() return the purpose of passed intent, what is the reason we have this intent
        if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")){
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

            // Just log the outgoing call
            Log.i(TAG, "new outgoing call <" + savedNumber + ">");
        } else {
            // 2-2- If the call is INCOMING save the NUMBER in a String variable
//            String incomingNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            savedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            // 2-3- In addition, save the STATE which can be IDLE, OFFHOOK or RINGING
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            Log.i(TAG, "stateStr = " + stateStr + "\nincommingNumber = " + savedNumber);

            // 2-4- Determine the state of the phone: IDLE, OFFHOOK or RINGING
            int state = 0;

            // 2-5- determine and save the CALL_STATE based on intent extra state
            if(TelephonyManager.EXTRA_STATE_IDLE.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_IDLE;

            } else if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;

            } else if(TelephonyManager.EXTRA_STATE_RINGING.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            // 2-6- Pass the saved incomingNumber and call state to onCallStateChanged() method
            // which we will manually create it in the next step
            onCallStateChanged(context, state, savedNumber);
        }
    }

    // Actions based on phone state
    private void onCallStateChanged(Context context, int state, String number) {

        // 3- Determine if new state is the same as before or it is changed
        // 3-1- If it is the same we do not need to run this method and just return
        if (lastState == state) {
            return;
        }

        // 3-2- If the state is changed
        // Based on the state we want to call appropriate method to handle the situation
        switch (state) {

            // If the phone is ringing
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;

                // Call this method and pass the information, but this method is abstract and
                // it is not clear what to do. In child class we can have our appropriate implementation
                onIncomingCallStarted(context, savedNumber, callStartTime);
                break;

            // The phone can be in OFF-HOOK mode by incoming or outgoing call
            // We have to distinguish incoming from outgoing
            case TelephonyManager.CALL_STATE_OFFHOOK:

                // It is INCOMING call if previous state was RINGING
                // RINGING -> OFF-HOOK : incoming call
                // We have to handle both situation, so we will have an if-else
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    // lastState != TelephonyManager.CALL_STATE_RINGING --> OUTGOING call
                    isIncoming = false;
                    callStartTime = new Date();
//                    savedNumber = number;

                    // Call this method and pass the information, but this method is abstract and
                    // it is not clear what to do. In child class we can have our appropriate implementation
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                } else {
                    isIncoming = true;
                    callStartTime = new Date();
//                    savedNumber = number;

                    // Call this method and pass the information, but this method is abstract and
                    // it is not clear what to do. In child class we can have our appropriate implementation
                    onIncomingCallAnswered(context, savedNumber, callStartTime);
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                // Current state is IDLE so if the previous state was:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    // ringing -> idle : MISSED-CALL
                    onMissedCall(context, savedNumber, callStartTime);
                } else if(isIncoming){
                    // incoming -> idle : ANSWERED-INCOMING-CALL
                    onIncomingCallEnded(context, savedNumber, callStartTime);
                } else {
                    // outgoing -> idle : TERMINATED-OUTGOING-CALL
                    onOutgoingCallEnded(context, savedNumber, callStartTime);
                }
                break;
            default:
                break;
        }

        lastState = state;
    }

    // To override by derived class

    // Start
    protected abstract void onIncomingCallStarted(Context ctx, String number, Date start);
    protected abstract void onOutgoingCallStarted(Context ctx, String number, Date start);

    // Answer
    protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);

    // Missed
    protected abstract void onMissedCall(Context ctx, String number, Date start);

    // End
    protected abstract void onIncomingCallEnded(Context ctx, String number, Date start);
    protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start);

}
