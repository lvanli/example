package com.practise.lizhiguang.practise.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.practise.lizhiguang.practise.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewpagerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "myDebug";
    ViewPager mainViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        bindWidget();
        init();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() + 1);
            }
            super.handleMessage(msg);
        }
    };
    MyPagerAdapter adapter;
    void bindWidget () {
        mainViewPager = (ViewPager) findViewById(R.id.viewpager_main);
    }
    void init () {
        mainViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        adapter = (MyPagerAdapter) getNormalAdapter();
        adapter.setMainView(mainViewPager);
        mainViewPager.setAdapter(adapter);
        mainViewPager.setCurrentItem(mainViewPager.getAdapter().getCount()/3);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        },1000,2000);
    }
    public MyFragmentStatePagerAdapter getFragmentStateAdapter() {
        //自动销毁fragment,加载多个fragment内存压力不大,但是由于每次都要加载都要创建,对性能要求高
        MyFragmentStatePagerAdapter myFragmentStatePagerAdapter;
        ViewPagerFragment fragment1 = new ViewPagerFragment("111111111",this);
        ViewPagerFragment fragment2 = new ViewPagerFragment("222222222",this);
        myFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
        myFragmentStatePagerAdapter.allViews.add(fragment1);
        myFragmentStatePagerAdapter.allViews.add(fragment2);
        return myFragmentStatePagerAdapter;
    }
    public MyFragmentPagerAdapter getFragmentAdapter() {
        //全部加载到内存中,对内存压力大,但是因为不用每次创建所以性能好
        ViewPagerFragment fragment1 = new ViewPagerFragment("111111111",this);
        ViewPagerFragment fragment2 = new ViewPagerFragment("222222222",this);
        MyFragmentPagerAdapter myFragmentPagerAdapter;
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.allViews.add(fragment1);
        myFragmentPagerAdapter.allViews.add(fragment2);
        return myFragmentPagerAdapter;
    }
    public PagerAdapter getNormalAdapter() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(this);
        myPagerAdapter.getTexts().add("111111");
        myPagerAdapter.getTexts().add("222222");
        myPagerAdapter.getTexts().add("333333");
        myPagerAdapter.getTexts().add("444444");
        myPagerAdapter.getTexts().add("555555");
        myPagerAdapter.getTexts().add("666666");
        myPagerAdapter.getTitles().add("1");
        myPagerAdapter.getTitles().add("2");
        myPagerAdapter.getTitles().add("3");
        myPagerAdapter.getTitles().add("4");
        myPagerAdapter.getTitles().add("5");
        myPagerAdapter.getTitles().add("6");
        return myPagerAdapter;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,((Button)v).getText().toString(),Toast.LENGTH_SHORT).show();
    }

}
