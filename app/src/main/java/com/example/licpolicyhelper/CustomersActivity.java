package com.example.licpolicyhelper;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
        List<CustomerClass> examList = new ArrayList<>();

        examList.add(new CustomerClass("12345", "Mr K", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "8/10/2026"));
        examList.add(new CustomerClass("123456", "Mr deK", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "18/10/2026"));
        examList.add(new CustomerClass("123457", "Mr Kde", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "28/10/2026"));

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        CustomerRecyclerViewAdapter adapter = new CustomerRecyclerViewAdapter(examList);
        adapter.setOnClickListener((position, customer) -> {
            // Handle item click here
            Toast.makeText(getApplicationContext(), "Clicked: " + customer.getName(), Toast.LENGTH_SHORT).show();
            showPopUpDialog();
        });
        recyclerView.setAdapter(adapter);


    }



    private void showPopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customers_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        TextView message = dialog.findViewById(R.id.dialogName);
        Button button1 = dialog.findViewById(R.id.button1);
        Button button2 = dialog.findViewById(R.id.button2);
        Button closeButton = dialog.findViewById(R.id.closeButton);

        // Set up button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button 1 clicked!", Toast.LENGTH_SHORT).show();
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