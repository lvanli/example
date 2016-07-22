package com.practise.lizhiguang.practise.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.practise.lizhiguang.practise.R;

public class FragmentActivity extends AppCompatActivity implements MyFragment.CallBack{
    private static final String TAG="myDebug";
    private TextView mainText;
    private MyFragment mFragment;
    private void init() {
        MyFragment fragment = new MyFragment();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(TAG,"from Activity");
        fragment.setArguments(bundle);
        manager.beginTransaction().add(R.id.fragment_main_dynamic_layout,fragment).commit();
        mFragment.setBackgroundColor(Color.RED);
    }
    private void bindWidget() {
        mainText = (TextView) findViewById(R.id.fragment_main_text);
        mFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_static_fragment);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        bindWidget();
        init();
        Log.d(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    public void onCall(String text) {
        mainText.setText(text);
    }
}
