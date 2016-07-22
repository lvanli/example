package com.practise.lizhiguang.componentlibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lizhiguang on 16/7/12.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    private Context mContext;

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }

    private List<T> mList;
    private int mLayoutId;
    public SimpleBaseAdapter(Context context,int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleViewHolder holder = SimpleViewHolder.getHolder(mContext,mLayoutId,position,convertView);
        getInfo(holder);
        return holder.getConvertView();
    }

    public abstract void getInfo(SimpleViewHolder holder);
}
