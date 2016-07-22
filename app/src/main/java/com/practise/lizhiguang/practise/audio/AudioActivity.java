package com.practise.lizhiguang.practise.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.practise.lizhiguang.practise.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener, AudioManager.OnAudioFocusChangeListener {

    Button playButton,stopButton,abandonButton;
    MediaPlayer mediaPlayer;
    AudioManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        bindWidget();
        init();
    }
    void bindWidget() {
        playButton = (Button) findViewById(R.id.audio_play_button);
        stopButton = (Button) findViewById(R.id.audio_stop_button);
        abandonButton = (Button) findViewById(R.id.audio_abandon_button);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        abandonButton.setOnClickListener(this);
    }
    void init() {
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor descriptor = getAssets().openFd("test.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audio_play_button:
                manager.requestAudioFocus(this,AudioManager.STREAM_ALARM,AudioManager.AUDIOFOCUS_GAIN);
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.audio_stop_button:
                mediaPlayer.stop();
                break;
            case R.id.audio_abandon_button:
                manager.abandonAudioFocus(this);
                break;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        Log.d("myDebug","focusChange="+focusChange);
    }
}
