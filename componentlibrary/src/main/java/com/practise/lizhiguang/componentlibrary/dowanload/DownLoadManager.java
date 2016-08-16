package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by lizhiguang on 16/7/21.
 * 后台下载与断点续传功能
 * 后台下载使用前台service完成,以保证service不被系统回收
 * 断点续传功能使用数据库完成.使得可以保存下载进度
 */
public class DownLoadManager extends BroadcastReceiver implements ServiceConnection {
    private Context mContext;
    private static final String TAG = "DownLoadManager";
    /***/
    public static final String ACTION_PROGRESS = "com.practise.lizhiguang.action.progress";
    /**下载成功消息*/
    public static final String ACTION_FINISH = "com.practise.lizhiguang.action.finish";
    /**暂停下载消息*/
    public static final String ACTION_PAUSE = "com.practise.lizhiguang.action.pause";
    /**重启下载消息*/
    public static final String ACTION_RESTART = "com.practise.lizhiguang.action.restart";
    private IntentFilter filter;
    //service中的handler,用来给service发消息,如果没有连接则为null
    private Handler mServiceHandle;
    private FileInfo mCurrentFile;
    public DownLoadManager(Context context) {
        mContext = context;
        filter = new IntentFilter();
        filter.addAction(ACTION_FINISH);
        filter.addAction(ACTION_PROGRESS);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_RESTART);
        mServiceHandle = null;
        mCurrentFile = null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mContext.unregisterReceiver(this);
    }

    /**
     * 使用url进行下载,进行自动下载分析
     * @param url
     */
    public void downloadWithUrl(String url) {
        Intent intent = new Intent();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(url);
        fileInfo.setName(getNameByUrl(url));
        intent.setClass(mContext,DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra(DownloadService.INFORMATION,fileInfo);
        mCurrentFile = fileInfo;
        mContext.startService(intent);
        mContext.bindService(new Intent(mContext,DownloadService.class),this,0);
    }
    /**
     * 传入参数可以自己命名下载文件名称
     * @param info
     */
    public void downloadWithFileInfo(FileInfo info) {
        Intent intent = new Intent();
        intent.setClass(mContext,DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra(DownloadService.INFORMATION,info);
        mCurrentFile = info;
        mContext.registerReceiver(this,filter);
        mContext.startService(intent);
        mContext.bindService(new Intent(mContext,DownloadService.class),this,0);
    }

    public static String getNameByUrl(String url) {
        int p = url.lastIndexOf('/');
        if (p >= 0)
            return url.substring(p + 1,url.length());
        else
            return "";
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        DownloadService.MyBinder binder = (DownloadService.MyBinder) service;
        mServiceHandle = binder.getHandler();
        Log.d(TAG, "onServiceConnected: mServiceHandle="+mServiceHandle);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mServiceHandle = null;
        Log.d(TAG, "onServiceDisconnected");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive:"+intent.getAction()+",mServiceHandle="+mServiceHandle);
        if (intent.getAction().equals(ACTION_FINISH)) {
            Toast.makeText(mContext,"下载成功",Toast.LENGTH_SHORT).show();
        }
        else if (intent.getAction().equals(ACTION_PAUSE)) {
            if (mServiceHandle != null) {
                mServiceHandle.obtainMessage(DownloadService.ACTION_PAUSE).sendToTarget();
            }
        }
        else if (intent.getAction().equals(ACTION_RESTART)) {
            if (mServiceHandle != null) {
                Message message = Message.obtain(mServiceHandle,DownloadService.ACTION_RESTART,mCurrentFile.getUrl());
                message.sendToTarget();
            }
        }
    }
}
