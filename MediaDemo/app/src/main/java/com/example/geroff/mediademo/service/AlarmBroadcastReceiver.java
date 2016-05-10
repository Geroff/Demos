package com.example.geroff.mediademo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Geroff on 2016/4/26.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm:bi bi bi", Toast.LENGTH_SHORT).show();
    }
}
