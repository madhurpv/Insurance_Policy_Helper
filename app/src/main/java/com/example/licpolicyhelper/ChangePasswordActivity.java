package com.example.licpolicyhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPWDEditText, newPWDEditText;
    Button submitButton;

    public static FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        firebaseDatabase = FirebaseDatabase.getInstance();


        oldPWDEditText = findViewById(R.id.oldPWDEditText);
        newPWDEditText = findViewById(R.id.newPWDEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "ERRORRR!!!!");
                //String oldPasswordHash = LoginActivity.getOldPasswordHash(username);
                //if(SecurityClass.comparePWDs(oldPWDEditText.getText().toString(), oldPasswordHash)){
                //    setNewPassword(newPWDEditText.getText().toString());
                //Toast.makeText(ChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                //    finish();
                //}
                //else{
                    //Toast.makeText(ChangePasswordActivity.this, "Wrong Old Password!", Toast.LENGTH_SHORT).show();
                //}



                final String[] oldPasswordHash = {""};
                DatabaseReference databaseReference;
                databaseReference = firebaseDatabase.getReference("users").child(username).child("passwordHash");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("QWER", "Getting data!");
                        Log.d("QWER", "snapshot.getValue() - " + snapshot.getValue());
                        if(snapshot.getValue() == null){
                            Log.d("QWER", "Wrong username!");
                            Toast.makeText(ChangePasswordActivity.this, "Error occurred - Login Username incorrect!!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            oldPasswordHash[0] = snapshot.getValue().toString();
                            if(SecurityClass.comparePWDs(oldPWDEditText.getText().toString(), oldPasswordHash[0])){
                                //if(passwordEditText.getText().toString().equals(oldPasswordHash[0])){

                                DatabaseReference saveDatabaseReference;
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                saveDatabaseReference = firebaseDatabase.getReference("users");
                                saveDatabaseReference.child(username).child("passwordHash").setValue(SecurityClass.getSHA512Hash(newPWDEditText.getText().toString()))
                                        .addOnSuccessListener(aVoid ->
                                                //Toast.makeText(LoginActivity.this, "Data added successfully!", Toast.LENGTH_SHORT).show()
                                                saveDatabaseReference.child(username).child("encryptedPassword").setValue(SecurityClass.encrypt_main_Password(newPWDEditText.getText().toString()))
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                editor.putString("password", newPWDEditText.getText().toString());
                                                                editor.apply();
                                                                Toast.makeText(ChangePasswordActivity.this, "Password Changed successfully!", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception error) {
                                                                Toast.makeText(ChangePasswordActivity.this, "Failed to add data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        })

                                        )
                                        .addOnFailureListener(error ->
                                                Toast.makeText(ChangePasswordActivity.this, "An error occured " + error.getMessage(), Toast.LENGTH_SHORT).show()
                                        );

                                /*Intent myIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                ChangePasswordActivity.this.startActivity(myIntent);
                                finish();*/
                            }
                            else{
                                Toast.makeText(ChangePasswordActivity.this, "Old Password incorrect!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //Toast.makeText(LoginActivity.this, "Failed to retrieve data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ChangePasswordActivity.this, "Some error occurred - please try again or contact the developers!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


    /*private void setNewPassword(String newPWD){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "ERRORRR!!!!");

        String newSecureToStorePassword = SecurityClass.encrypt_main_Password(newPWD); // SAVE THIS TO FIREBASE
    }*/
}