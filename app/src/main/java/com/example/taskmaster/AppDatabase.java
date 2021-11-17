package com.example.taskmaster;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    private static AppDatabase db =null;
    public static AppDatabase getDataBaseObj(Context context){
        if (db==null){
            db = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "taskmaster").allowMainThreadQueries().build();
        }
        return db;
    }
}
