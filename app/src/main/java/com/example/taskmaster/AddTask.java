package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText mtask = findViewById(R.id.mtask);
        EditText dtask = findViewById(R.id.dtask);
        EditText stask = findViewById(R.id.stask);

        Button button = findViewById(R.id.fromAddTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "submitted!", Toast.LENGTH_LONG).show();
                AppDatabase db = AppDatabase.getDataBaseObj(view.getContext());
                TaskDao taskDao = db.taskDao();
                taskDao.insertOne(new Task(mtask.getText().toString(),dtask.getText().toString(),stask.getText().toString()));
                mtask.setText("");
                dtask.setText("");
                stask.setText("");
                finish();
            }
        });

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