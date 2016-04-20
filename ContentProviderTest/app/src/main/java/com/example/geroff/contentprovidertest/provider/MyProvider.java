package com.example.geroff.contentprovidertest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.geroff.contentprovidertest.db.SQLOpenHelperUtils;

/**
 * Created by Geroff on 2016/4/20.
 */
public class MyProvider extends ContentProvider {
    private final static int STUDENT_TABLE = 0;
    private final static int STUDENT_ITEM = 1;
    private final static int CATEGORY_TABLE = 2;
    private final static int CATEGORY_ITEM = 3;
    private final static String AUTHORITY = "com.example.geroff.contentprovidertest";

    private SQLOpenHelperUtils dbHelper;
    private static UriMatcher uriMatcher;
    @Override
    public boolean onCreate() {
        dbHelper = new SQLOpenHelperUtils(getContext(), "book.db", null, 1);
        return true;
    }
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "student", STUDENT_TABLE);
        uriMatcher.addURI(AUTHORITY, "student/#", STUDENT_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_TABLE);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case STUDENT_TABLE:
                cursor = db.query("student", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case STUDENT_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query("student",projection, "id=?", new String[]{id}, null, null, sortOrder);
                break;
            case CATEGORY_TABLE:
                cursor = db.query("category", projection,selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId= uri.getPathSegments().get(1);
                cursor = db.query("category", projection, "id=?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case STUDENT_TABLE:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".student";
            case STUDENT_ITEM:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".student";
            case CATEGORY_TABLE:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".category";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri newUri = null;
        switch (uriMatcher.match(uri)) {
            case STUDENT_TABLE:
            case STUDENT_ITEM:
               long studentId = db.insert("student", null, values);
                newUri = Uri.parse("content://" + AUTHORITY + "/student/" + studentId);
                break;
            case CATEGORY_TABLE:
            case CATEGORY_ITEM:
                long categoryId = db.insert("category", null, values);
                newUri = Uri.parse("content://" + AUTHORITY + "/category/" + categoryId);
                break;
            default:
                break;
        }
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delLine = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENT_TABLE:
                delLine = db.delete("student", selection, selectionArgs);
                break;
            case STUDENT_ITEM:
                String delLineId = uri.getPathSegments().get(1);
                delLine = db.delete("student", "id=?", new String[]{delLineId});
                break;
            case CATEGORY_TABLE:
                delLine = db.delete("category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String delCategoryLineId = uri.getPathSegments().get(1);
                delLine = db.delete("category", "id=?", new String[]{delCategoryLineId });
                break;
        }
        return delLine;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateLine = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENT_TABLE:
                updateLine = db.update("student", values, selection,selectionArgs);
                break;
            case STUDENT_ITEM:
                String updateStuId = uri.getPathSegments().get(1);
                updateLine = db.update("student", values, "id=?", new String[]{updateStuId});
                break;
            case CATEGORY_TABLE:
                updateLine = db.update("category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String updateCategoryId = uri.getPathSegments().get(1);
                updateLine = db.update("category", values, "id=?", new String[]{updateCategoryId});
                break;
            default:
                break;
        }
        return updateLine;
    }
}
