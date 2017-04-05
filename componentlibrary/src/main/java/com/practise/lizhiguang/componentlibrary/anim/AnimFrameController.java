package com.practise.lizhiguang.componentlibrary.anim;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by lizhiguang on 2016/12/10.
 */

public class AnimFrameController {

    public static final String TAG = "AnimFrameController";
    /**
     * 是否已经开始绘制
     */
    private boolean mIsStart = false;
    /**
     * 绘制Handler
     */
    private Handler mDrawHandler = null;
    /**
     * 上次绘制时间
     */
    private long mLastDrawBeginTime = 0l;
    /**
     * 帧频，默认三十帧
     */
    private int mFtp = 30;
    /**
     * 刷新帧时间，默认三十帧
     */
    private long mIntervalTime = 1000 / 30;
    /**
     * 统计帧频所用
     */
    private int mFrameCount = 0;
    private long mStartTime = 0l;
    /**
     * IAnimFrameCallback
     */
    private IAnimFrameListener mListener = null;

    /**
     * 构造器
     */
    public AnimFrameController(IAnimFrameListener listener, Looper threadLooper) {
        if (listener == null) {
            throw new RuntimeException("AnimFrameController 构造参数listener 不能为null");
        }
        mListener = listener;
        mDrawHandler = new Handler(threadLooper);
    }

    /**
     * 开始渲染绘制动画
     */
    public void start() {
        if (!mIsStart) {
            mIsStart = true;
            mDrawHandler.post(mUpdateFrame);
        }
    }

    /**
     * 停止渲染绘制动画
     */
    public void stop() {
        if (mIsStart) {
            mIsStart = false;
        }
    }

    /**
     * 设置帧频,理想值，一般没那么精准
     */
    public void setFtp(int ftp) {
        if (ftp > 0) {
            mFtp = ftp;
            mIntervalTime = 1000 / mFtp;
        }
    }

    /**
     * 在每帧更新完毕时调用
     */
    public void updateFrame() {
        // 计算需要延迟的时间
        long passTime = System.currentTimeMillis() - mLastDrawBeginTime;
        final long delayTime = mIntervalTime - passTime;
        // 延迟一定时间去绘制下一帧
        if (delayTime > 0) {
            mDrawHandler.postDelayed(mUpdateFrame, delayTime);
        } else {
            mDrawHandler.post(mUpdateFrame);
        }
        // 统计帧频，如是未开始计时, 或帧时间太长(可能是由于动画暂时停止了,需要忽略这次计数据)则重置开始
        if (mStartTime == 0 || System.currentTimeMillis() - mStartTime >= 1100) {
            mStartTime = System.currentTimeMillis();
            mFrameCount = 0;
        } else {
            mFrameCount++;
            if (System.currentTimeMillis() - mStartTime >= 1000) {
                Log.d(TAG, "帧频为 ： " + mFrameCount + " 帧一秒 ");
                mStartTime = System.currentTimeMillis();;
                mFrameCount = 0;
            }
        }
    }

    /**
     * 刷新帧Runnable
     */
    private final Runnable mUpdateFrame = new Runnable() {

        @Override
        public void run() {
            if (!mIsStart) {
                return;
            }
            // 记录时间，每帧开始更新的时间
            mLastDrawBeginTime = System.currentTimeMillis();
            // 通知界面绘制帧
            mListener.onUpdateFrame();
        }
    };

    /**
     * 动画View要实现的接口
     */
    public interface IAnimFrameListener {
        /**
         * 需要刷新帧
         */
        public void onUpdateFrame();
        /**
         * 设置帧频
         */
        public void setFtp(int ftp);
    }
}
