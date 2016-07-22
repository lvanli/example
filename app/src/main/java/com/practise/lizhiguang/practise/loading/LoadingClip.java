package com.practise.lizhiguang.practise.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.practise.lizhiguang.practise.R;

/**
 * Created by lizhiguang on 16/6/3.
 */
public class LoadingClip extends FrameLayout {
    ImageView imageView;
    Drawable clipDraw;
    Handler myHandle;
    int level;
    public LoadingClip(Context context) {
        this(context,null,0);
    }

    public LoadingClip(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingClip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindWidget(context);
        init();
    }
    void init() {
        myHandle = new MyHandle();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    level = (level+100)%10000;
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = level;
                    myHandle.sendMessage(message);
                    try {
                        Thread.sleep(20);
                    }catch (Exception e) {

                    }
                }
            }
        }.start();
    }

    void bindWidget(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_image,null);
        addView(view);
        imageView = (ImageView) view.findViewById(R.id.loading_image);
        clipDraw = imageView.getDrawable();
    }
    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                clipDraw.setLevel(msg.arg1);
            }
            super.handleMessage(msg);
        }
    }
}
