package com.example.licpolicyhelper;

import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.CallLog;
import android.telecom.InCallService;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class PhoneCallReceiver extends Service {

    private final IBinder binder = new LocalBinder();
    public static boolean isServiceRunning = false;

    public class LocalBinder extends Binder {
        PhoneCallReceiver getService() {
            return PhoneCallReceiver.this;
        }
    }

    private final BroadcastReceiver callReceiver = new BroadcastReceiver() {

        String previousState;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                Log.d("QWER", "In Service!");
                String state = intent.getStringExtra("state");
                String phoneNumber = intent.getStringExtra("incoming_number");
                phoneNumber = getRecentCallNumber(getApplicationContext());
                Log.d("QWER", "previousState -> " + previousState);

                if (state != null && state.equals("IDLE") && previousState.equals("OFFHOOK")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    String phoneSMS = sharedPreferences.getString("phoneSMS", "ERRORRR!!!!");
                    //Toast.makeText(getApplicationContext(), "Hello " + (phoneNumber != null ? phoneNumber : "Unknown"), Toast.LENGTH_SHORT).show();
                    sendSMS(phoneNumber, phoneSMS);
                }
            }
            previousState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(callReceiver, filter);
        isServiceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isServiceRunning)
            stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callReceiver);
        isServiceRunning = false;
        //Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    public boolean isRunning() {
        return isServiceRunning;
    }



    public static String getRecentCallNumber(Context context) {
        String recentNumber = null;

        // Query the Call Log content provider
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER},
                null,
                null,
                CallLog.Calls.DATE + " DESC" // Order by most recent
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                recentNumber = cursor.getString(0); // Get the first (most recent) number
            }
            cursor.close();
        }

        return recentNumber;
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String SENT = "SMS_SENT";
            PendingIntent pi = PendingIntent.getBroadcast(this, 0,new Intent(SENT), PendingIntent.FLAG_IMMUTABLE);
            smsManager.sendTextMessage(phoneNo, null, msg, pi, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
