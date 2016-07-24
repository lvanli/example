package com.practise.lizhiguang.componentlibrary.dowanload;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.practise.lizhiguang.componentlibrary.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    private static final int PROGRESS = 2;
    private static final int FINISH = 3;
    private static final int MAX_THREAD = 5;
    private RemoteViews remoteViews;
    private Notification.Builder builder;
    private List<Thread> threads;
    private int mProgress;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_INIT:
                    threads.clear();
                    Log.d(TAG, "handleMessage: what="+msg.what+",obj="+msg.obj.toString());
                    FileInfo info = (FileInfo) msg.obj;
                    int oneDown = info.getLength()/MAX_THREAD;
                    for (int i=0;i<MAX_THREAD;i++) {
                        DownLoadThread thread;
                        if (i != MAX_THREAD - 1) {
                            thread = new DownLoadThread(info, oneDown * i, oneDown * (i + 1) - 1);
                            Log.d(TAG, "handleMessage: from "+(oneDown*i)+" to "+(oneDown*(i+1)-1));
                        }
                        else {
                            thread = new DownLoadThread(info, oneDown * i, info.getLength());
                            Log.d(TAG, "handleMessage: from "+(oneDown*i)+" to "+info.getLength());
                        }
                        threads.add(thread);
                        thread.start();
                    }
                    mProgress = 0;
                    remoteViews.setProgressBar(R.id.progressBar,100,0,false);
                    builder.setContent(remoteViews);
                    startForeground(201,builder.build());
                    break;
                case PROGRESS:
                    mProgress += msg.arg2;
                    Log.d(TAG, "handleMessage: what="+msg.what+",arg1="+msg.arg1+",arg2="+msg.arg2);
                    remoteViews.setProgressBar(R.id.progressBar,msg.arg1,mProgress,false);
                    builder.setContent(remoteViews);
                    startForeground(201,builder.build());
                    if (mProgress < msg.arg1)
                        break;
                case FINISH:
                    stopForeground(true);
                    Intent intent = new Intent();
                    intent.setAction(DownLoadManager.ACTION_FINISH);
                    sendBroadcast(intent);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        threads = new ArrayList<>(MAX_THREAD);
        remoteViews = new RemoteViews(getPackageName(),R.layout.download_notification);
        builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("下载").setContentText("正在下载").setSmallIcon(R.drawable.loading);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo;
        DownLoadPreThread thread;
        if (ACTION_START.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: start:"+fileInfo.toString());
            thread = new DownLoadPreThread(fileInfo);
            thread.start();
        }
        else if (ACTION_STOP.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: stop:"+fileInfo.toString());
            thread = new DownLoadPreThread(fileInfo);
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    class DownLoadThread extends Thread {
        private FileInfo mFileInfo = null;
        int begin,end;

        public DownLoadThread(FileInfo mFileInfo,int begin,int end) {
            this.mFileInfo = mFileInfo;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Range", "bytes=" + begin
                        + "-" + end + "");
                InputStream is = connection.getInputStream();
                byte buffer[] = new byte[4*1024*1024];
                File file = new File(PATH,mFileInfo.getName());
                RandomAccessFile accessFile = new RandomAccessFile(file,"rwd");
                accessFile.seek(begin);
                int len = 0;
                while ((len = is.read(buffer)) > 0) {
                    Log.d(TAG, "run: get "+len+" bytes");
                    accessFile.write(buffer);
                    mHandler.obtainMessage(PROGRESS,mFileInfo.getLength(),len).sendToTarget();
                }
                accessFile.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null)
                    connection.disconnect();
            }
        }
    }
    class DownLoadPreThread extends Thread {
        private FileInfo mFileInfo = null;

        public DownLoadPreThread(FileInfo fileInfo) {
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
                Log.d(TAG, "run: length="+length);
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
                mHandler.obtainMessage(ACTION_INIT,mFileInfo).sendToTarget();
                accessFile.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }

        }
    }
}
