package com.practise.lizhiguang.practise.loading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.practise.lizhiguang.practise.R;

public class LoadingActivity extends AppCompatActivity {
    ProgressBar loadingRotate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        bindWidget();
        init();
    }
    void bindWidget() {
        loadingRotate = (ProgressBar) findViewById(R.id.loading_rotate);
    }
    void init(){
//        Animation anim = AnimationUtils.loadAnimation(this,R.anim.loading_rotate);
//        Interpolator interpolator = new LinearInterpolator();
//        anim.setInterpolator(interpolator);
//        loadingRotate.setAnimation(anim);
//        loadingRotate.setInterpolator(this,android.animation);
    }
}
