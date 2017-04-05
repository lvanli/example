package com.practise.lizhiguang.componentlibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by lizhiguang on 16/10/24.
 */
public class GuaGuaView extends ImageView {
    private static final String TAG = "GuaGuaView";
    public GuaGuaView(Context context) {
        this(context,null);
    }

    public GuaGuaView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuaGuaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Path mPath;
    private int mCurrentX,mCurrentY;
    private boolean mOver;
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPath = new Path();
        mOver = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width >0 && height > 0) {
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.GRAY);
            int stroke = width > height ? height/5:width/5;
            mPaint.setStrokeWidth(stroke);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null && !mOver) {
            mCanvas.drawPath(mPath,mPaint);
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentX = x;
                mCurrentY = y;
                mPath.moveTo(mCurrentX,mCurrentY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mCurrentX - x) > 3 || Math.abs(mCurrentY - y) > 3) {
                    mPath.lineTo(x,y);
                    mCurrentX = x;
                    mCurrentY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mCheckRunnable).start();
                break;
        }
        invalidate();
        return true;
    }

    private Runnable mCheckRunnable = new Runnable() {
        @Override
        public void run() {
            int h = getMeasuredHeight();
            int w = getMeasuredWidth();
            int[] pixels = new int[h*w];
            int count = 0;
            mBitmap.getPixels(pixels,0,w,0,0,w,h);
            for (int i=0;i<pixels.length;i++) {
                if (pixels[i] != 0)
                    count++;
            }
            if (count * 100 /w/h < 50)
                mOver = true;
        }
    };
}
