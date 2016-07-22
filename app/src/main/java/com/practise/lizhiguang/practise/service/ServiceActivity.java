package com.practise.lizhiguang.practise.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.practise.lizhiguang.practise.MainActivity;
import com.practise.lizhiguang.practise.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStartFront,buttonStopFront;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        bindWidget();
        init();
    }
    void bindWidget() {
        buttonStartFront = (Button) findViewById(R.id.service_front_start);
        buttonStopFront = (Button) findViewById(R.id.service_front_stop);
    }
    void init() {
        buttonStartFront.setOnClickListener(this);
        buttonStopFront.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_front_start:
                startService(new Intent(ServiceActivity.this,MyFrontService.class));
                break;
            case R.id.service_front_stop:
                stopService(new Intent(ServiceActivity.this, MyFrontService.class));
                break;
        }
    }
}
