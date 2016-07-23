package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by lizhiguang on 16/7/21.
 */
public class DownLoadManager extends BroadcastReceiver implements ServiceConnection {
    Context mContext;
    private static final String TAG = "DownLoadManager";
    public static final String ACTION_FINISH = "com.practise.lizhiguang.action.finish";
    public DownLoadManager(Context context) {
        mContext = context;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mContext.unregisterReceiver(this);
    }

    public void downloadWithUrl(String url) {
        Intent intent = new Intent();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(url);
        fileInfo.setName(getNameByUrl(url));
        intent.setClass(mContext,DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra(DownloadService.INFORMATION,fileInfo);
        mContext.startService(intent);
    }
    public void downloadWithFileInfo(FileInfo info) {
        Intent intent = new Intent();
        intent.setClass(mContext,DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra(DownloadService.INFORMATION,info);
        mContext.registerReceiver(this,new IntentFilter(ACTION_FINISH));
        mContext.startService(intent);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public static String getNameByUrl(String url) {
        int p = url.lastIndexOf('/');
        if (p >= 0)
            return url.substring(p + 1,url.length());
        else
            return "";
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_FINISH)) {
            Log.d(TAG, "onReceive: 下载成功");
            Toast.makeText(mContext,"下载成功",Toast.LENGTH_SHORT).show();
        }
    }
}
