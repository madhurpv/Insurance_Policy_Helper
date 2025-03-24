package com.example.licpolicyhelper;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    /*
    * Widget

    Login page
    Dashboard - Quick access buttons (enable auto  sms, etc.)
          -Settings
          -Customers
          -SMS settings + enable button
          -Bday settings  + enable
    Settings
         -Change password
         -sign out, display username
    Customers
         -add customers
         - view data (download excel)
    SMS settings
         -custom mssg
         -criteria for receiver
         - there can be multiple policies
    Bday Settings
         -custom mssg
         -criteria for receiver (here  acc to age)
         - there can be multiple policies
    *
    * */

    /*
    *
    * TODO : Set Battery usage to UNRESTRICTED
    *
    * */

    CardView birthdaySettingsCard, smsSettingsCard, customersCard, settingsCard, autoSMSCard;
    TextView cardText1AutoSMS;
    ConstraintLayout autoSMSCardColourBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        birthdaySettingsCard = findViewById(R.id.birthdaySettingsCard);
        smsSettingsCard = findViewById(R.id.smsSettingsCard);
        customersCard = findViewById(R.id.customersCard);
        settingsCard = findViewById(R.id.settingsCard);
        autoSMSCard = findViewById(R.id.autoSMSCard);

        autoSMSCardColourBG = findViewById(R.id.autoSMSCardColourBG);
        cardText1AutoSMS = findViewById(R.id.cardText1AutoSMS);

        //checkIfServiceIsRunning(this);
        //checkIfSMSServiceIsRunning();


        autoSMSCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Starting Service", Toast.LENGTH_SHORT).show();
                //Starting service
                Intent serviceIntent = new Intent(MainActivity.this, PhoneCallReceiver.class);
                MainActivity.this.startService(serviceIntent);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkIfSMSServiceIsRunning();
                    }
                }, 300);
            }
        });


        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, "Stopping Service", Toast.LENGTH_SHORT).show();
                /*Intent serviceIntent = new Intent(MainActivity.this, PhoneCallReceiver.class);
                MainActivity.this.stopService(serviceIntent);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkIfSMSServiceIsRunning();
                    }
                }, 300);*/

                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        customersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CustomersActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        smsSettingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkIfSMSServiceIsRunning();
                Intent myIntent = new Intent(MainActivity.this, SMSSettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        birthdaySettingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, BirthdaySettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });



        // Running Code after 1 sec
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIfSMSServiceIsRunning();
            }
        }, 1000);


    }


    /*private boolean isSMSServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            int pid = android.os.Process.myPid(); // Get current process ID
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(getPackageName())) {
                            return true; // The app (including services) is running
                        }
                    }
                }
            }
        }
        return false;
    }*/

    /*public void checkIfServiceIsRunning(Context context) {
        Intent intent = new Intent(context, PhoneCallReceiver.class);

        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PhoneCallReceiver.LocalBinder binder = (PhoneCallReceiver.LocalBinder) service;
                PhoneCallReceiver phoneCallService = binder.getService();
                boolean isRunning = phoneCallService.isRunning();

                Toast.makeText(context, isRunning ? "Service is running" : "Service is NOT running", Toast.LENGTH_SHORT).show();

                // Unbind the service immediately after checking
                context.unbindService(this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(context, "Service disconnected", Toast.LENGTH_SHORT).show();
            }
        };

        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }*/

    /*public void checkIfServiceIsRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean isRunning = false;

        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                Log.d("QWER", service.service.getClassName());
                if (PhoneCallReceiver.class.getName().equals(service.service.getClassName())) {
                    isRunning = true;
                    break;
                }
            }
        }

        Toast.makeText(context, isRunning ? "Service is running" : "Service is NOT running", Toast.LENGTH_SHORT).show();
    }*/

    public void checkIfSMSServiceIsRunning() {
        boolean isRunning = PhoneCallReceiver.isServiceRunning;
        if(isRunning){
            Toast.makeText(this, "Service Detected Running!", Toast.LENGTH_SHORT).show();
            autoSMSCardColourBG.setBackgroundColor(Color.parseColor("#55FF55"));
            cardText1AutoSMS.setText("Auto SMS:Enabled");
            autoSMSCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent serviceIntent = new Intent(MainActivity.this, PhoneCallReceiver.class);
                    MainActivity.this.stopService(serviceIntent);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkIfSMSServiceIsRunning();
                        }
                    }, 300);
                }
            });
        }
        else{
            Toast.makeText(this, "Service Detected Not Running!", Toast.LENGTH_SHORT).show();
            autoSMSCardColourBG.setBackgroundColor(Color.parseColor("#FF5555"));
            cardText1AutoSMS.setText("Auto SMS:Disabled");
            autoSMSCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, PhoneCallReceiver.class);
                    MainActivity.this.startService(i);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkIfSMSServiceIsRunning();
                        }
                    }, 300);
                }
            });
        }
    }
}