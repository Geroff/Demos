package com.example.geroff.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.geroff.databasetest.db.SQLOpenHelperUtils;

public class MainActivity extends Activity {

    private TextView info;
    private Button btnAdd;
    private Button btnDel;
    private Button btnUpdate;
    private Button btnQuery;
    private SQLOpenHelperUtils dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        dbHelper = new SQLOpenHelperUtils(MainActivity.this, "student.db", null, 2);

        info = (TextView) findViewById(R.id.tv_info);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDel = (Button) findViewById(R.id.btn_del);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnQuery = (Button) findViewById(R.id.btn_query);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("number", 90507232);
                contentValues.put("name", "Geroff");
                contentValues.put("sex", "male");
                db.insert("student", null, contentValues);
                contentValues.clear();
                contentValues.put("number", 90507231);
                contentValues.put("name", "Lu");
                contentValues.put("sex", "female");
                db.insert("student", null, contentValues);
                db.close();
                info.setText(queryInfo());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", "Chen");
                db.update("student", cv, "name = ?", new String[]{"Lu"});
                db.close();
                info.setText(queryInfo());
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("student", "name=?", new String[]{"Chen"});
                db.close();
                info.setText(queryInfo());
            }
        });
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("student", null, null, null, null, null, null);
                StringBuffer stringBuffer = new StringBuffer();
                if (cursor.moveToFirst()) {
                    do {
                        stringBuffer.append("num:").append(cursor.getInt(cursor.getColumnIndex("number")))
                                .append(",name:").append(cursor.getString(cursor.getColumnIndex("name")))
                                .append(".sex").append(cursor.getString(cursor.getColumnIndex("sex")))
                                .append("\n");
                    } while (cursor.moveToNext());
                }
                info.setText(stringBuffer.toString());
                cursor.close();
            }
        });

    }
    private String queryInfo(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("student", null, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        for (cursor.moveToFirst(); !cursor.isLast(); cursor.moveToNext()) {
            stringBuffer.append("number:").append(cursor.getInt(cursor.getColumnIndex("number")))
                    .append(",name:").append(cursor.getString(cursor.getColumnIndex("name")))
                    .append(",sex:").append(cursor.getString(cursor.getColumnIndex("sex"))).append("\n");
        }
        cursor.close();
        return stringBuffer.toString();
    }
}
