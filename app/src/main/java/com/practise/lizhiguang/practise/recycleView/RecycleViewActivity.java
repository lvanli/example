package com.practise.lizhiguang.practise.recycleView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.practise.lizhiguang.practise.R;

public class RecycleViewActivity extends AppCompatActivity {
    RecyclerView mainView;
    DividerItemDecoration mDecotarion = null;
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
        mDecotarion = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        mainView.addItemDecoration(mDecotarion);
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
                break;
            case R.id.recycle_menu_grid:
                if (mDecotarion != null)
                    mainView.removeItemDecoration(mDecotarion);
                mDecotarion = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST);
                mainView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));
                mainView.addItemDecoration(mDecotarion);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
