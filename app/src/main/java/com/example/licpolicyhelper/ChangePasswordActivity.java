package com.example.licpolicyhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPWDEditText, newPWDEditText;
    Button submitButton;

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


        oldPWDEditText = findViewById(R.id.oldPWDEditText);
        newPWDEditText = findViewById(R.id.newPWDEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "ERRORRR!!!!");
                String oldPasswordHash = LoginActivity.getOldPasswordHash(username);
                //if(SecurityClass.comparePWDs(oldPWDEditText.getText().toString(), oldPasswordHash)){
                    setNewPassword(newPWDEditText.getText().toString());
                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                //}
                //else{
                    //Toast.makeText(ChangePasswordActivity.this, "Wrong Old Password!", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }


    private void setNewPassword(String newPWD){
        // TODO
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "ERRORRR!!!!");

        String newSecureToStorePassword = SecurityClass.encrypt_main_Password(newPWD); // SAVE THIS TO FIREBASE
    }
}