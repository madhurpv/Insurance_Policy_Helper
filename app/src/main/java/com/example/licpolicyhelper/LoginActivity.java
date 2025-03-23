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

    public static FirebaseDatabase firebaseDatabase;

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


        firebaseDatabase = FirebaseDatabase.getInstance();



        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        long loggedIn = sharedPreferences.getLong("login", -1L);
        if(loggedIn!=-1){
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(myIntent);
            finish();
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("QWER", "HASH -> " + SecurityClass.getSHA512Hash("123abc"));
                //Log.d("QWER", "Encrypt -> " + SecurityClass.encrypt_main_Password("123abc"));
                //getOldPasswordHash(usernameEditText.getText().toString());
                getDataFirebase();

                final String[] oldPasswordHash = {""};
                DatabaseReference databaseReference;
                databaseReference = firebaseDatabase.getReference("users").child(usernameEditText.getText().toString()).child("passwordHash");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("QWER", "Getting data!");
                        Log.d("QWER", "snapshot.getValue() - " + snapshot.getValue());
                        if(snapshot.getValue() == null){
                            Log.d("QWER", "Wrong username!");
                            Toast.makeText(LoginActivity.this, "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            oldPasswordHash[0] = snapshot.getValue().toString();
                            if(SecurityClass.comparePWDs(passwordEditText.getText().toString(), oldPasswordHash[0])){
                            //if(passwordEditText.getText().toString().equals(oldPasswordHash[0])){
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
                                Toast.makeText(LoginActivity.this, "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //Toast.makeText(LoginActivity.this, "Failed to retrieve data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Some error occurred - please try again or contact the developers!", Toast.LENGTH_SHORT).show();
                    }
                });




                //setDataFirebase();
                /*if(checkUsernamePasswordPairIsCorrect(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {

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


    /*private boolean checkUsernamePasswordPairIsCorrect(String username, String password){

        return true;
    }*/

    /*public static String getOldPasswordHash(String username){

        Log.d("QWER", "HASH = " + oldPasswordHash[0]);
        return oldPasswordHash[0];
    }*/



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
        DatabaseReference databaseReference;
        databaseReference = firebaseDatabase.getReference("usersw");
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

    private void setDataFirebase(){
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.child("nimalesoham").setValue("person")
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(LoginActivity.this, "Data added successfully!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(error ->
                        Toast.makeText(LoginActivity.this, "Failed to add data: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}