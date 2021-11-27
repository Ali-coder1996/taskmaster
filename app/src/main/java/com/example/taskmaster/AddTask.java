package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity {
    public   Task t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText mtask = findViewById(R.id.mtask);
        EditText dtask = findViewById(R.id.dtask);
        EditText stask = findViewById(R.id.stask);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddTask.this);
        Set<String> allTeams= sharedPreferences.getStringSet("teams",new HashSet<String>());

        AutoCompleteTextView menuView = findViewById(R.id.menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, new ArrayList<>(allTeams));
        menuView.setAdapter(adapter);
        menuView.setInputType(InputType.TYPE_NULL);

        Button button = findViewById(R.id.fromAddTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "submitted!", Toast.LENGTH_LONG).show();
                t =Task.builder().teamName(menuView.getText().toString()).title(mtask.getText().toString()).body(dtask.getText().toString()).state(stask.getText().toString()).build();
                /*---------------------------------------------------*/
                Amplify.API.mutate(
                        ModelMutation.create(t),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                finish();
            }
        });

    }


}