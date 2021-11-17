package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        getTask();


    }
    public void getTask(){

            TextView textView =findViewById(R.id.detail);
            String task = getIntent().getExtras().get("title").toString();
            textView.setText(task);

            TextView md =findViewById(R.id.md);
            String des = getIntent().getExtras().get("body").toString();
            md.setText(des);

            TextView sd =findViewById(R.id.sd);
            String sta = getIntent().getExtras().get("state").toString();
            sd.setText(sta);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}