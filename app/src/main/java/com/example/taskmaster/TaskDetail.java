package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.predicate.QueryPredicate;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity {
    private Handler handleImageView;
    private String taskName;
    private File downloadedImage;
    private ImageView img;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        System.out.println(getIntent().getStringExtra("data"));
        getTask();
        key = getIntent().getStringExtra("file");
         img = findViewById(R.id.ali);
        handleImageView = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                setTaskImage();
                return false;
            }
        });
        getFileFromApi();

    }
    private void setTaskImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // down sizing image as it throws OutOfMemory  Exception for larger images
        Bitmap bitmap = BitmapFactory.decodeFile(downloadedImage.getAbsolutePath(), options);
        img.setImageBitmap(bitmap);
    }

    private void getFileFromApi() {
        Amplify.Storage.downloadFile(
                key ,
                new File(getApplicationContext().getFilesDir() + "test.jpg"),
                success -> {
                    Log.i("getFileFromApi: successfully   ----> " , success.toString());
                    downloadedImage = success.getFile();
                    handleImageView.sendEmptyMessage(1);
                },
                failure -> Log.i("getFileFromApi:  failed  ---> " , failure.toString())
        );
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