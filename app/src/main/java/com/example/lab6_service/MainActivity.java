package com.example.lab6_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1207;

    Button button, btnBackgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        btnBackgroundService = findViewById(R.id.btnBackgroundService);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNotificationPermission();
            }
        });

        btnBackgroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundService();
            }
        });

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(CharWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            sendNotification();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        }
    }

    private void sendNotification() {
        int id = (int) System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, ConfigNotifications.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification")
                .setContentText("This is a notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setColor(getResources().getColor(R.color.purple_200, null))
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(id, builder.build());

        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
    }

    private void startBackgroundService() {
        Intent intent = new Intent(MainActivity.this, BackgroundService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotification();
            }
        }
    }
}
