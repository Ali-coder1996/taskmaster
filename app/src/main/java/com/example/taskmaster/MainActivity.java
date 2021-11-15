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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
        Button sport = findViewById(R.id.sport);
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sport= new Intent(MainActivity.this,TaskDetail.class);
                sport.putExtra("title","sport");
                startActivity(sport);
            }
        });
        Button read = findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent read= new Intent(MainActivity.this,TaskDetail.class);
                read.putExtra("title","read");
                startActivity(read);
            }
        });
        Button sleep = findViewById(R.id.sleep);
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sleep= new Intent(MainActivity.this,TaskDetail.class);
                sleep.putExtra("title","sleep");
                startActivity(sleep);
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