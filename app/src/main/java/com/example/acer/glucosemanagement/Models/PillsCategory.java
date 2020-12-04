package com.example.acer.glucosemanagement.Models;

public class PillsCategory {

    String pillsEntry;
    Integer pillsId;

    public PillsCategory(Integer pillsId, String pillsEntry) {
        this.pillsEntry = pillsEntry;
        this.pillsId = pillsId;
    }

    public String getPillsEntry() {
        return pillsEntry;
    }

    public Integer getPillsId() {
        return pillsId;
    }

    public void setPillsEntry(String pillsEntry) {
        this.pillsEntry = pillsEntry;
    }

    public void setPillsId(Integer pillsId) {
        this.pillsId = pillsId;
    }
}
