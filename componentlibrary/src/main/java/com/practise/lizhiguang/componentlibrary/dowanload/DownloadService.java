package com.practise.lizhiguang.componentlibrary.dowanload;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lizhiguang on 16/7/21.
 */
public class DownloadService extends Service {
    public static final String ACTION_START = "start";
    private static final String TAG = "DownloadService";
    public static final String ACTION_STOP = "stop";
    public static final String INFORMATION = "fileInfo";
    public static final int ACTION_INIT = 1;
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download/";
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_INIT:
                    Log.d(TAG, "handleMessage: what="+msg.what+",obj="+msg.obj.toString());
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo;
        DownLoadThread thread;
        if (ACTION_START.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: start:"+fileInfo.toString());
            thread = new DownLoadThread(fileInfo);
            thread.start();
        }
        else if (ACTION_STOP.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: stop:"+fileInfo.toString());
            thread = new DownLoadThread(fileInfo);
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    class DownLoadThread extends Thread {
        private FileInfo mFileInfo = null;

        public DownLoadThread(FileInfo fileInfo) {
            this.mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == 200) {
                    length = connection.getContentLength();
                }
                if (length <= 0)
                    return;
                File dir = new File(PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(PATH,mFileInfo.getName());
                accessFile = new RandomAccessFile(file,"rwd");
                accessFile.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(ACTION_INIT,mFileInfo);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.disconnect();
                    accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
