package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.Context;
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
    public DbHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
