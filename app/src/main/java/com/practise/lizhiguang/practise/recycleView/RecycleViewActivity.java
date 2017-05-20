package com.practise.lizhiguang.practise.recycleView;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mrw.wzmrecyclerview.AutoLoad.AutoLoadFooterCreator;
import com.mrw.wzmrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.mrw.wzmrecyclerview.HeaderAndFooter.OnItemLongClickListener;
import com.mrw.wzmrecyclerview.PullToLoad.OnLoadListener;
import com.mrw.wzmrecyclerview.PullToRefresh.OnRefreshListener;
import com.mrw.wzmrecyclerview.SimpleAdapter.SimpleAdapter;
import com.mrw.wzmrecyclerview.SimpleAdapter.ViewHolder;
import com.practise.lizhiguang.practise.R;
import com.practise.lizhiguang.practise.util.glide.GrayscaleTransformation;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {
    RecyclerView mainView;
    DividerItemDecoration mDecotarion = null;
    RecyclerAdapter mAdapter = null;
    Handler mHandler;
    AutoLoadRecyclerView recyclerView;
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
        mHandler = new Handler();
        mAdapter = new RecyclerAdapter(this,getData());
        mAdapter.setMode(RecyclerAdapter.MODE_FALL);
        mainView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mainView.setAdapter(mAdapter);
        mainView.setItemAnimator(new DefaultItemAnimator());
        mDecotarion = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        mainView.addItemDecoration(mDecotarion);
        mainView.setVisibility(View.GONE);

        final ArrayList<String> imgs = new ArrayList<>();
        imgs.add("http://img1.3lian.com/2015/w7/78/d/81.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/85.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/85.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/85.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/85.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/85.jpg");
        imgs.add("http://img1.3lian.com/2015/w7/78/d/82.jpg");
        recyclerView = (AutoLoadRecyclerView) findViewById(R.id.recycle_auto_load);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setAdapter(new SimpleAdapter<String>(this, imgs, R.layout.recycler_auto_load_item) {
//            float tr = 0;
//            @Override
//            protected void onBindViewHolder(ViewHolder holder, String data) {
//                Glide.with(mContext).load(data).placeholder(R.color.gray).dontAnimate().dontTransform().bitmapTransform(new GrayscaleTransformation(mContext,tr))
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.<ImageButton>getView(R.id.recycle_auto_load_image));
//                Glide.with(mContext).load(data).bitmapTransform(new GrayscaleTransformation(mContext,tr)).into(holder.<ImageButton>getView(R.id.recycle_auto_load_image));
//                tr += 0.1;
//                if (tr >= 0.5)
//                    tr = 0;
//                holder.<TextView>getView(R.id.recycle_auto_load_text).setText(data);
//            }
//        });
        recyclerView.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/83.jpg");
                        mAdapter.addData(100,mAdapter.getItemCount());
                        recyclerView.completeLoad();
                    }
                },2000);
            }
        });
        recyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT,String.valueOf(mAdapter.getItemData(position)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                return true;
            }
        });
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onStartRefreshing() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        imgs.clear();
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        imgs.add("http://img1.3lian.com/2015/w7/78/d/84.jpg");
//                        Glide.with(RecycleViewActivity.this).load("http://img1.3lian.com/2015/w7/78/d/85.jpg").downloadOnly(200,200);
                        mAdapter.addData(99,0);
                        recyclerView.completeRefresh();
                    }
                },2000);
            }
        });
    }

    List<Integer> getData() {
        ArrayList<Integer> data = new ArrayList<>(40);
        for (int i=0;i<40;i++) {
            data.add(i);
        }
        return  data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycle_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recycle_menu_list:
                if (mDecotarion != null)
                    mainView.removeItemDecoration(mDecotarion);
                mDecotarion = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
                mainView.setLayoutManager(new LinearLayoutManager(this));
                mainView.addItemDecoration(mDecotarion);
                mAdapter.setMode(RecyclerAdapter.MODE_NORMAL);
                break;
            case R.id.recycle_menu_grid:
                if (mDecotarion != null)
                    mainView.removeItemDecoration(mDecotarion);
                mDecotarion = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST);
                mainView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));
                mainView.addItemDecoration(mDecotarion);
                mAdapter.setMode(RecyclerAdapter.MODE_NORMAL);
                break;
            case R.id.recycle_menu_fall:
                mAdapter.setMode(RecyclerAdapter.MODE_FALL);
                if (mDecotarion != null)
                    mainView.removeItemDecoration(mDecotarion);
                mainView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                break;
            case R.id.recycle_menu_add:
                mAdapter.addData(99,1);
                break;
            case R.id.recycle_menu_delete:
                mAdapter.deleteData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
