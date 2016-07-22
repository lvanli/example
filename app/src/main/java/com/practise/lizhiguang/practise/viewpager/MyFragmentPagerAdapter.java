package com.practise.lizhiguang.practise.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Vector;

/**
 * Created by lizhiguang on 16/6/15.
 */
class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    Vector<Fragment> allViews = new Vector<>();

    public Vector<Fragment> getAllViews() {
        return allViews;
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return allViews.get(position);
    }

    @Override
    public int getCount() {
        return allViews.size();
    }
}
