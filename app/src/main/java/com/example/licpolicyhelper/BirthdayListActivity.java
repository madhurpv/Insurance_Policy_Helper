package com.example.licpolicyhelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BirthdayListActivity extends AppCompatActivity {

    FloatingActionButton addNewBirthdayManuallyFloatingActionButton, addNewBirthdayContactsFloatingActionButton;

    RecyclerView recyclerView;


    List<BirthdayDetailsClass> birthdayList;
    BirthdayRecyclerViewAdapter adapter;



    Dialog newBirthdayDialog, editBirthdayDialog;

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


        recyclerView = findViewById(R.id.birthdayListRecyclerView);

        birthdayList = new ArrayList<>();

        sortBirthdays(birthdayList, "");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BirthdayRecyclerViewAdapter(birthdayList);
        adapter.setOnClickListener((position, birthdate) -> {
            // Handle item click here
            //Toast.makeText(getApplicationContext(), "Clicked: " + birthdate.getName(), Toast.LENGTH_SHORT).show();
            showBirthdayEditPopUpDialog(position);
            //showCustomerPopUpDialog(position);
        });
        recyclerView.setAdapter(adapter);

        fetchBirthdaysList();





        addNewBirthdayManuallyFloatingActionButton = findViewById(R.id.addNewBirthdayManuallyFloatingActionButton);
        addNewBirthdayContactsFloatingActionButton = findViewById(R.id.addNewBirthdayContactsFloatingActionButton);


        addNewBirthdayManuallyFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewBirthdayPopUpDialog("", "");
            }
        });



        //For getting Contact
        ActivityResultLauncher<Intent> contactPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri contactUri = result.getData().getData();
                        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                        try (Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null)) {
                            if (cursor != null && cursor.moveToFirst()) {
                                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //Toast.makeText(this, "Name: " + name + "\nPhone: " + phoneNumber, Toast.LENGTH_LONG).show();
                                showNewBirthdayPopUpDialog(name, phoneNumber);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(this, "Some Error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        addNewBirthdayContactsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                contactPickerLauncher.launch(intent);
            }
        });


    }







    public static void sortBirthdays(List<BirthdayDetailsClass> birthdays, String sortBy) {
        Comparator<BirthdayDetailsClass> comparator;

        comparator = Comparator.comparingLong(BirthdayDetailsClass::getLongBirthDate);

        Collections.sort(birthdays, comparator);
    }





    private void showNewBirthdayPopUpDialog(String name, String phoneNumber) {
        // Create a new editCustomerDetailsDialog
        newBirthdayDialog = new Dialog(this);
        newBirthdayDialog.setContentView(R.layout.birthday_new_popupdialog);
        //editCustomerDetailsDialog.setCancelable(true); // Allows dismissing by tapping outside
        newBirthdayDialog.setCancelable(false);
        newBirthdayDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the editCustomerDetailsDialog
        TextView title = newBirthdayDialog.findViewById(R.id.dialogTitle);
        EditText nameEditText = newBirthdayDialog.findViewById(R.id.dialogNameEditText);
        EditText phoneNoEditText = newBirthdayDialog.findViewById(R.id.dialogPhoneNoEditText);
        EditText birthDateEditText = newBirthdayDialog.findViewById(R.id.dialogBirthDateEditText);
        ImageView birthDateCalenderImageView = newBirthdayDialog.findViewById(R.id.birthDateCalenderImageView);

        //Button editDetailsButton = editCustomerDetailsDialog.findViewById(R.id.editButton);
        Button cancelButton = newBirthdayDialog.findViewById(R.id.cancelButton);
        Button button2 = newBirthdayDialog.findViewById(R.id.button2);
        Button saveButton = newBirthdayDialog.findViewById(R.id.saveButton);

        ProgressBar progressBarNewPopup = newBirthdayDialog.findViewById(R.id.progressBarNewPopup);
        View disabledPopupView = newBirthdayDialog.findViewById(R.id.disabledPopupView);


        nameEditText.setText(name);
        phoneNoEditText.setText(phoneNumber);



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
                showNewBirthdayCancelPopUpDialog();
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
                addNewBirthdayFirebase(new BirthdayDetailsClass(nameEditText.getText().toString(), phoneNoEditText.getText().toString(), birthDateEditText.getText().toString()));
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



    private void showNewBirthdayCancelPopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.birthday_newcancel_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        Button discardButton = dialog.findViewById(R.id.discardButton);
        Button goBackButton = dialog.findViewById(R.id.goBackButton);

        // Set up button click listeners
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBirthdayDialog.cancel();
                dialog.dismiss();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void showBirthdayEditPopUpDialog(int position) {
        // Create a new editCustomerDetailsDialog
        editBirthdayDialog = new Dialog(this);
        editBirthdayDialog.setContentView(R.layout.birthday_edit_popupdialog);
        //editCustomerDetailsDialog.setCancelable(true); // Allows dismissing by tapping outside
        editBirthdayDialog.setCancelable(false);
        editBirthdayDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the editCustomerDetailsDialog
        TextView title = editBirthdayDialog.findViewById(R.id.dialogTitle);
        EditText nameEditText = editBirthdayDialog.findViewById(R.id.dialogNameEditText);
        EditText phoneNoEditText = editBirthdayDialog.findViewById(R.id.dialogPhoneNoEditText);
        EditText dateOfBirthEditText = editBirthdayDialog.findViewById(R.id.dialogBirthDateEditText);

        ImageView dateOfBirthCalenderImageView = editBirthdayDialog.findViewById(R.id.birthDateCalenderImageView);


        //Button editDetailsButton = editCustomerDetailsDialog.findViewById(R.id.editButton);
        Button cancelButton = editBirthdayDialog.findViewById(R.id.cancelButton);
        Button button2 = editBirthdayDialog.findViewById(R.id.button2);
        Button saveButton = editBirthdayDialog.findViewById(R.id.saveButton);

        ProgressBar progressBarEditPopup = editBirthdayDialog.findViewById(R.id.progressBarEditPopup);
        View disabledPopupView = editBirthdayDialog.findViewById(R.id.disabledPopupView);


        nameEditText.setText(birthdayList.get(position).getName());
        phoneNoEditText.setText(birthdayList.get(position).getPhoneNo());
        dateOfBirthEditText.setText(birthdayList.get(position).getBirthDate());

        dateOfBirthCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BirthdayListActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dateOfBirthEditText.setText(date);
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
                showEditBirthdayCancelPopUpDialog();
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
                progressBarEditPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                //Save here
                progressBarEditPopup.setVisibility(View.GONE);
                disabledPopupView.setVisibility(View.GONE);

                /*progressBarEditPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        progressBarEditPopup.setVisibility(View.GONE);
                        disabledPopupView.setVisibility(View.GONE);
                        editCustomerDetailsDialog.dismiss(); // Close the editCustomerDetailsDialog
                    }
                }, 5000);*/

            }
        });

        editBirthdayDialog.show();
    }



    private void showEditBirthdayCancelPopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.birthday_editcancel_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        Button discardButton = dialog.findViewById(R.id.discardButton);
        Button goBackButton = dialog.findViewById(R.id.goBackButton);

        // Set up button click listeners
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBirthdayDialog.cancel();
                dialog.dismiss();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void fetchBirthdaysList(){
        // TODO
        List<BirthdayDetailsClass> newList = new ArrayList<>();

        newList.add(new BirthdayDetailsClass("Ramesh K", "9876543210", "10/10/2020"));
        newList.add(new BirthdayDetailsClass("Kamlesh", "7418529630", "10/08/2010"));
        newList.add(new BirthdayDetailsClass("Suraj", "9876543210", "25/11/2022"));
        newList.add(new BirthdayDetailsClass("Amit L", "9876543210", "10/12/1990"));
        newList.add(new BirthdayDetailsClass("Shamit L", "9876543210", "10/05/2000"));

        birthdayList.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    private void addNewBirthdayFirebase(BirthdayDetailsClass birthdayDetailsClass){
        // TODO
    }
}