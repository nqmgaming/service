package com.example.lab6_service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {
    private static final String TAG = "ForegroundService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ConfigNotifications.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Service is running")
                .setContentText("You can't stop me if you slide me away")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Notification notification = builder.build();
        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service stopped");
    }
}
