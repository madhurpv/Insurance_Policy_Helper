package com.example.licpolicyhelper;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CustomerClass> customersList;

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

        customersList.add(new CustomerClass("12345", "Mr K", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "8/10/2026"));
        customersList.add(new CustomerClass("123456", "Mr deK", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "18/10/2026"));
        customersList.add(new CustomerClass("123457", "Mr Kde", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "28/10/2026"));

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        CustomerRecyclerViewAdapter adapter = new CustomerRecyclerViewAdapter(customersList);
        adapter.setOnClickListener((position, customer) -> {
            // Handle item click here
            Toast.makeText(getApplicationContext(), "Clicked: " + customer.getName(), Toast.LENGTH_SHORT).show();
            showCustomerPopUpDialog(position);
        });
        recyclerView.setAdapter(adapter);


    }



    private void showCustomerPopUpDialog(int position) {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customers_popupdialog);
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
        policyNoTextView.setText(customersList.get(position).getPolicyNo());
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
                Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
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
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customers_edit_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        EditText nameEditText = dialog.findViewById(R.id.dialogNameEditText);
        EditText policyNoEditText = dialog.findViewById(R.id.dialogPolicyNoEditText);
        EditText dateOfCommencementEditText = dialog.findViewById(R.id.dialogDateOfCommencementEditText);
        EditText premiumEditText = dialog.findViewById(R.id.dialogPremiumEditText);
        EditText dateOfBirthEditText = dialog.findViewById(R.id.dialogDateOfBirthEditText);
        EditText planTermEditText = dialog.findViewById(R.id.dialogPlanTermEditText);
        EditText modeOfPaymentEditText = dialog.findViewById(R.id.dialogModeOfPaymentEditText);
        EditText nextDueDateEditText = dialog.findViewById(R.id.dialogNextDueDateEditText);
        Button editDetailsButton = dialog.findViewById(R.id.editButton);
        Button button2 = dialog.findViewById(R.id.button2);
        Button closeButton = dialog.findViewById(R.id.closeButton);


        nameEditText.setText(customersList.get(position).getName());
        policyNoEditText.setText(customersList.get(position).getPolicyNo());
        dateOfCommencementEditText.setText(customersList.get(position).getDateOfCommencement());
        premiumEditText.setText(customersList.get(position).getPremium());
        dateOfBirthEditText.setText(customersList.get(position).getDateOfBirth());
        planTermEditText.setText(customersList.get(position).getPlanTerm());
        modeOfPaymentEditText.setText(customersList.get(position).getModeOfPayment());
        nextDueDateEditText.setText(customersList.get(position).getNextDueDate());

        // Set up button click listeners
        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ediDetailsButton clicked!", Toast.LENGTH_SHORT).show();
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
}