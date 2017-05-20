package com.practise.lizhiguang.practise.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.practise.lizhiguang.practise.IServiceConnectInterface;
import com.practise.lizhiguang.practise.IServiceListenerInterface;
import com.practise.lizhiguang.practise.MainActivity;
import com.practise.lizhiguang.practise.R;

/**
 * Created by lizhiguang on 16/6/17.
 */
public class MyFrontService extends Service {

    IServiceListenerInterface mListener;
    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        //创建最少要有的数据
        builder.setContentTitle("标题").setContentText("内容").setSmallIcon(R.drawable.loading_progress);
        //点击事件
        builder.setContentIntent(PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0));
        //autoCancel这么做没用,ticker就是顶端闪过的提示消息
        builder.setAutoCancel(true).setTicker("WTF");
        startForeground(1,builder.build());
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                boolean isRun = false;
                while (true) {
                    if (mListener != null) {
                        try {
                            mListener.onAddData(String.valueOf(i));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        i++;
                        isRun = true;
                    } else if (isRun) {
                        break;
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    class MyConnect extends IServiceConnectInterface.Stub {

        @Override
        public String getData() throws RemoteException {
            return "from server";
        }

        @Override
        public void setListener(IServiceListenerInterface listener) throws RemoteException {
            mListener = listener;
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyConnect();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mListener = null;
        return super.onUnbind(intent);
    }
}
