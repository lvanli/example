package com.practise.lizhiguang.practise.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.practise.lizhiguang.componentlibrary.adapter.SimpleBaseAdapter;
import com.practise.lizhiguang.componentlibrary.adapter.SimpleViewHolder;
import com.practise.lizhiguang.componentlibrary.widget.RollTextView;
import com.practise.lizhiguang.practise.R;

import java.util.ArrayList;
import java.util.List;

public class WidgetActivity extends AppCompatActivity {
    ListView mListView;
    RollTextView mRollTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        bindWidget();
        init();
    }
    void bindWidget() {
        mListView = (ListView) findViewById(R.id.widget_list_view);
        mRollTextView = (RollTextView) findViewById(R.id.widget_roll_text_view);
    }
    void init() {
        mRollTextView.setRollText("这是一条长测试这是一条长测试这是一条长测试这是一条长测试这是一条长测试");
        SimpleBaseAdapter adapter = new SimpleBaseAdapter<Integer>(this,android.R.layout.simple_list_item_1) {
            @Override
            public void getInfo(SimpleViewHolder holder) {
                holder.setText(android.R.id.text1,""+getList().get(holder.getPosition()));
            }
        };
        List<Integer> data = new ArrayList<>(20);
        for (int i=0;i<20;i++)
            data.add(i);
        adapter.setList(data);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        mRollTextView.onDestory();
        super.onPause();
    }
}
