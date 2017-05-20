package com.practise.lizhiguang.practise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.practise.lizhiguang.componentlibrary.dowanload.DownloadService;
import com.practise.lizhiguang.practise.audio.AudioActivity;
import com.practise.lizhiguang.practise.blackWrite.BAndWActivity;
import com.practise.lizhiguang.practise.coordinator.CoordinatorActivity;
import com.practise.lizhiguang.practise.download.DownloadActivity;
import com.practise.lizhiguang.practise.floatMenu.FloatMenuActivity;
import com.practise.lizhiguang.practise.fragment.FragmentActivity;
import com.practise.lizhiguang.practise.handle.HandleActivity;
import com.practise.lizhiguang.practise.layout.TabHostActivity;
import com.practise.lizhiguang.practise.loading.LoadingActivity;
import com.practise.lizhiguang.practise.network.NetworkActivity;
import com.practise.lizhiguang.practise.recycleView.RecycleViewActivity;
import com.practise.lizhiguang.practise.service.MyFrontService;
import com.practise.lizhiguang.practise.service.ServiceActivity;
import com.practise.lizhiguang.practise.viewpager.ViewpagerActivity;
import com.practise.lizhiguang.practise.widget.WidgetActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//http://www.imooc.com/mobile/mukewang.apk
        //http://p.gdown.baidu.com/5ee065e58c9e6cb1183526d39954305014b8f4c3019069e97d8e7370af1e7f5581f33239df1852e913809f8c37646b6bdb479dd357726d35d22287818a966b03d5a74917fa5ec28e8128413baacbd9775b9aa44b321fab0245de14b088940423ff1cf65dde19a699f1b089bf4f4de9bf12714a43444cd0a67051d0090a199a12
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_loading:
                startActivity(new Intent(MainActivity.this,LoadingActivity.class));
                break;
            case R.id.button_audio:
                startActivity(new Intent(MainActivity.this, AudioActivity.class));
                break;
            case R.id.button_handle:
                startActivity(new Intent(MainActivity.this, HandleActivity.class));
                break;
            case R.id.button_coordinator:
                startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
                break;
            case R.id.button_viewpager:
                startActivity(new Intent(MainActivity.this, ViewpagerActivity.class));
                break;
            case R.id.button_recycle:
                startActivity(new Intent(MainActivity.this, RecycleViewActivity.class));
                break;
            case R.id.button_fragment:
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));
                break;
            case R.id.button_front_service:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                break;
            case R.id.button_float_menu:
                startActivity(new Intent(MainActivity.this, FloatMenuActivity.class));
                break;
            case R.id.button_widget:
                startActivity(new Intent(MainActivity.this, WidgetActivity.class));
                break;
            case R.id.button_download:
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                break;
            case R.id.button_black_and_write:
                startActivity(new Intent(MainActivity.this, BAndWActivity.class));
                break;
            case R.id.button_tab:
                startActivity(new Intent(MainActivity.this, TabHostActivity.class));
                break;
            case R.id.button_network:
                startActivity(new Intent(MainActivity.this, NetworkActivity.class));
                break;
        }
    }
}
