package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhiguang on 16/7/22.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download_db";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String DELETE_TABLE = "delete table thread_info if exists thread_info";
    private SQLiteDatabase mDatabase;
    private DbHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }
    private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static DbHelper dbHelper = null;

    public static DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            synchronized (DbHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DbHelper(context);
                    return dbHelper;
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        mDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        db.execSQL(CREATE_TABLE);
        mDatabase = db;
    }

    public void saveInfo(TaskInfo info) {
        ContentValues values = new ContentValues();
        values.put("thread_id",info.getId());
        values.put("url",info.getUrl());
        values.put("start",info.getStart());
        values.put("end",info.getEnd());
        values.put("finished",info.getFinished());
        synchronized (DbHelper.this) {
            mDatabase.insert("thread_info", null, values);
        }
    }
    public void updateInfo(TaskInfo info) {
        synchronized (DbHelper.this) {
        }
    }
//    public TaskInfo getInfo() {
//        Cursor cursor;
//        synchronized (DbHelper.this) {
//            cursor = mDatabase.query("thread_info", null, null, null, null, null, null);
//        }
//        for (int i=0;i<cursor.getColumnCount();i++) {
//
//        }
//    }

}
