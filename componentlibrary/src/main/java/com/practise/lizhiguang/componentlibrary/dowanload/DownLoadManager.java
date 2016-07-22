package com.practise.lizhiguang.componentlibrary.dowanload;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


/**
 * Created by lizhiguang on 16/7/21.
 */
public class DownLoadManager implements ServiceConnection {
    Context mContext;
    private static final String TAG = "DownLoadManager";
    public DownLoadManager(Context context) {
        mContext = context;
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
}
