package com.example.licpolicyhelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class CustomersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CustomerClass> customersList;
    EditText searchbar;
    ImageView searchIcon;
    Spinner sortingSpinner;
    FloatingActionButton addNewCustomerFloatingActionButton;

    CustomerRecyclerViewAdapter adapter;

    ArrayList<CustomerClass> filteredlist;

    Dialog editCustomerDetailsDialog, newCustomerDetailsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.customersRecyclerView);

        // Sample data
        customersList = new ArrayList<>();

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        adapter = new CustomerRecyclerViewAdapter(customersList);
        adapter.setOnClickListener((position, customer) -> {
            // Handle item click here
            Toast.makeText(getApplicationContext(), "Clicked: " + customer.getName(), Toast.LENGTH_SHORT).show();
            showCustomerInfoPopUpDialog(position);
        });
        recyclerView.setAdapter(adapter);
        fetchCustomersList();

        searchbar = findViewById(R.id.searchbar);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchFilter(searchbar.getText().toString());
            }
        });


        searchIcon = findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbar.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchbar, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        sortingSpinner = findViewById(R.id.sortingSpinner);
        String[] sortingSpinnerItems = new String[]{"Name", "Policy No", "Due Date"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortingSpinnerItems);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, sortingSpinnerItems);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        sortingSpinner.setAdapter(spinnerAdapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(CustomersActivity.this, "Selected : " + sortingSpinnerItems[i], Toast.LENGTH_SHORT).show();
                sortCustomers(customersList, sortingSpinnerItems[i]);
                Log.d("QWER", "customersList : " + customersList);
                if(filteredlist!=null) {
                    sortCustomers(filteredlist, sortingSpinnerItems[i]);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addNewCustomerFloatingActionButton = findViewById(R.id.addNewCustomerFloatingActionButton);
        addNewCustomerFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewCustomerPopUpDialog();
            }
        });

    }


    public static void sortCustomers(List<CustomerClass> customers, String sortBy) {
        Comparator<CustomerClass> comparator;

        switch (sortBy) {
            case "Name":
                comparator = Comparator.comparing(CustomerClass::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "Policy No":
                comparator = Comparator.comparingInt(CustomerClass::getPolicyNo);
                break;
            case "Due Date":
                comparator = Comparator.comparingLong(CustomerClass::getNextDueDateUnix);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort key: " + sortBy);
        }

        Collections.sort(customers, comparator);
    }



    private void searchFilter(String text) {
        // creating a new array list to filter data
        filteredlist = new ArrayList<>();

        /*if(text==null || text.isEmpty()){
            adapter.filterList(filteredlist);
        }*/

        // running a for loop to compare elements
        for (CustomerClass item : customersList) {
            // checking if the entered string matches any item of our recycler view
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || String.valueOf(item.getPolicyNo()).toLowerCase().contains(text.toLowerCase())) {
                // adding matched item to the filtered list
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            // displaying a toast message if no data found
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            adapter.filterList(filteredlist);
        } else {
            // passing the filtered list to the adapter class
            sortCustomers(filteredlist, sortingSpinner.getSelectedItem().toString());
            adapter.filterList(filteredlist);
        }

    }





    private void showCustomerInfoPopUpDialog(int position) {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customersinfo_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        TextView nameTextView = dialog.findViewById(R.id.dialogName);
        TextView policyNoTextView = dialog.findViewById(R.id.dialogPolicyNo);
        TextView dateOfCommencementTextView = dialog.findViewById(R.id.dialogDateOfCommencement);
        TextView premiumTextView = dialog.findViewById(R.id.dialogPremium);
        TextView dateOfBirthTextView = dialog.findViewById(R.id.dialogDateOfBirth);
        TextView planTermTextView = dialog.findViewById(R.id.dialogPlanTerm);
        TextView modeOfPaymentTextView = dialog.findViewById(R.id.dialogModeOfPayment);
        TextView nextDueDateTextView = dialog.findViewById(R.id.dialogNextDueDate);
        Button editDetailsButton = dialog.findViewById(R.id.editButton);
        Button button2 = dialog.findViewById(R.id.button2);
        Button closeButton = dialog.findViewById(R.id.closeButton);


        nameTextView.setText(customersList.get(position).getName());
        policyNoTextView.setText(String.valueOf(customersList.get(position).getPolicyNo()));
        dateOfCommencementTextView.setText(customersList.get(position).getDateOfCommencement());
        premiumTextView.setText(customersList.get(position).getPremium());
        dateOfBirthTextView.setText(customersList.get(position).getDateOfBirth());
        planTermTextView.setText(customersList.get(position).getPlanTerm());
        modeOfPaymentTextView.setText(customersList.get(position).getModeOfPayment());
        nextDueDateTextView.setText(customersList.get(position).getNextDueDate());

        // Set up button click listeners
        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
                showCustomerEditPopUpDialog(position);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 2 clicked!", Toast.LENGTH_SHORT).show();
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



    private void showCustomerEditPopUpDialog(int position) {
        // Create a new editCustomerDetailsDialog
        editCustomerDetailsDialog = new Dialog(this);
        editCustomerDetailsDialog.setContentView(R.layout.customers_edit_popupdialog);
        //editCustomerDetailsDialog.setCancelable(true); // Allows dismissing by tapping outside
        editCustomerDetailsDialog.setCancelable(false);
        editCustomerDetailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the editCustomerDetailsDialog
        TextView title = editCustomerDetailsDialog.findViewById(R.id.dialogTitle);
        EditText nameEditText = editCustomerDetailsDialog.findViewById(R.id.dialogNameEditText);
        EditText policyNoEditText = editCustomerDetailsDialog.findViewById(R.id.dialogPolicyNoEditText);
        EditText dateOfCommencementEditText = editCustomerDetailsDialog.findViewById(R.id.dialogDateOfCommencementEditText);
        EditText premiumEditText = editCustomerDetailsDialog.findViewById(R.id.dialogPremiumEditText);
        EditText dateOfBirthEditText = editCustomerDetailsDialog.findViewById(R.id.dialogDateOfBirthEditText);
        EditText planTermEditText = editCustomerDetailsDialog.findViewById(R.id.dialogPlanTermEditText);
        EditText modeOfPaymentEditText = editCustomerDetailsDialog.findViewById(R.id.dialogModeOfPaymentEditText);
        EditText nextDueDateEditText = editCustomerDetailsDialog.findViewById(R.id.dialogNextDueDateEditText);
        ImageView dateOfCommencementCalenderImageView = editCustomerDetailsDialog.findViewById(R.id.dateOfCommencementCalenderImageView);
        ImageView dateOfBirthCalenderImageView = editCustomerDetailsDialog.findViewById(R.id.dateOfBirthCalenderImageView);
        ImageView nextDueDateCalenderImageView = editCustomerDetailsDialog.findViewById(R.id.nextDueDateCalenderImageView);

        //Button editDetailsButton = editCustomerDetailsDialog.findViewById(R.id.editButton);
        Button cancelButton = editCustomerDetailsDialog.findViewById(R.id.cancelButton);
        Button button2 = editCustomerDetailsDialog.findViewById(R.id.button2);
        Button saveButton = editCustomerDetailsDialog.findViewById(R.id.saveButton);

        ProgressBar progressBarEditPopup = editCustomerDetailsDialog.findViewById(R.id.progressBarEditPopup);
        View disabledPopupView = editCustomerDetailsDialog.findViewById(R.id.disabledPopupView);


        nameEditText.setText(customersList.get(position).getName());
        policyNoEditText.setText(String.valueOf(customersList.get(position).getPolicyNo()));
        dateOfCommencementEditText.setText(customersList.get(position).getDateOfCommencement());
        premiumEditText.setText(customersList.get(position).getPremium());
        dateOfBirthEditText.setText(customersList.get(position).getDateOfBirth());
        planTermEditText.setText(customersList.get(position).getPlanTerm());
        modeOfPaymentEditText.setText(customersList.get(position).getModeOfPayment());
        nextDueDateEditText.setText(customersList.get(position).getNextDueDate());

        dateOfCommencementCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dateOfCommencementEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        dateOfBirthCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dateOfBirthEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        nextDueDateCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    nextDueDateEditText.setText(date);
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
                showEditCustomerCancelPopUpDialog();
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
                editCustomerFirebase(new CustomerClass(Integer.parseInt(policyNoEditText.getText().toString()), nameEditText.getText().toString(), dateOfCommencementEditText.getText().toString(), premiumEditText.getText().toString(), dateOfBirthEditText.getText().toString(), planTermEditText.getText().toString(), modeOfPaymentEditText.getText().toString(), nextDueDateEditText.getText().toString()));

                progressBarEditPopup.setVisibility(View.GONE);
                disabledPopupView.setVisibility(View.GONE);
                editCustomerDetailsDialog.dismiss();

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

        editCustomerDetailsDialog.show();
    }



    private void showEditCustomerCancelPopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customers_editcancel_popupdialog);
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
                editCustomerDetailsDialog.cancel();
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







    private void showNewCustomerPopUpDialog() {
        // Create a new newCustomerDetailsDialog
        newCustomerDetailsDialog = new Dialog(this);
        newCustomerDetailsDialog.setContentView(R.layout.customers_new_popupdialog);
        //newCustomerDetailsDialog.setCancelable(true); // Allows dismissing by tapping outside
        newCustomerDetailsDialog.setCancelable(false);
        newCustomerDetailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the newCustomerDetailsDialog
        TextView title = newCustomerDetailsDialog.findViewById(R.id.dialogTitle);
        EditText nameEditText = newCustomerDetailsDialog.findViewById(R.id.dialogNameEditText);
        EditText policyNoEditText = newCustomerDetailsDialog.findViewById(R.id.dialogPolicyNoEditText);
        EditText dateOfCommencementEditText = newCustomerDetailsDialog.findViewById(R.id.dialogDateOfCommencementEditText);
        EditText premiumEditText = newCustomerDetailsDialog.findViewById(R.id.dialogPremiumEditText);
        EditText dateOfBirthEditText = newCustomerDetailsDialog.findViewById(R.id.dialogDateOfBirthEditText);
        EditText planTermEditText = newCustomerDetailsDialog.findViewById(R.id.dialogPlanTermEditText);
        EditText modeOfPaymentEditText = newCustomerDetailsDialog.findViewById(R.id.dialogModeOfPaymentEditText);
        EditText nextDueDateEditText = newCustomerDetailsDialog.findViewById(R.id.dialogNextDueDateEditText);
        ImageView dateOfCommencementCalenderImageView = newCustomerDetailsDialog.findViewById(R.id.dateOfCommencementCalenderImageView);
        ImageView dateOfBirthCalenderImageView = newCustomerDetailsDialog.findViewById(R.id.dateOfBirthCalenderImageView);
        ImageView nextDueDateCalenderImageView = newCustomerDetailsDialog.findViewById(R.id.nextDueDateCalenderImageView);

        //Button editDetailsButton = newCustomerDetailsDialog.findViewById(R.id.editButton);
        Button cancelButton = newCustomerDetailsDialog.findViewById(R.id.cancelButton);
        Button button2 = newCustomerDetailsDialog.findViewById(R.id.button2);
        Button saveButton = newCustomerDetailsDialog.findViewById(R.id.saveButton);

        ProgressBar progressBarNewPopup = newCustomerDetailsDialog.findViewById(R.id.progressBarNewPopup);
        View disabledPopupView = newCustomerDetailsDialog.findViewById(R.id.disabledPopupView);


        dateOfCommencementCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dateOfCommencementEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        dateOfBirthCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dateOfBirthEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        nextDueDateCalenderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomersActivity.this, (v, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    nextDueDateEditText.setText(date);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        // Set up button click listeners
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
                //newCustomerDetailsDialog.cancel();
                showNewCustomerCancelPopUpDialog();
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
                addNewCustomerFirebase(new CustomerClass(Integer.parseInt(policyNoEditText.getText().toString()), nameEditText.getText().toString(), dateOfCommencementEditText.getText().toString(), premiumEditText.getText().toString(), dateOfBirthEditText.getText().toString(), planTermEditText.getText().toString(), modeOfPaymentEditText.getText().toString(), nextDueDateEditText.getText().toString()));

                progressBarNewPopup.setVisibility(View.GONE);
                disabledPopupView.setVisibility(View.GONE);
                newCustomerDetailsDialog.dismiss();

                /*progressBarEditPopup.setVisibility(View.VISIBLE);
                disabledPopupView.setVisibility(View.VISIBLE);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        progressBarEditPopup.setVisibility(View.GONE);
                        disabledPopupView.setVisibility(View.GONE);
                        newCustomerDetailsDialog.dismiss(); // Close the newCustomerDetailsDialog
                    }
                }, 5000);*/

            }
        });

        newCustomerDetailsDialog.show();
    }


    private void showNewCustomerCancelPopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customers_newcancel_popupdialog);
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
                newCustomerDetailsDialog.cancel();
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


    private void editCustomerFirebase(CustomerClass customer){
        // TODO
    }

    private void addNewCustomerFirebase(CustomerClass customer){
        // TODO
    }

    private void fetchCustomersList(){
        // TODO
        List<CustomerClass> newList = new ArrayList<>();

        newList.add(new CustomerClass(12345, "Mr K", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "8/10/2026"));
        newList.add(new CustomerClass(123456, "Mr deK", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "18/10/2026"));
        newList.add(new CustomerClass(123457, "Mr Kde", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "28/10/2026"));
        newList.add(new CustomerClass(777777, "Popatrao", "20/01/2020", "50000.00", "20/10/1700", "936-10", "M/Y", "28/10/2026"));
        newList.add(new CustomerClass(777, "Amitabh Bachchan", "27/05/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "20/10/2026"));
        newList.add(new CustomerClass(1001001, "Soham", "27/09/2020", "500000.00", "20/10/1700", "936-10", "M/Y", "8/10/2025"));

        customersList.addAll(newList);
        adapter.notifyDataSetChanged();

    }

}