package com.practise.lizhiguang.componentlibrary.adapter;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lizhiguang on 16/7/12.
 * holder的实现类,可以添加多种控件的支持
 */
public class SimpleViewHolder {
    private SparseArray <View> mViews;
    private View mConvertView;
    private int mPosition;
    public SimpleViewHolder(Context context,int layoutId) {
        mConvertView = LayoutInflater.from(context).inflate(layoutId,null);
        this.mViews = new SparseArray<>();
        mConvertView.setTag(this);
    }
    public static SimpleViewHolder getHolder(Context context,int layoutId,int position, View convertView) {
        SimpleViewHolder holder;
        if (convertView == null) {
            holder = new SimpleViewHolder(context,layoutId);
        }
        else {
            holder = (SimpleViewHolder) convertView.getTag();
        }
        holder.mPosition = position;
        return holder;
    }

    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = mConvertView.findViewById(resId);
            mViews.append(resId,view);
        }
        return (T) view;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public View getConvertView() {
        return mConvertView;
    }
    public SimpleViewHolder setText(int resId,String text) {
        ((TextView)getView(resId)).setText(text);
        return this;
    }
}
