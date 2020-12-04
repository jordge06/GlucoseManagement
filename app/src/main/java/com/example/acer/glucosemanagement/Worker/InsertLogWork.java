package com.example.acer.glucosemanagement.Worker;

import android.content.Context;

import com.example.acer.glucosemanagement.Databases.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InsertLogWork extends Worker {

    public InsertLogWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

    private void insertLog() {

    }
}
