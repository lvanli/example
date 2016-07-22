package com.practise.lizhiguang.practise.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.practise.lizhiguang.componentlibrary.adapter.SimpleBaseAdapter;
import com.practise.lizhiguang.componentlibrary.adapter.SimpleViewHolder;
import com.practise.lizhiguang.practise.R;

import java.util.ArrayList;
import java.util.List;

public class WidgetActivity extends AppCompatActivity {
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        bindWidget();
        init();
    }
    void bindWidget() {
        mListView = (ListView) findViewById(R.id.widget_list_view);
    }
    void init() {
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
}
