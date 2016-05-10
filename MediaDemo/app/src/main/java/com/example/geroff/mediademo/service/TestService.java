package com.example.geroff.mediademo.service;


import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.geroff.mediademo.R;


/**
 * Created by Geroff on 2016/4/25.
 */
public class TestService extends Service {
    private final static String TAG = "SERVICE";
    ServiceBinder serviceBinder = new ServiceBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        Log.i(TAG, "currentThread-->id:" + Thread.currentThread().getId() + ",name:" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
