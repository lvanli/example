package com.practise.lizhiguang.componentlibrary.dowanload;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.practise.lizhiguang.componentlibrary.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiguang on 16/7/21.
 * 前台service下载类
 */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    //可以修改成handler消息驱动,连接成功就会返回当前类的handler
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final String INFORMATION = "fileInfo";
    public static final int ACTION_INIT = 1;
    public static final int BUFFER_SIZE = 1 * 1024 * 1024;
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";
    private static final int ACTION_PROGRESS = 2;
    public static final int ACTION_PAUSE = 3;
    public static final int ACTION_RESTART = 4;
    private static final int MAX_THREAD = 5;
    private RemoteViews remoteViews;
    private Notification.Builder builder;
    private List<Thread> threads;
    private int mProgress;
    private DbHelper mDbHelper;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_INIT:
                    threads.clear();
                    Log.d(TAG, "handleMessage: what=" + msg.what + ",obj=" + msg.obj.toString());
                    FileInfo info = (FileInfo) msg.obj;
                    int oneDown = info.getLength() / MAX_THREAD;
                    for (int i = 0; i < MAX_THREAD; i++) {
                        DownLoadThread thread;
                        if (i != MAX_THREAD - 1) {
                            thread = new DownLoadThread(info, oneDown * i, oneDown * (i + 1) - 1,i);
                            Log.d(TAG, "handleMessage: from " + (oneDown * i) + " to " + (oneDown * (i + 1) - 1));
                        } else {
                            thread = new DownLoadThread(info, oneDown * i, info.getLength(),i);
                            Log.d(TAG, "handleMessage: from " + (oneDown * i) + " to " + info.getLength());
                        }
                        threads.add(thread);
                        thread.start();
                    }
                    mProgress = 0;
                    remoteViews.setProgressBar(R.id.progressBar, 100, 0, false);
                    builder.setContent(remoteViews);
                    startForeground(201, builder.build());
                    break;
                case ACTION_PROGRESS:
                    mProgress += msg.arg2;
                    //Log.d(TAG, "handleMessage: what=" + msg.what + ",arg1=" + msg.arg1 + ",arg2=" + msg.arg2);
                    remoteViews.setProgressBar(R.id.progressBar, msg.arg1, mProgress, false);
                    builder.setContent(remoteViews);
                    startForeground(201, builder.build());
                    if (mProgress >= msg.arg1) {
                        stopForeground(true);
                        Intent intent = new Intent();
                        intent.setAction(DownLoadManager.ACTION_FINISH);
                        sendBroadcast(intent);
                    }
                    break;
                case ACTION_PAUSE:
                    Log.d(TAG, "handleMessage: pause,threads.size="+threads.size());
                    for (int i = 0; i < threads.size(); i++) {
                        threads.get(i).interrupt();
                    }
                    threads.clear();
                    break;
                case ACTION_RESTART:
                    List<TaskInfo> infos = mDbHelper.getInfo((String) msg.obj);
                    for (int i = 0; i < infos.size(); i++) {
                        TaskInfo taskInfo = infos.get(i);
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setUrl(taskInfo.getUrl());
                        fileInfo.setName(DownLoadManager.getNameByUrl(taskInfo.getUrl()));
                        fileInfo.setFinish(taskInfo.getFinished());
                        fileInfo.setLength(taskInfo.getEnd() - taskInfo.getStart());
                        DownLoadThread thread = new DownLoadThread(fileInfo, taskInfo.getStart() + taskInfo.getFinished(), taskInfo.getEnd(),i);
                        Log.d(TAG, "handleMessage: from " + (taskInfo.getStart() + taskInfo.getFinished()) + " to " + taskInfo.getEnd());
                        threads.add(thread);
                        thread.start();
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        threads = new ArrayList<>(MAX_THREAD);
        remoteViews = new RemoteViews(getPackageName(), R.layout.download_notification);
        builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("下载").setContentText("正在下载").setSmallIcon(R.drawable.loading);
        remoteViews.setOnClickPendingIntent(R.id.download_pause, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(DownLoadManager.ACTION_PAUSE), 0));
        remoteViews.setOnClickPendingIntent(R.id.download_start, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(DownLoadManager.ACTION_RESTART), 0));
        mDbHelper = DbHelper.getInstance(getApplicationContext());
    }

    class MyBinder extends Binder {
        private DownloadService getService() {
            return DownloadService.this;
        }

        public Handler getHandler() {
            return mHandler;
        }
    }

    private MyBinder classBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return classBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo;
        DownLoadPreThread thread;
        if (ACTION_START.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: start:" + fileInfo.toString());
            thread = new DownLoadPreThread(fileInfo);
            thread.start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra(INFORMATION);
            Log.d(TAG, "onStartCommand: stop:" + fileInfo.toString());
            thread = new DownLoadPreThread(fileInfo);
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class DownLoadThread extends Thread {
        private FileInfo mFileInfo = null;
        int begin, end,id;

        public DownLoadThread(FileInfo mFileInfo, int begin, int end,int id) {
            this.mFileInfo = mFileInfo;
            this.begin = begin;
            this.end = end;
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            TaskInfo info = new TaskInfo(mFileInfo.getUrl(),begin,end,0,id);
            if (!mDbHelper.isExists(mFileInfo.getUrl(),id))
                mDbHelper.saveInfo(info);
            HttpURLConnection connection = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setRequestProperty("Range", "bytes=" + (begin + mFileInfo.getFinish())
                        + "-" + end + "");
                InputStream is = connection.getInputStream();
                byte buffer[] = new byte[BUFFER_SIZE];
                File file = new File(PATH, mFileInfo.getName());
                RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
                accessFile.seek(begin + mFileInfo.getFinish());
                int len = 0;
                while ((len = is.read(buffer)) > 0) {
                    if (isInterrupted())
                        break;
                    Log.d(TAG, "run: get " + len + " bytes");
                    accessFile.write(buffer, 0, len);
                    mFileInfo.setFinish(mFileInfo.getFinish() + len);
                    info.setFinished(mFileInfo.getFinish());
                    if (len < end  - mFileInfo.getFinish() + begin)
                        mDbHelper.updateInfo(info);
                    else
                        mDbHelper.deleteInfo(info);
                    mHandler.obtainMessage(ACTION_PROGRESS, mFileInfo.getLength(), len).sendToTarget();
                }
                accessFile.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
        public void interrupt() {
            super.interrupt();
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
                Log.d(TAG, "run: length=" + length);
                if (length <= 0)
                    return;
                File dir = new File(PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(PATH, mFileInfo.getName());
                accessFile = new RandomAccessFile(file, "rwd");
                accessFile.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(ACTION_INIT, mFileInfo).sendToTarget();
                accessFile.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

        }
    }
}
