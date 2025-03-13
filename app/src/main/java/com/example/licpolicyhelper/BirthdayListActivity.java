package com.example.licpolicyhelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class BirthdayListActivity extends AppCompatActivity {

    FloatingActionButton addNewBirthdayManuallyFloatingActionButton, addNewBirthdayContactsFloatingActionButton;


    Dialog newBirthdayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_birthday_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        addNewBirthdayManuallyFloatingActionButton = findViewById(R.id.addNewBirthdayManuallyFloatingActionButton);
        addNewBirthdayContactsFloatingActionButton = findViewById(R.id.addNewBirthdayContactsFloatingActionButton);


        addNewBirthdayManuallyFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewBirthdayPopUpDialog();
            }
        });
    }



    private void showNewBirthdayPopUpDialog() {
        // Create a new editCustomerDetailsDialog
        newBirthdayDialog = new Dialog(this);
        newBirthdayDialog.setContentView(R.layout.birthday_new_popupdialog);
        //editCustomerDetailsDialog.setCancelable(true); // Allows dismissing by tapping outside
        newBirthdayDialog.setCancelable(false);
        newBirthdayDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the editCustomerDetailsDialog
        TextView title = newBirthdayDialog.findViewById(R.id.dialogTitle);
        EditText dameEditText = newBirthdayDialog.findViewById(R.id.dialogNameEditText);
        EditText phoneNoEditText = newBirthdayDialog.findViewById(R.id.dialogPhoneNoEditText);
        EditText birthDateEditText = newBirthdayDialog.findViewById(R.id.dialogBirthDateEditText);
        ImageView birthDateCalenderImageView = newBirthdayDialog.findViewById(R.id.birthDateCalenderImageView);

        //Button editDetailsButton = editCustomerDetailsDialog.findViewById(R.id.editButton);
        Button cancelButton = newBirthdayDialog.findViewById(R.id.cancelButton);
        Button button2 = newBirthdayDialog.findViewById(R.id.button2);
        Button saveButton = newBirthdayDialog.findViewById(R.id.saveButton);

        ProgressBar progressBarNewPopup = newBirthdayDialog.findViewById(R.id.progressBarNewPopup);
        View disabledPopupView = newBirthdayDialog.findViewById(R.id.disabledPopupView);



        birthDateCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BirthdayListActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    birthDateEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        // Set up button click listeners
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
                //editCustomerDetailsDialog.cancel();
                //showEditCustomerCancelPopUpDialog();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 2 clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarNewPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                //Save here
                progressBarNewPopup.setVisibility(View.GONE);
                disabledPopupView.setVisibility(View.GONE);

                /*progressBarNewPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        progressBarNewPopup.setVisibility(View.GONE);
                        disabledPopupView.setVisibility(View.GONE);
                        editCustomerDetailsDialog.dismiss(); // Close the editCustomerDetailsDialog
                    }
                }, 5000);*/

                newBirthdayDialog.dismiss();

            }
        });

        newBirthdayDialog.show();
    }
}