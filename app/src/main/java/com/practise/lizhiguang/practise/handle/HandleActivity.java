package com.practise.lizhiguang.practise.handle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.practise.lizhiguang.practise.R;

public class HandleActivity extends AppCompatActivity implements View.OnClickListener {
    Button sendButton;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);
        bindWidget();
        init();
    }
    void bindWidget() {
        sendButton = (Button) findViewById(R.id.handle_send_button);
        sendButton.setOnClickListener(this);
    }

    void init() {
       handler = new MyHandle();
        count = 0;
    }
    int count;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handle_send_button:
                Message nM = new Message();
                nM.what = count;
                count++;
                handler.sendMessage(nM);
                Toast.makeText(this,"send"+count,Toast.LENGTH_SHORT).show();
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Handler tHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                //父ViewRootImp不是自己线程就会报错,所以说子线程也可以更新界面,但下面这个不行
//                                sendButton.setText("handle");
                                super.handleMessage(msg);
                            }
                        };
                        super.run();
                        tHandler.sendMessage(new Message());
                        Looper.loop();
                    }
                }.start();
                break;
        }
    }

    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d("myDebug","msg.what="+msg.what);
            super.handleMessage(msg);
        }
    }
}
