package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.predicate.QueryPredicate;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        System.out.println(getIntent().getStringExtra("data"));
        getTask();

    }

    public void getTask() {


        TextView textView = findViewById(R.id.detail);
        textView.setText(getIntent().getStringExtra("title"));

        TextView md = findViewById(R.id.md);
        md.setText(getIntent().getStringExtra("body"));

        TextView sd = findViewById(R.id.sd);
        sd.setText(getIntent().getStringExtra("state"));

    }

}