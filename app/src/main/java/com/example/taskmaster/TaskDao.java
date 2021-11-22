package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAllTask();

    @Query("SELECT * FROM task WHERE uid=:id")
    Task getById(int id);

    @Insert
    void insertOne(Task tasks);

    @Delete
    void delete(Task tasks);
}
