package com.example.acer.glucosemanagement.Databases;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PillDao {

    @Insert
    void insert(Pill pill);

    @Query("SELECT * FROM pill_table")
    LiveData<List<Pill>> getAllPill();

    @Delete
    void delete(Pill pill);

    @Query("SELECT * FROM pill_table WHERE userId = :id")
    Pill getPill(int id);

}
