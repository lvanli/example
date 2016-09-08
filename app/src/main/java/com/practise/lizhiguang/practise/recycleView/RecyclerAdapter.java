package com.practise.lizhiguang.practise.recycleView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practise.lizhiguang.practise.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiguang on 16/6/24.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "RecyclerAdapter";
    public static final int MODE_NORMAL = 0;
    public static final int MODE_FALL = 1;
    Context mContext;
    int mode = MODE_NORMAL;
    List<Integer> datas;
    List<Integer> mHeights;
    public RecyclerAdapter (Context context,List<Integer> data) {
        mContext = context;
        mHeights = new ArrayList<>(data.size());
        for (int i=0;i<data.size();i++) {
            mHeights.add((int)(Math.random()*300+200));
        }
        datas = data;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    public void addData(int data,int position) {
        datas.add(position,data);
        mHeights.add(position,(int)(Math.random()*300+200));
        notifyItemInserted(position);
    }
    public void deleteData(int position) {
        datas.remove(position);
        mHeights.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_list_item,parent,false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String text = position+"-"+datas.get(position);
        ((MyHolder)holder).tv.setText(text);
        if (mode == MODE_FALL) {
            ViewGroup.LayoutParams lp = ((MyHolder)holder).itemView.getLayoutParams();
            lp.height = mHeights.get(position);
            ((MyHolder)holder).itemView.setLayoutParams(lp);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.recycle_list_text);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
