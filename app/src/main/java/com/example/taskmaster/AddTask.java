package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity {
    public   Task t;
    private File uploadFile;
    public static final int REQUEST_FOR_FILE = 999;
    EditText mtask;
    String fileName;
    Uri path;
    InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

         mtask = findViewById(R.id.mtask);
        EditText dtask = findViewById(R.id.dtask);
        EditText stask = findViewById(R.id.stask);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddTask.this);
        Set<String> allTeams= sharedPreferences.getStringSet("teams",new HashSet<String>());

        AutoCompleteTextView menuView = findViewById(R.id.menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, new ArrayList<>(allTeams));
        menuView.setAdapter(adapter);
        menuView.setInputType(InputType.TYPE_NULL);

        Button button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "submitted!", Toast.LENGTH_LONG).show();
                if (fileName != null) {
                    Amplify.Storage.uploadInputStream(
                            fileName,
                            inputStream,
                            results -> Log.i("MyAmplifyApp", "Successfully uploaded: " + results.getKey()),
                            storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
                    );
                }
                t =Task.builder().teamName(menuView.getText().toString()).title(mtask.getText().toString()).body(dtask.getText().toString()).state(stask.getText().toString()).file(fileName).build();
                /*---------------------------------------------------*/

                Amplify.API.mutate(
                        ModelMutation.create(t),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                finish();
            }
        });
        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFileFromDevice();
            }
        });

    }
    private void getFileFromDevice() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a File");
        startActivityForResult(chooseFile, REQUEST_FOR_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        assert data !=null;
//        path = data.getData();
//        File file =new File(path.getPath());
//        fileName =file.getName();
        try {
             inputStream = getContentResolver().openInputStream(data.getData());
            File files = new File(data.getData().getPath());
            fileName = files.getName();
            Log.i ("MyAmplifyApp", fileName);
            Toast.makeText(getApplicationContext(),"Added the file Successfully",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}