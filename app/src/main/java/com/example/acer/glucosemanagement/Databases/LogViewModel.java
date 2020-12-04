package com.example.acer.glucosemanagement.Databases;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LogViewModel extends AndroidViewModel {

    private LogRepository repository;

    public LogViewModel(@NonNull Application application) {
        super(application);
        repository = new LogRepository(application);
    }

    public List<Log> getLogByDate(String date) throws ExecutionException, InterruptedException {
        return repository.getLogByDate(date);
    }

    public List<String> getDateList() throws ExecutionException, InterruptedException {
        return repository.getDateList();
    }

    public List<String> getA1cLevel() throws ExecutionException, InterruptedException {
        return repository.getA1cLevel();
    }

    public List<String> getDiastolic() throws ExecutionException, InterruptedException {
        return repository.getDiastolic();
    }

    public List<String> getSystolic() throws ExecutionException, InterruptedException {
        return repository.getSystolic();
    }

    public List<String> getBloodSugar() throws ExecutionException, InterruptedException {
        return repository.getBloodSugar();
    }

    public void insert(Log log) {
        repository.insert(log);
    }

    public void update(Log log) {
        repository.update(log);
    }

    public void delete(Log log) { repository.delete(log); }

    public void deleteAllLogs() {
        repository.deleteAllNotes();
    }
}
