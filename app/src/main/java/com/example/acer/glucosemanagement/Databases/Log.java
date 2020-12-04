package com.example.acer.glucosemanagement.Databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_table")
public class Log {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String date;
    private String time;
    private String glucoseLevel;
    private String systolic;
    private String diastolic;
    private String a1cLevel;
    private String weight;
    private String pills;
    private String pillsAmount;
    private String event;
    private String notes;
    private String tags;

    public int getId() {
        return id;
    }

    public Log(String email, String date, String time, String glucoseLevel, String systolic, String diastolic,
               String a1cLevel, String weight, String pills, String pillsAmount, String event, String notes, String tags) {
        this.email = email;
        this.date = date;
        this.time = time;
        this.glucoseLevel = glucoseLevel;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.a1cLevel = a1cLevel;
        this.weight = weight;
        this.pills = pills;
        this.pillsAmount = pillsAmount;
        this.event = event;
        this.notes = notes;
        this.tags = tags;
    }

   public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getGlucoseLevel() {
        return glucoseLevel;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getA1cLevel() {
        return a1cLevel;
    }

    public String getWeight() {
        return weight;
    }

    public String getPills() {
        return pills;
    }

    public String getPillsAmount() {
        return pillsAmount;
    }

    public String getEvent() {
        return event;
    }

    public String getNotes() {
        return notes;
    }

    public String getTags() {
        return tags;
    }
}
