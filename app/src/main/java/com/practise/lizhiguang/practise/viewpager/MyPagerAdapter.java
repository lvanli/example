package com.practise.lizhiguang.practise.viewpager;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by lizhiguang on 16/6/15.
 */
class MyPagerAdapter extends PagerAdapter {
    Vector<TextView> allViews = new Vector<>();
    Vector<String> titles = new Vector<>();
    private static final String TAG = "MyPagerAdapter";
    public Vector<TextView> getAllViews() {
        return allViews;
    }

    public Vector<String> getTitles() {
        return titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(allViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(allViews.get(position));
        return allViews.get(position);
    }

    @Override
    public int getCount() {
        return allViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
