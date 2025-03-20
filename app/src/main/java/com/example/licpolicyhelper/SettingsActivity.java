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

public class SettingsActivity extends AppCompatActivity {

    CardView userInfoCard, changePasswordCard, smsSettingsCard, birthdaySettingsCard, signOutCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userInfoCard = findViewById(R.id.userInfoCard);
        changePasswordCard = findViewById(R.id.changePasswordCard);
        smsSettingsCard = findViewById(R.id.smsSettingsCard);
        birthdaySettingsCard = findViewById(R.id.birthdaySettingsCard);
        signOutCard = findViewById(R.id.signOutCard);



        userInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changePasswordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });

        smsSettingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SettingsActivity.this, SMSSettingsActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });

        birthdaySettingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SettingsActivity.this, BirthdaySettingsActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });

        signOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}