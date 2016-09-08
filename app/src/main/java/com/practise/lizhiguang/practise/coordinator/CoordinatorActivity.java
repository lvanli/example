package com.practise.lizhiguang.practise.coordinator;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practise.lizhiguang.practise.R;
import com.practise.lizhiguang.practise.recycleView.RecyclerAdapter;

import java.util.ArrayList;

public class CoordinatorActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<View> viewContainter = new ArrayList<View>();
    private static final String TAG = "myDebug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        bindWidget();
        init();
    }
    void bindWidget() {
        viewPager = (ViewPager) findViewById(R.id.coordinator_viewpager);
    }
    void init() {
        ArrayList<Integer> datas = new ArrayList<>(10);
        for (int i=0;i<10;i++)
            datas.add(i);
        RecyclerAdapter myAdapter = new RecyclerAdapter(this,datas);
        RecyclerView view1 = new RecyclerView(this);
        view1.setLayoutManager(new LinearLayoutManager(this));
        view1.setAdapter(myAdapter);
        RecyclerView view2 = new RecyclerView(this);
        view2.setLayoutManager(new LinearLayoutManager(this));
        view2.setAdapter(myAdapter);
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewPager.setAdapter(new MyPagerAdapter());
    }
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewContainter.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainter.get(position));
            return viewContainter.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView(viewContainter.get(position));
        }
    }
}
