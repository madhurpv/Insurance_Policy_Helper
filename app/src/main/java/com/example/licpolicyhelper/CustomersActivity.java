package com.example.licpolicyhelper;

import android.os.Bundle;

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
        examList.add(new CustomerClass("123456", "Mr deK", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "8/10/2026"));
        examList.add(new CustomerClass("123457", "Mr Kde", "27/01/2020", "5000.00", "20/10/1700", "936-10", "M/Y", "8/10/2026"));

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        CustomerRecyclerViewAdapter adapter = new CustomerRecyclerViewAdapter(examList);
        recyclerView.setAdapter(adapter);
    }
}