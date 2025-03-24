package com.example.licpolicyhelper;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    CardView userInfoCard, changePasswordCard, smsSettingsCard, birthdaySettingsCard, signOutCard;
    TextView usernameTextView;

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

        usernameTextView = findViewById(R.id.usernameTextView);



        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "ERRORRR!!!!");
        usernameTextView.setText(username);


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
                showSignOutConfirmPopUpDialog();
            }
        });
    }



    private void showSignOutConfirmPopUpDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.signout_confirm_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        Button signOutButton = dialog.findViewById(R.id.signOutButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        ProgressBar progressBarEditPopup = dialog.findViewById(R.id.progressBarEditPopup);
        View disabledPopupView = dialog.findViewById(R.id.disabledPopupView);

        // Set up button click listeners
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarEditPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                //Delete here
                signOut();

                progressBarEditPopup.setVisibility(View.GONE);
                disabledPopupView.setVisibility(View.GONE);

                dialog.dismiss();

                //Clear all activities
                finishAffinity();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void signOut(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("login");
        editor.remove("username");
        editor.remove("password");
        editor.apply();
    }
}