package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button AddTask = findViewById(R.id.AddTask);
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"I'm click",Toast.LENGTH_LONG).show();
                Intent AddTask= new Intent(MainActivity.this,AddTask.class);

                startActivity(AddTask);
            }
        });
        Button AllTasks = findViewById(R.id.AllTaskes);
        AllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"I'm click",Toast.LENGTH_LONG).show();
                Intent AllTasks= new Intent(MainActivity.this,AllTaskes.class);
                startActivity(AllTasks);
            }
        });
    }
}