package com.example.geroff.sensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AccelatoratorActivity extends Activity {
    private TextView tvInfo;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private long lastTime = 0;
    private long duration = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelatorator);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float xValue= event.values[0];
                float yValue= event.values[1];
                float zValue= event.values[2];
                if (xValue > 15 /* || yValue > 15|| zValue > 15 */&& System.currentTimeMillis() - lastTime > duration) {
                    Toast.makeText(getApplicationContext(), "摇一摇", Toast.LENGTH_SHORT).show();
                    lastTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
}
