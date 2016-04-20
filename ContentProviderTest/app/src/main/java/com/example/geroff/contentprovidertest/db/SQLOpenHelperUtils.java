package com.example.geroff.contentprovidertest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Geroff on 2016/4/19.
 */
public class SQLOpenHelperUtils extends SQLiteOpenHelper {
    private static final String CreateTable = "create table student(" +
            "id integer primary key autoincrement," +
            " number integer," +
            " name text, " +
            "sex text)";
    private static final String CreateCategory = "create table if not exists category(" +
            "id integer primary key autoincrement, " +
            "abstract text, " +
            "content text, " +
            "page integer, " +
            "price real)";

    private Context mContext;
    public SQLOpenHelperUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable);
        db.execSQL(CreateCategory);
//        Toast.makeText(mContext, "Create Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists student");
        db.execSQL("drop table if exists category");
        this.onCreate(db);
//        Toast.makeText(mContext, "Upgrade Success", Toast.LENGTH_LONG).show();
    }
}
