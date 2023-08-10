package com.example.lab6_service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CharWorker extends Worker {
    public CharWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        // This code will be executed after the work is done
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Charing", Toast.LENGTH_SHORT).show();
            }
        }, 1000);

        // Return the result of the work
        return Result.success();
    }
}
