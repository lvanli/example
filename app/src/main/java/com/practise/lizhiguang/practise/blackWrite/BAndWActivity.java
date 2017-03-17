package com.practise.lizhiguang.practise.blackWrite;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.practise.lizhiguang.componentlibrary.widget.BWClickImpl;
import com.practise.lizhiguang.practise.R;

public class BAndWActivity extends AppCompatActivity {
    private BWClickImpl bwClick;
    private MyGameOverListener listener;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_and_w);
        listener = new MyGameOverListener();
        bwClick = (BWClickImpl) findViewById(R.id.bw_impl);
        bwClick.setOnGameOverListener(listener);
        context = this;
    }

    private class MyGameOverListener implements BWClickImpl.OnGameOverListener {

        @Override
        public void onSuccess() {
            restart("闯关成功");
        }

        @Override
        public void onFail() {
            restart("闯关失败");
        }
    }

    void restart(String tip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bwClick.start();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.setTitle(tip);
        builder.create().show();
    }
}
