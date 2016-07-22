package com.practise.lizhiguang.practise.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Vector;

/**
 * Created by lizhiguang on 16/6/15.
 */
public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    Vector <Fragment> allViews = new Vector<>();
    public MyFragmentStatePagerAdapter(FragmentManager fm) {
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
