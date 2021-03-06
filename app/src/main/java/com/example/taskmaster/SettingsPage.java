package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);


        Button button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText teamNameField=findViewById(R.id.teamName);
                TextInputEditText textInputEditText = findViewById(R.id.user);

                String userName = textInputEditText.getText().toString();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsPage.this);


                sharedPreferences.edit().putString("teamName",teamNameField.getText().toString()).apply();
                sharedPreferences.edit().putString("userName",userName).apply();

                finish();
            }
        });
    }

}