package com.example.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public String title;
    public String body;
    public String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
