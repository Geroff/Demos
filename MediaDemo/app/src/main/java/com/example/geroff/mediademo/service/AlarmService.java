package com.example.geroff.mediademo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by Geroff on 2016/4/26.
 */
public class AlarmService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent it = new Intent(AlarmService.this,AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,it, PendingIntent.FLAG_UPDATE_CURRENT);
        long longTriger = SystemClock.elapsedRealtime() + 10 * 1000;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, longTriger, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
