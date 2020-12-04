package com.example.acer.glucosemanagement.Databases;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LogRepository {

    private LogDao logDao;

    private PillDao pillDao;
    private LiveData<List<Pill>> allPill;

    public LogRepository(Application application) {
        LogDatabase logDatabase = LogDatabase.getInstance(application);
        logDao = logDatabase.logDao();
        pillDao = logDatabase.pillDao();
        allPill = pillDao.getAllPill();
    }

    public LiveData<List<Pill>> getAllPill() {
        return allPill;
    }

    public void insert(Log log) {
        new InsertLogAsyncTask(logDao).execute(log);
    }

    public void update(Log log) {
        new UpdateLogAsyncTask(logDao).execute(log);
    }

    public void updateLogById(String id) {

    }

    public void delete(Log log) {
        new DeleteLogAsyncTask(logDao).execute(log);
    }

    public void deleteAllNotes() {
        new DeleteAllLogAsyncTask(logDao).execute();
    }

    public List<Log> getLogByDate(String date) throws ExecutionException, InterruptedException {
        return new GetLogByDateAsync(logDao).execute(date).get();
    }

    public List<String> getDateList() throws ExecutionException, InterruptedException {
        return new GetDateAsync(logDao).execute().get();
    }

    public List<String> getA1cLevel() throws ExecutionException, InterruptedException {
        return new GetA1cLevelAsync(logDao).execute().get();
    }

    public List<String> getBloodSugar() throws ExecutionException, InterruptedException {
        return new GetBloodSugarAsync(logDao).execute().get();
    }

    public List<String> getSystolic() throws ExecutionException, InterruptedException {
        return new GetSystolicAsync(logDao).execute().get();
    }

    public List<String> getDiastolic() throws ExecutionException, InterruptedException {
        return new GetDiastolicAsync(logDao).execute().get();
    }

    public void insert(Pill pill) {
        new InsertPillAsync(pillDao).execute(pill);
    }

    public Pill getPillById(int id) throws ExecutionException, InterruptedException {
        return new GetPillByIdAsync(pillDao).execute(id).get();
    }

    private static class GetLogByDateAsync extends AsyncTask<String, Void, List<Log>> {

        private LogDao logDao;

        GetLogByDateAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<Log> doInBackground(String... strings) {
            return logDao.getByDate(strings[0]);
        }
    }

    private static class InsertPillAsync extends AsyncTask<Pill, Void, Void> {

        private PillDao pillDao;

        public InsertPillAsync(PillDao pillDao) {
            this.pillDao = pillDao;
        }

        @Override
        protected Void doInBackground(Pill... pills) {
            pillDao.insert(pills[0]);
            return null;
        }
    }

    private static class GetPillByIdAsync extends AsyncTask<Integer, Void, Pill> {

        private PillDao pillDao;

        public GetPillByIdAsync(PillDao pillDao) {
            this.pillDao = pillDao;
        }

        @Override
        protected Pill doInBackground(Integer... integers) {
            return pillDao.getPill(integers[0]);
        }
    }

    private static class GetDateAsync extends AsyncTask<Void, Void, List<String>> {

        private LogDao logDao;

        public GetDateAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return logDao.getDateList();
        }
    }

    private static class GetBloodSugarAsync extends AsyncTask<Void, Void, List<String>> {

        private LogDao logDao;

        GetBloodSugarAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return logDao.getBloodSugar();
        }
    }

    private static class GetDiastolicAsync extends AsyncTask<Void, Void, List<String>> {

        private LogDao logDao;

        GetDiastolicAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return logDao.getBloodSugar();
        }
    }

    private static class GetSystolicAsync extends AsyncTask<Void, Void, List<String>> {

        private LogDao logDao;

        GetSystolicAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return logDao.getBloodSugar();
        }
    }

    private static class GetA1cLevelAsync extends AsyncTask<Void, Void, List<String>> {

        private LogDao logDao;

        public GetA1cLevelAsync(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return logDao.getA1cLevel();
        }
    }

    private static class InsertLogAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao logDao;

        private InsertLogAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(Log... logs) {
            logDao.insert(logs[0]);
            return null;
        }
    }

    private static class UpdateLogAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao logDao;

        private UpdateLogAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(Log... logs) {
            logDao.update(logs[0]);
            return null;
        }
    }

    private static class DeleteLogAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao logDao;

        private DeleteLogAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(Log... logs) {
            logDao.delete(logs[0]);
            return null;
        }
    }

    private static class DeleteAllLogAsyncTask extends AsyncTask<Void, Void, Void> {
        private LogDao logDao;

        private DeleteAllLogAsyncTask(LogDao logDao) {
            this.logDao = logDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            logDao.deleteAllLogs();
            return null;
        }

    }

}
