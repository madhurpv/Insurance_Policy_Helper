package com.example.licpolicyhelper;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BirthdayTodayListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<BirthdayDetailsClass> todaysBirthdayList;
    BirthdayTodayRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_birthday_today_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        todaysBirthdayList = new ArrayList<>();
        fillTodaysBirthdayList();

        recyclerView = findViewById(R.id.birthdayTodayListRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BirthdayTodayRecyclerViewAdapter(todaysBirthdayList);
        /*adapter.setOnClickListener((position, birthdate) -> {
            // Handle item click here
            //Toast.makeText(getApplicationContext(), "Clicked: " + birthdate.getName(), Toast.LENGTH_SHORT).show();
            showBirthdayEditPopUpDialog(position);
            //showCustomerPopUpDialog(position);
        });*/
        recyclerView.setAdapter(adapter);


    }


    public void fillTodaysBirthdayList(){
        //GET_ALL_BIRTHDAYS
        List<BirthdayDetailsClass> allBirthdaysList = new ArrayList<>();
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K1", "9921350816", "10/08/1975"));
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K2", "9921350816", "18/03/1978"));
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K3", "9921350816", "20/03/1977"));
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K4", "9921350816", "19/03/1973"));
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K5", "9921350816", "18/03/1970"));
        allBirthdaysList.add(new BirthdayDetailsClass("Ramesh K6", "9921350816", "19/03/2025"));

        for(int i=0; i<allBirthdaysList.size(); i++){
            if(isSameDayAsToday(allBirthdaysList.get(i).getBirthDate())){
                todaysBirthdayList.add(allBirthdaysList.get(i));
            }
        }


    }


    public boolean isSameDayAsToday(String inputDate) {
        // Define the date format (DD/MM/YYYY)
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Get today's date
        SimpleDateFormat todayFormat = new SimpleDateFormat("dd/MM");

        try {
            // Parse the input date
            Date parsedDate = inputDateFormat.parse(inputDate);

            // Format the input date to only include day and month (ignores the year)
            String formattedInputDate = todayFormat.format(parsedDate);

            // Get today's date formatted as dd/MM
            String formattedTodayDate = todayFormat.format(new Date());

            // Compare the formatted input date with today's date
            return formattedInputDate.equals(formattedTodayDate);

        } catch (ParseException e) {
            // Handle the exception in case of invalid date format
            e.printStackTrace();
        }
        return false;
    }
}