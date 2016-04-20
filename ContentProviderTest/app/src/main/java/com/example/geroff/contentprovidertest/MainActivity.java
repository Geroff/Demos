package com.example.geroff.contentprovidertest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView info;
    private Button btnQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.tv_info);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                    StringBuffer stringBuffer = new StringBuffer();
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            stringBuffer.append("name:").append(name).append(",number:").append(number).append("\n");
                        } while(cursor.moveToNext());
                        info.setText(stringBuffer.toString());
                    }
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
