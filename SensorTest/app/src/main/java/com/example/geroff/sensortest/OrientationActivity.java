package com.example.geroff.sensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class OrientationActivity extends Activity {
    private TextView tvInfo;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private long lastTime = 0;
    private long duration = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelatorator);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorEventListener = new SensorEventListener() {
            float[] accelerometerValues = new float[3];
            float[] magneticValues = new float[3];

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    accelerometerValues = event.values.clone();
                } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    magneticValues = event.values.clone();
                }
                float[] R = new float[9];
                float[] values = new float[3];

                SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
                SensorManager.getOrientation(R, values);

                float zValue=  values[0];
                float xValue=  values[1];
                float yValue=  values[2];
                if (System.currentTimeMillis() - lastTime > duration) {
                    Toast.makeText(getApplicationContext(), "Z轴角度：" + Math.toDegrees(zValue) + "\nX轴角度：" + Math.toDegrees(xValue) + "\nY轴角度：" + Math.toDegrees(yValue), Toast.LENGTH_LONG).show();
                    lastTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("TAG", "onAccuracyChanged");
            }
        };
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
}
