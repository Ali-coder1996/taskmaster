package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imageView =findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting= new Intent(MainActivity.this,SettingsPage.class);
                startActivity(setting);
            }
        });

        Button AddTask = findViewById(R.id.AddTask);
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddTask= new Intent(MainActivity.this,AddTask.class);
                startActivity(AddTask);
            }
        });
        Button AllTasks = findViewById(R.id.AllTaskes);
        AllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AllTasks= new Intent(MainActivity.this,AllTaskes.class);
                startActivity(AllTasks);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Task> allTask = new ArrayList<>();

        AppDatabase db = AppDatabase.getDataBaseObj(this);
        TaskDao taskDao = db.taskDao();
        allTask=taskDao.getAllTask();

        RecyclerView recyclerView =findViewById(R.id.allTaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TasksAdapter(this,allTask));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("userName","username");
        TextView textView = findViewById(R.id.name);
        textView.setText(name+"'s " +"Tasks");
    }
}