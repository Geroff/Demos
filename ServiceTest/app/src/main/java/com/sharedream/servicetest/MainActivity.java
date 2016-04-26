package com.sharedream.servicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sharedream.servicetest.service.ForgroundService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TAG{
    private Button btnStartService;
    private Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartService = (Button) findViewById(R.id.btn_startService);
        btnStopService = (Button) findViewById(R.id.btn_stopService);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(1);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ForgroundService.class);
        switch (v.getId()) {
            case R.id.btn_startService:
                startService(intent);
                break;
            case R.id.btn_stopService:
                stopService(intent);
                break;
        }
    }
}
