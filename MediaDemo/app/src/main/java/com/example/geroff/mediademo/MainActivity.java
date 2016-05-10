package com.example.geroff.mediademo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends Activity {
    private Button btnNotification;
    private TextView tvInfo;
    private MessageReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        btnNotification = (Button) findViewById(R.id.btn_notification);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        receiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction("SENT_SMS_ACTION");
//        filter.setPriority(10000);
        registerReceiver(receiver, filter);

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                Intent sentIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent, 0);
                smsManager.sendTextMessage("15280624901", null, "test from mx3", pendingIntent, null);
            }
        });
    }
    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("SENT_SMS_ACTION")) {
                if (getResultCode() == RESULT_OK) {
                    Toast.makeText(context, "send succeeded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "send failed", Toast.LENGTH_SHORT).show();
                }
            } else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                StringBuffer stringBuffer = new StringBuffer();
                for (SmsMessage smsMessage :messages) {
                    stringBuffer.append(smsMessage.getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                tvInfo.setText("from:" + sender + "\ncontent:" + stringBuffer.toString());
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
