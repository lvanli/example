package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiguang on 16/7/22.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download_db";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String DELETE_TABLE = "delete table thread_info if exists thread_info";
    private static final String[] COLUMNS = new String[]{"thread_id","url","start","end","finished"};
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
            SQLiteDatabase db =  dbHelper.getWritableDatabase();
            db.insert("thread_info", null, values);
            db.close();
        }
    }
    public void updateInfo(TaskInfo info) {
        ContentValues values = new ContentValues();
        values.put("thread_id",info.getId());
        values.put("url",info.getUrl());
        values.put("start",info.getStart());
        values.put("end",info.getEnd());
        values.put("finished",info.getFinished());
        synchronized (DbHelper.this) {
            SQLiteDatabase db  = dbHelper.getWritableDatabase();
            db.update("thread_info",values,"thread_id = ? and url = ?",new String[]{String.valueOf(info.getId()),info.getUrl()});
            db.close();
        }
    }
    public void deleteInfo(TaskInfo info) {
        synchronized (DbHelper.this) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("thread_info","thread_id = ? and url=?",new String[]{String.valueOf(info.getId()),info.getUrl()});
            db.close();
        }
    }
    public List<TaskInfo> getInfo(String url) {
        if (url == null)
            return null;
        synchronized (DbHelper.this) {
            List<TaskInfo> infos = new ArrayList<>();
            Cursor cursor;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // TODO have crash because the bind value at index 1 is null
            cursor = db.query("thread_info",COLUMNS,"url = ?",new String[]{url},null,null,null);
            while (cursor.moveToNext()) {
                TaskInfo info = new TaskInfo();
                info.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
                info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                info.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
                infos.add(info);
            }
            cursor.close();
            db.close();
            return  infos;
        }
    }
    public boolean isExists(String url,int id) {
        synchronized (DbHelper.this) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("thread_info",COLUMNS,"url = ? and thread_id = ?",new String[]{url,String.valueOf(id)},null,null,null);
            boolean exists = cursor.moveToNext();
            cursor.close();
            db.close();
            return exists;
        }
    }
}
