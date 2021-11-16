package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        List<Task> allTask = new ArrayList<>();
        allTask.add(new Task("sleep","I don't sleep well yesterday","new"));
        allTask.add(new Task("Gym","I wanna go to practice exercise to change my mood ","assign"));
        allTask.add(new Task("read","I wanna read about the lecture tomorrow","in progress"));
        allTask.add(new Task("re-submitted lab","just check if I need to re-submit any assignment ","complete"));


        RecyclerView recyclerView =findViewById(R.id.allTaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TasksAdapter(this,allTask));

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
//        Button setting = findViewById(R.id.setting);
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent setting= new Intent(MainActivity.this,SettingsPage.class);
//                startActivity(setting);
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("userName","username");
        TextView textView = findViewById(R.id.name);
        textView.setText(name+"'s " +"Tasks");
    }
}