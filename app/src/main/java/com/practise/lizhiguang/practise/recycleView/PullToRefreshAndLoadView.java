package com.practise.lizhiguang.practise.recycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lizhiguang on 2017/4/6.
 */

public class PullToRefreshAndLoadView extends RecyclerView {
    public PullToRefreshAndLoadView(Context context) {
        this(context,null);
    }

    public PullToRefreshAndLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PullToRefreshAndLoadView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private static final int STATUE_NONE = 0;
    private static final int STATUE_PULL = 1;
    private static final int STATUE_RELEASE = 2;
    private static final int STATUE_DOWN = 3;
    private static final int STATUE_PULL_DOWN = 4;
    private static final int STATUE_RELEASE_DOWN = 5;
    private static final int STATUE_TOP = 6;
    private static final int LAYOUT_LINEAR = 1;
    private static final int LAYOUT_GRID = 2;
    private static final int LAYOUT_STAG = 3;
    private int mStatue;
    private int mLayout;
    private int mPullDistance;
    private RefreshAndLoadListener mRefreshAndLoadListener;

    public void setRefreshAndLoadListener(RefreshAndLoadListener refreshAndLoadListener) {
        this.mRefreshAndLoadListener = refreshAndLoadListener;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof StaggeredGridLayoutManager) {
            mLayout = LAYOUT_STAG;
        } else if (layout instanceof GridLayoutManager) {
            mLayout = LAYOUT_GRID;
        } else if (layout instanceof LinearLayoutManager) {
            mLayout = LAYOUT_LINEAR;
        }
    }

    private void init() {
        mStatue = STATUE_NONE;
        mPullDistance = 100;
    }
    private boolean isTop() {
        return !ViewCompat.canScrollVertically(this, -1);
    }
    private boolean isDown() {
        return !ViewCompat.canScrollVertically(this, 1);
    }
    private void pull(float distance) {
        if (distance > 0) {
            if (mLayout == LAYOUT_STAG) {
                int count = ((StaggeredGridLayoutManager)getLayoutManager()).getSpanCount();
                for (int i=0;i<count;i++) {

                }
            }
        } else {

        }
    }
    private void onRefresh() {

    }
    private void onLoad() {

    }
    float startY;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (e.getY() > startY && isTop()) {
                    mStatue = STATUE_TOP;
                } else if (e.getY() < startY && isDown()) {
                    mStatue = STATUE_DOWN;
                }
                if (mStatue == STATUE_TOP || mStatue == STATUE_DOWN) {
                    pull(e.getY() - startY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mStatue == STATUE_TOP) {
                    if (e.getY() - startY > mPullDistance) {
                        mStatue = STATUE_PULL;
                        if (mRefreshAndLoadListener != null)
                            mRefreshAndLoadListener.onRefresh();
                        onRefresh();
                    }
                } else if (mStatue == STATUE_DOWN) {
                    if (startY - e.getY() > mPullDistance) {
                        mStatue = STATUE_PULL_DOWN;
                        if (mRefreshAndLoadListener != null)
                            mRefreshAndLoadListener.onLoad();
                        onLoad();
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }
    public interface  RefreshAndLoadListener {
        void onRefresh();
        void onLoad();
    }
}
