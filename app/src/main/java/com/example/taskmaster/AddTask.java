package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText mtask = findViewById(R.id.mtask);
        EditText dtask = findViewById(R.id.dtask);
        EditText stask = findViewById(R.id.stask);

        Button button = findViewById(R.id.fromAddTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "submitted!", Toast.LENGTH_LONG).show();

                Team teamA= Team.builder().teamName("a").build(); //team from aws
                Amplify.API.mutate(
                        ModelMutation.create(teamA),
                        response -> Log.i("MyAmplifyApp", "Added team with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                Team teamB= Team.builder().teamName("b").build(); //team from aws
                Amplify.API.mutate(
                        ModelMutation.create(teamB),
                        response -> Log.i("MyAmplifyApp", "Added team with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );

                Team teamC= Team.builder().teamName("c").build(); //team from aws
                Amplify.API.mutate(
                        ModelMutation.create(teamC),
                        response -> Log.i("MyAmplifyApp", "Added team with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );

                /*---------------------------------------------------*/
                RadioGroup teams=findViewById(R.id.teams);
                int teamNumber=teams.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(teamNumber);

                System.out.println("teamssssssssssssssssssssss"+teams.getCheckedRadioButtonId());
                Task task;
                if(radioButton.getText()=="A"){
                     task = Task.builder().team(teamA).title(mtask.getText().toString()).body(dtask.getText().toString()).state(stask.getText().toString()).build();

                }else if(radioButton.getText()=="B"){
                     task = Task.builder().team(teamB).title(mtask.getText().toString()).body(dtask.getText().toString()).state(stask.getText().toString()).build();

                }else {
                     task = Task.builder().team(teamC).title(mtask.getText().toString()).body(dtask.getText().toString()).state(stask.getText().toString()).build();
                }
                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                finish();
            }
        });

    }


}