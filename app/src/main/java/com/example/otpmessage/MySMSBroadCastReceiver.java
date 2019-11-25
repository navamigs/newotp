package com.example.otpmessage;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySMSBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // Get Bundle object contained in the SMS intent passed in
            Bundle bundle = intent.getExtras();
            SmsMessage[] smsm = null;
            String sms_str ="";

            if (bundle != null)
            {
                // Get the SMS message
                //pdu- protocol data unit
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsm = new SmsMessage[pdus.length];
                for (int i=0; i<smsm.length; i++){
                    smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                  //  sms_str += "\r\nMessage: ";
                    sms_str += smsm[i].getMessageBody().toString();
                    sms_str+= "\r\n";

                    String Sender = smsm[i].getOriginatingAddress();
                   // String Sen =smsm[i].getDisplayOriginatingAddress();//both are same
                    //Check here sender is yours
                    String num="+919481012406";
                   if(   PhoneNumberUtils.compare(num,Sender)) {
                     Intent smsIntent = new Intent("otp");
                     smsIntent.putExtra("message", sms_str);

                     LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                 }
                 else {
                     //Toast.makeText(MySMSBroadCastReceiver.this,"invalid url!", Toast.LENGTH_LONG).show();

                 }
               }
            }
        }
}






