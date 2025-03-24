package com.example.licpolicyhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BirthdayTodayListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<BirthdayDetailsClass> todaysBirthdayList;
    BirthdayTodayRecyclerViewAdapter adapter;

    public static FirebaseDatabase firebaseDatabase;

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


        firebaseDatabase = FirebaseDatabase.getInstance();


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
        /*List<BirthdayDetailsClass> allBirthdaysList = new ArrayList<>(getAllBirthdays());

        //Get today's birthdays only
        for(int i=0; i<allBirthdaysList.size(); i++){
            if(isSameDayAsToday(allBirthdaysList.get(i).getBirthDate())){
                todaysBirthdayList.add(allBirthdaysList.get(i));
            }
        }*/



        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "ERRORRR!!!!");

        List<BirthdayDetailsClass> allList = new ArrayList<>();

        DatabaseReference databaseReference;
        databaseReference = firebaseDatabase.getReference("users").child(username).child("birthdays");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String s = dataSnapshot.getKey();
                    allList.add(dataSnapshot.getValue(BirthdayDetailsClass.class));
                    //Toast.makeText(BirthdayListActivity.this, "Retrieved : " + s, Toast.LENGTH_SHORT).show();
                    Log.d("QWER", "Retrieved : " + s);
                }
                todaysBirthdayList.clear();
                for(int i=0; i<allList.size(); i++){
                    if(isSameDayAsToday(allList.get(i).getBirthDate())){
                        todaysBirthdayList.add(allList.get(i));
                    }
                }
                //birthdayList.clear();
                //birthdayList.addAll(newList);
                //sortBirthdays(birthdayList, "");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BirthdayTodayListActivity.this, "Error retrieving Birthdays - check your internet connection or contact the developer", Toast.LENGTH_SHORT).show();
            }
        });



    }

    /*private List<BirthdayDetailsClass> getAllBirthdays() {
        // TODO

    }*/


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