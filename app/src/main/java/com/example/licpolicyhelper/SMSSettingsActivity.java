package com.example.licpolicyhelper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSSettingsActivity extends AppCompatActivity {


    EditText messageEditText;
    Button saveButton;

    String oldSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smssettings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        oldSMS = sharedPreferences.getString("phoneSMS", "Sample SMS");

        saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
        saveButton.setBackgroundColor(Color.parseColor("#ADADAD"));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phoneSMS", messageEditText.getText().toString());
                editor.apply();
            }
        });

        messageEditText = findViewById(R.id.messageEditText);
        messageEditText.setText(oldSMS);
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(messageEditText.getText().toString().equals(oldSMS)){
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.parseColor("#ADADAD"));
                }
                else{
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(Color.parseColor("#0BA2D0"));
                }
            }
        });

    }

}