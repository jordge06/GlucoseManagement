package com.example.acer.glucosemanagement.Databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pill_table")
public class Pill {

    @PrimaryKey(autoGenerate = true)
    int id;
    String email;
    String userId;
    String pillsName;

    public Pill(String email, String userId, String pillsName) {
        this.email = email;
        this.userId = userId;
        this.pillsName = pillsName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPillsName() {
        return pillsName;
    }

    public void setPillsName(String pillsName) {
        this.pillsName = pillsName;
    }
}
