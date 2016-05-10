package com.example.geroff.mediademo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.geroff.mediademo.R;

/**
 * Created by Geroff on 2016/4/25.
 */
public class ServiceActivity extends Activity {
    private static final String TAG = "SERVICE";
    private Button btnStartService;
    private Button btnStopService;
    private ServiceBinder serviceBinder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            serviceBinder = (ServiceBinder) service;
            serviceBinder.download();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_layout);
        btnStartService = (Button) findViewById(R.id.startService);
        btnStopService = (Button) findViewById(R.id.stopService);
        Log.i(TAG, "Activity onCreate");
        Log.i(TAG, "Activity currentThread-->id:" + Thread.currentThread().getId() + ",name:" + Thread.currentThread().getName());
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, AlarmService.class);
                startService(intent);
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, AlarmService.class);
                stopService(intent);
            }
        });
    }
}
