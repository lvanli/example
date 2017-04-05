package com.practise.lizhiguang.componentlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.practise.lizhiguang.componentlibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ASUS User on 2017/3/16.
 */

public class BWClickImpl extends View {
    private static final String TAG = "BWClickImpl";
    private Context context;
    private Boolean isReturn;
    private FreshHandler myHandler;
    private Timer timer;
    private int offset;
    private static final int MAX_BUFFER = 10;
    private Paint untouchedPaint, touchedPaint;
    private int width, height, lineWidth;
    private int maxLine;
    private int numbers[] = new int[MAX_BUFFER];
    private boolean statue[] = new boolean[MAX_BUFFER];
    private int pNum;
    private int lumpHeight;
    private int rollHeight;
    private int defRollHeight;
    private boolean debug;
    private OnGameOverListener gameOverListener;

    public BWClickImpl(Context context) {
        this(context, null);
    }

    public BWClickImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BWClickImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BWClickImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        this.context = context;
        untouchedPaint = new Paint();
        touchedPaint = new Paint();
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.BWClickImpl);
            touchedPaint.setColor(attr.getColor(R.styleable.BWClickImpl_touchedColor,Color.GRAY));
            untouchedPaint.setColor(attr.getColor(R.styleable.BWClickImpl_untouchedColor,Color.BLACK));
            maxLine = attr.getInteger(R.styleable.BWClickImpl_maxLine,4);
            defRollHeight = attr.getInteger(R.styleable.BWClickImpl_beginHeight,-200);
        }
        else {
            untouchedPaint.setColor(Color.BLACK);
            touchedPaint.setColor(Color.GRAY);
            maxLine = 4;
            defRollHeight = -200;
        }
        gameOverListener = null;
        if (debug)
            Log.d(TAG, "init: max=" + MAX_BUFFER);
        myHandler = new FreshHandler();
        isReturn = false;
        debug = false;
        start();
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void start() {
        pNum = 0;
        synchronized (isReturn) {
            isReturn = false;
        }
        int tempNum;
        numbers[0] = (int) (Math.random() * maxLine);
        statue[0] = false;
        for (int i = 1; i < MAX_BUFFER; i++) {
            tempNum = (int) (Math.random() * maxLine);
            if (tempNum == numbers[i - 1])
                i--;
            else {
                numbers[i] = tempNum;
                statue[i] = false;
            }
            if (debug)
                Log.d(TAG, "init: numbers=" + numbers[i]);
        }
        rollHeight = defRollHeight;
        offset = 2;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(0);
            }
        }, 0, 40);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != View.VISIBLE)
            timer.cancel();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        lineWidth = width / maxLine;
        lumpHeight = lineWidth * 4 / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //画竖线
        for (int i = 1; i < maxLine; i++)
            canvas.drawLine(lineWidth * i, 0, lineWidth * i, height, untouchedPaint);
        int c;
        Paint paint;
        for (int i = 0; i < MAX_BUFFER && height - lumpHeight * (i - 1) + rollHeight > 0; i++) {
            if (statue[(pNum + i) % MAX_BUFFER])
                paint = touchedPaint;
            else
                paint = untouchedPaint;
            c = numbers[(pNum + i) % MAX_BUFFER];
            //画横线
            canvas.drawLine(0, height - lumpHeight * (i + 1) + rollHeight, width, height - lumpHeight * (i + 1) + rollHeight, paint);
            if (debug)
                Log.d(TAG, "onDraw: " + (c * lineWidth) + "," + (height - lumpHeight * (i + 1) + rollHeight) + "," + ((c + 1) * lineWidth) + "," + (height - lumpHeight * i + rollHeight));
            //画方块
            canvas.drawRect(c * lineWidth, height - lumpHeight * (i + 1) + rollHeight, (c + 1) * lineWidth, height - lumpHeight * i + rollHeight, paint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int num = (int) (x / lineWidth);
            int index = (int) (height - y + rollHeight) / lumpHeight;
            if (numbers[(pNum + index) % MAX_BUFFER] == num) {
                statue[(pNum + index) % MAX_BUFFER] = true;
            } else {
                synchronized (isReturn) {
                    if (!isReturn) {
                        timer.cancel();
                        if (gameOverListener != null) {
                            gameOverListener.onFail();
                            isReturn = true;
                        } else
                            Toast.makeText(context, "闯关失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (debug)
                Log.d(TAG, "onTouchEvent: num=" + num + ",now=" + (numbers[(pNum + index) % MAX_BUFFER]) + ",pNum=" + pNum + ",index=" + index + ",height=" + y);
        }
        return super.onTouchEvent(event);
    }

    public void setOnGameOverListener(OnGameOverListener listener) {
        gameOverListener = listener;
    }

    public interface OnGameOverListener {
        void onSuccess();

        void onFail();
    }

    class FreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (debug)
                Log.d(TAG, "handleMessage: roll=" + rollHeight);
            rollHeight += (++offset) / 10;
            if (offset > lumpHeight*2) {
                synchronized (isReturn) {
                    if (!isReturn) {
                        timer.cancel();
                        if (gameOverListener != null) {
                            gameOverListener.onSuccess();
                            isReturn = true;
                        } else {
                            Toast.makeText(context, "闯关成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return;
            }
            if (rollHeight >= lumpHeight) {
                rollHeight = rollHeight - lumpHeight;
                int tempNum;
                while (true) {
                    tempNum = (int) (Math.random() * maxLine);
                    if (tempNum != numbers[(pNum - 1 + MAX_BUFFER) % MAX_BUFFER]) {
                        numbers[pNum] = tempNum;
                        statue[pNum] = false;
                        break;
                    }
                }
                pNum = (pNum + 1) % MAX_BUFFER;
            }
            if (rollHeight >= 0 && rollHeight <= offset / 10) {
                if (!statue[pNum]) {
                    synchronized (isReturn) {
                        if (!isReturn) {
                            timer.cancel();
                            if (gameOverListener != null) {
                                gameOverListener.onFail();
                                isReturn = true;
                            } else
                                Toast.makeText(context, "闯关失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return;
                }
            }
            invalidate();
        }
    }
}
