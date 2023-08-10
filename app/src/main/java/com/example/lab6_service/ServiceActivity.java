package com.example.lab6_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {
    Button startService, stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        startService = findViewById(R.id.btnStartService);
        stopService = findViewById(R.id.btnStopService);
        startService.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceActivity.this, ForegroundService.class);
            startService(intent);
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        });
        stopService.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceActivity.this, ForegroundService.class);
            stopService(intent);
        });
    }
}