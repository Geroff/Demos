package com.example.geroff.mediademo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Geroff on 2016/4/26.
 */
public class TestIntentService extends IntentService {
    private final static String TAG = "SERVICE";

    public TestIntentService() {
         super("TestIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service onCreate");
        Log.i(TAG, "Service currentThread-->id:" + Thread.currentThread().getId() + ",name:" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Service onHandleIntent");
        Log.i(TAG, "ServicecurrentThread-->id:" + Thread.currentThread().getId() + ",name:" + Thread.currentThread().getName());
    }
}
