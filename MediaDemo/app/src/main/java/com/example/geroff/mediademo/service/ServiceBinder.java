package com.example.geroff.mediademo.service;

import android.os.Binder;
import android.util.Log;

/**
 * Created by Geroff on 2016/4/25.
 */
public class ServiceBinder extends Binder{
    private static final String TAG = "SERVICE";
    public void  download(){
        Log.i(TAG, "download");
    }
}
