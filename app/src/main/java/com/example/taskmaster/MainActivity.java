package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public Set<String> teamNameList=new HashSet<>();
    private Handler handleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home Page");

        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }


        TextView loginUser = findViewById(R.id.loginUser);

        Button singup = findViewById(R.id.singup);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signInWithWebUI(
                        MainActivity.this,
                        result -> Log.i("AuthQuickStart", result.toString()),
                        error -> Log.e("AuthQuickStart", error.toString())
//                        String user = loginUser.setText();

                );
                Toast.makeText(MainActivity.this,"a",Toast.LENGTH_LONG).show();
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                Toast.makeText(MainActivity.this,"a",Toast.LENGTH_LONG).show();
            }
        });



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


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String teamName = sharedPreferences.getString("teamName","team name");
        String name = sharedPreferences.getString("userName","username");
        TextView textView = findViewById(R.id.name);
        textView.setText(name+"'s " +"Tasks");

        RecyclerView recyclerView = findViewById(R.id.allTaskRecyclerView);
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        List<Task> allTask = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task task : response.getData()) {
                        if (task.getTeamName().equals(teamName)) {
                            allTask.add(task);
                        }
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("TaskMaster", error.toString(), error)
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TasksAdapter(this, allTask));

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        Log.i("teamName",team.getTeamName());
                        teamNameList.add(team.getTeamName());
                    }
                    SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    sharedPreferences1.edit().putStringSet("teams",teamNameList).apply();
                },

                error -> Log.e("TaskMaster", error.toString(), error)
        );
        Button singup = findViewById(R.id.singup);
        Button logout = findViewById(R.id.logout);
        TextView loginUser = findViewById(R.id.loginUser);
        logout.setVisibility(View.INVISIBLE);

        Amplify.Auth.fetchAuthSession(
                result ->{
                    if(result.isSignedIn()){
                        logout.setVisibility(View.VISIBLE);
                        singup.setVisibility(View.INVISIBLE);
                        loginUser.setText(Amplify.Auth.getCurrentUser().getUsername());
                    }else{
                        singup.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.INVISIBLE);

                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
    }


    }




