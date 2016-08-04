package com.practise.lizhiguang.practise.download;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.practise.lizhiguang.componentlibrary.dowanload.DownLoadManager;
import com.practise.lizhiguang.componentlibrary.dowanload.FileInfo;
import com.practise.lizhiguang.practise.R;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{
    EditText inputText;
    Button startButton,pauseButton,restartButton;
    DownLoadManager manager;
    private static final String TAG = "DownloadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        bindWidget();
        init();
    }
    private void bindWidget() {
        inputText = (EditText) findViewById(R.id.download_url);
        startButton = (Button) findViewById(R.id.download_start);
        pauseButton = (Button) findViewById(R.id.download_pause);
        restartButton = (Button) findViewById(R.id.download_restart);
    }
    private void init () {
        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        manager = new DownLoadManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_start:
                String url = inputText.getText().toString();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setUrl(url);
                fileInfo.setName(DownLoadManager.getNameByUrl(url));
                Log.d(TAG, "onClick: url="+url+",name="+fileInfo.getName());
                manager.downloadWithFileInfo(fileInfo);
                break;
            case R.id.download_pause:
                sendBroadcast(new Intent(DownLoadManager.ACTION_PAUSE));
                break;
            case R.id.download_restart:
                sendBroadcast(new Intent(DownLoadManager.ACTION_RESTART));
                break;
        }
    }
}
