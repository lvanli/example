package com.practise.lizhiguang.practise.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by lizhiguang on 16/6/15.
 */
class MyPagerAdapter extends PagerAdapter {

    ArrayList<String> texts = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    private static final String TAG = "MyPagerAdapter";
    private Context mContext;
    private ViewPager mViewPager;

    MyPagerAdapter(Context context) {
        mContext = context;
    }
    public void setMainView(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position%titles.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = new TextView(mContext);
        textView.setText(texts.get(position%texts.size()));
        container.addView(textView, ViewPager.LayoutParams.WRAP_CONTENT,ViewPager.LayoutParams.WRAP_CONTENT);
        return textView;
    }

    @Override
    public int getCount() {
        return texts.size() * 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        // ViewPager的更新即将完成，替换position，以达到无限循环的效果
        if (position == getCount() / 3 - 1) {
            Log.d(TAG, "finishUpdate: po="+position+",new="+(getCount() / 3 * 2 - 1));
            position = getCount() / 3 * 2 - 1;
            mViewPager.setCurrentItem(position, false);
        } else if (position == getCount() / 3 * 2) {
            position = getCount() / 3;
            mViewPager.setCurrentItem(position, false);
        }
        super.finishUpdate(container);
    }
}
