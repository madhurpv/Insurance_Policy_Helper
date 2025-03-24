package com.example.licpolicyhelper;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BirthdaySettingsActivity extends AppCompatActivity {


    CardView todaysBirthdayListCard, birthdayListCard, birthdayMessage1Card, birthdayMessage2Card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_birthday_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        todaysBirthdayListCard = findViewById(R.id.todaysBirthdayListCard);
        birthdayListCard = findViewById(R.id.birthdayListCard);
        birthdayMessage1Card = findViewById(R.id.birthdayMessage1Card);
        birthdayMessage2Card = findViewById(R.id.birthdayMessage2Card);


        todaysBirthdayListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BirthdaySettingsActivity.this, BirthdayTodayListActivity.class);
                BirthdaySettingsActivity.this.startActivity(myIntent);
            }
        });

        birthdayListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BirthdaySettingsActivity.this, BirthdayListActivity.class);
                BirthdaySettingsActivity.this.startActivity(myIntent);
            }
        });

        birthdayMessage1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage1PopUpDialog();
            }
        });

        birthdayMessage2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage2PopUpDialog();
            }
        });
    }



    private void showMessage1PopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.birthdaysettings_message1_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        EditText message1EditText = dialog.findViewById(R.id.message1EditText);
        Button editDetailsButton = dialog.findViewById(R.id.editButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button saveButton = dialog.findViewById(R.id.saveButton);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        message1EditText.setText(sharedPreferences.getString("birthdayMessage1", ""));

        // Set up button click listeners
        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("birthdayMessage1", message1EditText.getText().toString());
                editor.apply();
                dialog.dismiss(); // Close the dialog
                Toast.makeText(getApplicationContext(), "Changes Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showMessage2PopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.birthdaysettings_message2_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        EditText message2EditText = dialog.findViewById(R.id.message2EditText);
        Button editDetailsButton = dialog.findViewById(R.id.editButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button saveButton = dialog.findViewById(R.id.saveButton);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        message2EditText.setText(sharedPreferences.getString("birthdayMessage2", ""));

        // Set up button click listeners
        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("birthdayMessage2", message2EditText.getText().toString());
                editor.apply();
                dialog.dismiss(); // Close the dialog
                Toast.makeText(getApplicationContext(), "Changes Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}