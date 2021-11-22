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

        getTask();

    }
    public void getTask(){

int id =getIntent().getIntExtra("id",0);

    AppDatabase db = AppDatabase.getDataBaseObj(this);
    TaskDao taskDao = db.taskDao();
    Task task=taskDao.getById(id);

    TextView textView =findViewById(R.id.detail);
        textView.setText(task.title);

    TextView md =findViewById(R.id.md);
        md.setText(task.body);

    TextView sd =findViewById(R.id.sd);
        sd.setText(task.state);

    }

}