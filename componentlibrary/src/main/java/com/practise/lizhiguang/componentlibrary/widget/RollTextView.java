package com.practise.lizhiguang.componentlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lizhiguang on 16/9/15.
 */
public class RollTextView extends TextView {
    private String rollText = "";
    private static final String TAG = "RollTextView";
    public RollTextView(Context context) {
        super(context);
        init();
    }

    public RollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RollTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    private Timer mTimer = null;
    private TimerTask mTask = null;
    public void init() {
        setMaxLines(1);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                if (rollText.isEmpty())
                    return;
                int width = getWidth();
                Paint p = getPaint();
                Rect rect = new Rect();
                p.getTextBounds(rollText,0,rollText.length(),rect);
                int textWidth = rect.width();
//                Log.d(TAG, "onDraw: textwidth="+textWidth+",width="+width);
                if (width >= textWidth) {
                    return;
                }
                int part = (int)(width * 1.0 / textWidth * rollText.length());
                maxCount = textWidth / width + (textWidth%width == 0?0:1);
//                Log.d(TAG, "onDraw: part="+part+",maxCount="+maxCount+",count="+count);
                String rText = "";
                if (count == maxCount - 1) {
                    rText = rollText.substring(part * count, rollText.length());
                }
                else {
                    rText = rollText.substring(part * count, part * (count + 1));
                }
                myHandle.obtainMessage(0,rText).sendToTarget();
                if (count >= maxCount -1 )
                    count = 0;
                else
                    count++;
            }
        };
        mTimer.schedule(mTask,1000,2000);
    }
    private Handler myHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                setText((String)msg.obj);
            }
        }
    };
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    public void setRollText(String rollText) {
        this.rollText = rollText;
        setText(rollText);
    }

    private int count = 0;
    private int maxCount = 0;
    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d(TAG, "onDraw: text="+getText().toString());
        super.onDraw(canvas);
    }
    public void onDestory() {
        mTask.cancel();
        mTimer.cancel();
    }
}
