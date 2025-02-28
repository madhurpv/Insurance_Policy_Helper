package com.example.licpolicyhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

    CardView birthdaySettingsCard, smsSettingsCard, customersCard, settingsCard, autoSMSCard;

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


        autoSMSCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


    }
}