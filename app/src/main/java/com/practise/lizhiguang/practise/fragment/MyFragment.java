package com.practise.lizhiguang.practise.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.practise.lizhiguang.practise.R;

/**
 * Created by lizhiguang on 16/6/20.
 */
public class MyFragment extends Fragment {
    private static final String TAG = "myDebug";
    private CallBack callBack;
    private ImageView mImageView;
    public interface CallBack {
        void onCall(String text);
    }
    public void setBackgroundColor(int color) {
        mImageView.setBackgroundColor(color);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (CallBack) context;
        Log.d(TAG,"Attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Create");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"CreateView");
        View view = inflater.inflate(R.layout.loading_image,null);
        mImageView = (ImageView) view.findViewById(R.id.loading_image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            Log.d(TAG,bundle.getString(TAG));
            callBack.onCall(bundle.getString(TAG,"from fragment"));
            Log.d(TAG,"ActivityCreated");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"Start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"Stop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"DestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"Detach");
    }
}
