package com.sharedream.servicetest.service;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.sharedream.servicetest.MainActivity;
import com.sharedream.servicetest.R;
import com.sharedream.servicetest.TAG;

/**
 * Created by as on 2016/4/26.
 */
public class ForgroundService extends Service implements TAG{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(FILTER_TAG, "onCreate");
        super.onCreate();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(ForgroundService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ForgroundService.this, 0 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.ic_launcher);
        notification.setContentTitle("content title");
        notification.setContentIntent(pendingIntent);
        notification.setContentText("content text");
        startForeground(1, notification.build());
//        notificationManager.notify(1, notification.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(FILTER_TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(FILTER_TAG, "onDestroy");
        super.onDestroy();
    }
}
