package com.practise.lizhiguang.practise.viewpager;

import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.practise.lizhiguang.practise.R;

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
    void bindWidget () {
        mainViewPager = (ViewPager) findViewById(R.id.viewpager_main);
    }
    void init () {
        mainViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mainViewPager.setAdapter(getNormalAdapter());
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
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        TextView textView = new TextView(this);
        TextView textView2 = new TextView(this);
        TextView textView3 = new TextView(this);
        TextView textView4 = new TextView(this);
        TextView textView5 = new TextView(this);
        TextView textView6 = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText("11111111");
        textView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView2.setText("22222222");
        textView3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView3.setText("33333333");
        textView4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView4.setText("4444444");
        textView5.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView5.setText("55555555");
        textView6.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView6.setText("666666666");
        myPagerAdapter.allViews.add(textView);
        myPagerAdapter.allViews.add(textView2);
        myPagerAdapter.allViews.add(textView3);
        myPagerAdapter.allViews.add(textView4);
        myPagerAdapter.allViews.add(textView5);
        myPagerAdapter.allViews.add(textView6);
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
