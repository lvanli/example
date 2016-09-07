package com.practise.lizhiguang.practise.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practise.lizhiguang.practise.R;

/**
 * Created by lizhiguang on 16/6/24.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "RecyclerAdapter";
    public static final int MODE_NORMAL = 0;
    public static final int MODE_FALL = 1;
    Context mContext;
    int mode = MODE_NORMAL;
    public RecyclerAdapter (Context context) {
        mContext = context;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_list_item,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).tv.setText(""+position);
        if (mode == MODE_FALL) {
            ((MyHolder)holder).tv.setHeight((int)(Math.random()*100%80+20));
            Log.d(TAG, "onBindViewHolder: "+(int)(Math.random()*100%80+20));
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
        return 40;
    }
}
