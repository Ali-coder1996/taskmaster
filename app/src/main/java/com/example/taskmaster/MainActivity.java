package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home Page");
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Button AddTask = findViewById(R.id.AddTask);
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddTask = new Intent(MainActivity.this, AddTask.class);
                startActivity(AddTask);
            }
        });
        Button AllTasks = findViewById(R.id.AllTaskes);
        AllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AllTasks = new Intent(MainActivity.this, AllTaskes.class);
                startActivity(AllTasks);
            }
        });
//        FloatingActionButton floatingActionButton = findViewById(R.id.add);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view,"Submmited",Snackbar.LENGTH_LONG).setAction("close", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent AddTask= new Intent(MainActivity.this,AddTask.class);
//                        startActivity(AddTask);
//                    }
//                }).show();
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my:
                Intent setting = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(setting);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleRViewShow();
//
//        AppDatabase db = AppDatabase.getDataBaseObj(this);
//        TaskDao taskDao = db.taskDao();
//        allTask=taskDao.getAllTask();

    }
    private synchronized void handleRViewShow(){
        List<Task> allTask = new ArrayList<>();

        try {
            Amplify.API.query(ModelQuery.list(Task.class),
                    response -> {
                        for (Task task : response.getData()){
                            allTask.add(task);
                            Log.i("ali",task.getTitle());
                        }
                    },
                    error -> Log.e("error",error.toString())
                    );
            try {
                Thread.sleep(1500);
                RecyclerView recyclerView = findViewById(R.id.allTaskRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new TasksAdapter(this, allTask));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("userName","username");
        TextView textView = findViewById(R.id.name);
        textView.setText(name+"'s " +"Tasks");
    }

    }


//
//        RecyclerView recyclerView =findViewById(R.id.allTaskRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setAdapter(new TasksAdapter(this,allTask));



