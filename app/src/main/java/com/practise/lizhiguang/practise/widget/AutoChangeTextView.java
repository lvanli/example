package com.practise.lizhiguang.practise.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lizhiguang on 16/6/26.
 */
public class AutoChangeTextView extends TextView {
    public AutoChangeTextView(Context context) {
        super(context);
    }

    public AutoChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoChangeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = getText().toString();
        Paint p = getPaint();
        Rect textRect = new Rect();
        p.getTextBounds(text,0,text.length(),textRect);

    }
}
