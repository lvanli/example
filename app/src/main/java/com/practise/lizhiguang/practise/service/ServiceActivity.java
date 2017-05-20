package com.practise.lizhiguang.practise.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.practise.lizhiguang.practise.IServiceConnectInterface;
import com.practise.lizhiguang.practise.IServiceListenerInterface;
import com.practise.lizhiguang.practise.MainActivity;
import com.practise.lizhiguang.practise.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    Button buttonStartFront,buttonStopFront;
    TextView textView;
    IServiceConnectInterface mConnect;
    MyHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        bindWidget();
        init();
    }
    void bindWidget() {
        buttonStartFront = (Button) findViewById(R.id.service_front_start);
        buttonStopFront = (Button) findViewById(R.id.service_front_stop);
        textView = (TextView) findViewById(R.id.service_front_text);
    }
    void init() {
        buttonStartFront.setOnClickListener(this);
        buttonStopFront.setOnClickListener(this);
        handler = new MyHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_front_start:
                startService(new Intent(ServiceActivity.this,MyFrontService.class));
                bindService(new Intent(ServiceActivity.this,MyFrontService.class),this,0);
                break;
            case R.id.service_front_stop:
                unbindService(this);
                stopService(new Intent(ServiceActivity.this, MyFrontService.class));
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mConnect = IServiceConnectInterface.Stub.asInterface(service);
        try {
            textView.setText(mConnect.getData());
            mConnect.setListener(new IServiceListenerInterface.Stub() {
                @Override
                public void onAddData(String data) throws RemoteException {
                    Message message = Message.obtain();
                    message.obj = data;
                    handler.sendMessage(message);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mConnect = null;
    }
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = (String) msg.obj;
            textView.setText(textView.getText().toString() + text);
        }
    }
}
