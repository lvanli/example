package com.practise.lizhiguang.componentlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lizhiguang on 2016/11/10.
 */

public class MicrophoneAnimation extends View {
    public MicrophoneAnimation(Context context) {
        this(context,null);
    }

    public MicrophoneAnimation(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MicrophoneAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MicrophoneAnimation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    Paint mPaint;
    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        now = 1f;
        up = false;
        start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    float now;
    float mix = 0.1f;
    boolean up;

    public void start() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (up) {
                    if (now >=1.0f)
                        up = false;
                    else {
                        now += 0.1f;
                    }
                }
                else {
                    if (now<=mix)
                        up = true;
                    else {
                        now -=0.1f;
                    }
                }
                postInvalidate();
            }
        },50,50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getBackground();
        drawable.draw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        Path path = new Path();
        path.moveTo(0,height/2);
        int hw = width / 2;
        int hh = height / 2;

        float max1 = 1.0f / 12.0f;
        float may1 = 1.0f / 6.0f;
        float max2 = 1.0f / 3.0f;
        float may2 = 5.0f / 6.0f;

        float x1 = max1*now,y1=may1*now,x2=max2*now,y2=may2*now;
        path.cubicTo(hw*(1 - x1),hh*(1 - y1),hw*(1 - x2),hh*(1 - y2),hw,hh*(1-now));
        path.cubicTo(hw*(1 + x2),hh*(1 - y2),hw*(1 + x1),hh*(1 - y1),width,hh);
        path.cubicTo(hw*(1 + x1),hh*(1 + y1),hw*(1 + x2),hh*(1 + y2),hw,height/2*(1+now));
        path.cubicTo(hw*(1 - x2),hh*(1 + y2),hw*(1 - x1),hh*(1 + y1),0,hh);

        path.close();
        canvas.drawPath(path,mPaint);
    }
}
