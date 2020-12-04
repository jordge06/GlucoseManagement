package com.example.acer.glucosemanagement.Databases;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LogDao {

    @Insert
    void insert(Log log);

    @Update
    void update(Log log);

    @Delete
    void delete(Log log);

    @Query("DELETE FROM log_table")
    void deleteAllLogs();

    @Query("UPDATE FROM log_table WHERE id = :userId")
    void updateById(int userId);

    @Query("SELECT * FROM log_table WHERE date = :sDate")
    List<Log> getByDate(String sDate);

    @Query("SELECT date FROM log_table GROUP BY date HAVING COUNT (*) >= 1 ORDER BY date DESC")
    List<String> getDateList();

    @Query("SELECT a1cLevel FROM log_table")
    List<String> getA1cLevel();

    @Query("SELECT glucoseLevel FROM log_table")
    List<String> getBloodSugar();

    @Query("SELECT systolic FROM log_table")
    List<String> getSystolicPressure();

    @Query("SELECT diastolic FROM log_table")
    List<String> getDiastolic();

}
