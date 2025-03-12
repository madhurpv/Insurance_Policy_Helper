package com.example.licpolicyhelper;

import android.app.Dialog;
import android.content.Intent;
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


    CardView birthdayListCard, birthdayMessage1Card, birthdayMessage2Card;

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


        birthdayListCard = findViewById(R.id.birthdayListCard);
        birthdayMessage1Card = findViewById(R.id.birthdayMessage1Card);
        birthdayMessage2Card = findViewById(R.id.birthdayMessage2Card);


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
                showMessage1PopUpDialog();
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
        Button closeButton = dialog.findViewById(R.id.closeButton);


        message1EditText.setText("");

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
                Toast.makeText(getApplicationContext(), "Cancel Button clicked!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
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
        Button closeButton = dialog.findViewById(R.id.closeButton);


        message2EditText.setText("");

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
                Toast.makeText(getApplicationContext(), "Cancel Button clicked!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        dialog.show();
    }
}