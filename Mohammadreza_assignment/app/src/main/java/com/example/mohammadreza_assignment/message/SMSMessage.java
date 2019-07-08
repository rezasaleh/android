package com.example.mohammadreza_assignment.message;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.mohammadreza_assignment.R;
import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;
import com.example.mohammadreza_assignment.mytoast.ToastBuild;

import java.util.Optional;
import java.util.function.Predicate;

public class SMSMessage extends BroadcastReceiver {

    private static final String SMS_RECEIVED  = android.os.Message.class.getSimpleName();
    public static final String pdu_type = "pdus";

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {

                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                final String receivedNumber = msgs[i].getOriginatingAddress();

                Optional<Person> registeredPerson = Personcollection.getPersonList().stream()
                        .filter(new Predicate<Person>() {
                            @Override
                            public boolean test(Person person) {
                                return person.getPhone().equals(receivedNumber);
                            }
                        })
                        .findFirst();

                if (registeredPerson.isPresent()) {
                    Person person = registeredPerson.get();
                    ToastBuild.displayImageToast(context,
                            "SMS from " + person.getFullName(),
                            person.getImageResourceId(),
                            Toast.LENGTH_LONG).show();
                } else {

                    ToastBuild.displayImageToast(context,
                            "SMS from " + receivedNumber,
                            R.drawable.no_photo_available,
                            Toast.LENGTH_LONG).show();
                }


                strMessage += "SMS from " + receivedNumber;
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                
                Log.d(SMS_RECEIVED , "onReceive: " + strMessage);
            }
        }
    }
}

