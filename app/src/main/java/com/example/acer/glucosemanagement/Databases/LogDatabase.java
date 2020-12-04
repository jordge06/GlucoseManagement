package com.example.acer.glucosemanagement.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Log.class, Pill.class}, version = 8, exportSchema = false)
public abstract class LogDatabase extends RoomDatabase {

    private static volatile LogDatabase instance;

    public abstract LogDao logDao();
    public abstract PillDao pillDao();

    public static synchronized LogDatabase getInstance(Context context) {
        if (instance == null) {
            // Added Things
            synchronized (LogDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LogDatabase.class, "log_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LogDao logDao;

        private PopulateDbAsyncTask(LogDatabase logDatabase) {
            logDao = logDatabase.logDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            logDao.insert(new Log("john016dionisio@gmail.com",
                    "Aug 19, 2020",
                    "02:54",
                    "5",
                    "7",
                    "7",
                    "8",
                    "50",
                    "Insulin",
                    "3",
                    "Dinner",
                    "Sample Notes",
                    "Sample Tags"));

//            logDao.insert(new Log("john016dionisio@gmail.com",
//                    "Aug 21, 2020",
//                    "02:54",
//                    "5",
//                    "7",
//                    "8",
//                    "50",
//                    "Insulin",
//                    "3",
//                    "Dinner",
//                    "Sample Notes",
//                    "Sample Tags"));
//
//            logDao.insert(new Log("john016dionisio@gmail.com",
//                    "Aug 23, 2020",
//                    "02:54",
//                    "5",
//                    "7",
//                    "8",
//                    "50",
//                    "Insulin",
//                    "3",
//                    "Dinner",
//                    "Sample Notes",
//                    "Sample Tags"));

            return null;
        }
    }
}
