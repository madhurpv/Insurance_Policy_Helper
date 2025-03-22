package com.example.licpolicyhelper;

import android.app.Dialog;
import android.app.Person;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button submitButton;
    TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        Long loggedIn = sharedPreferences.getLong("login", -1L);
        /*if(loggedIn!=-1){
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(myIntent);
            finish();
        }*/


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFirebase();
                /*if(checkUsernamePasswordPairIsCorrect(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("login", System.currentTimeMillis());
                    editor.putString("username", usernameEditText.getText().toString());
                    editor.putString("password", passwordEditText.getText().toString());
                    editor.apply();

                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(myIntent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Username/Password incorrect!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
                finish();*/
                showMessage1PopUpDialog();
            }
        });



    }


    private boolean checkUsernamePasswordPairIsCorrect(String username, String password){
        String getOrigPWDHash = getOldPasswordHash(username);
        if(SecurityClass.comparePWDs(password, getOrigPWDHash)){
            return true;
        }
        return true; // TODO : False when implemented using Firebase
    }

    public static String getOldPasswordHash(String username){
        // TODO
        return "";
    }



    private void showMessage1PopUpDialog() {
        // Create a new dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_forgotpassword_popupdialog);
        //dialog.setCancelable(true); // Allows dismissing by tapping outside
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Get references to views in the dialog
        TextView title = dialog.findViewById(R.id.dialogTitle);
        Button okButton = dialog.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        dialog.show();
    }



    private void getDataFirebase(){
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("QWER", "Getting data!");
                Log.d("QWER", "snapshot.getChildren() - " + snapshot.getChildren().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String s = dataSnapshot.getKey();
                    Toast.makeText(LoginActivity.this, "Retrieved : " + s, Toast.LENGTH_SHORT).show();
                    Log.d("QWER", "Retrieved : " + s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Failed to retrieve data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}