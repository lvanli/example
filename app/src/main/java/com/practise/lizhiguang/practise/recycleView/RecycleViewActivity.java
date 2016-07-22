package com.practise.lizhiguang.practise.recycleView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.practise.lizhiguang.practise.R;

public class RecycleViewActivity extends AppCompatActivity {
    RecyclerView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        bindWidget();
        init();
    }
    void bindWidget() {
        mainView = (RecyclerView) findViewById(R.id.recycle_main);
    }
    void init() {
        RecyclerAdapter myAdapter = new RecyclerAdapter(this);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        mainView.setAdapter(myAdapter);
        mainView.setItemAnimator(new DefaultItemAnimator());
        mainView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }
}
