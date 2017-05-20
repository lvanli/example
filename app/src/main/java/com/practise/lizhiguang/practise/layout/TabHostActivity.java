package com.practise.lizhiguang.practise.layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.practise.lizhiguang.practise.R;

public class TabHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        TabHost tabHost = (TabHost) findViewById(R.id.tab_tab_host);
        tabHost.setup();
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.tabhost_first_fragment,tabHost.getTabContentView());
        inflater.inflate(R.layout.tabhost_second_fragment,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.tab_first_fragment).setIndicator("第一个"));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.tab_second_fragment).setIndicator("第二个"));
        FragmentManager manager = getFragmentManager();
        MyFragment fragment1 = new MyFragment();
        fragment1.setText("first");
        MyFragment fragment2 = new MyFragment();
        fragment2.setText("second");
        manager.beginTransaction().add(R.id.tab_first_fragment,fragment1)
                .add(R.id.tab_second_fragment,fragment2).commit();
    }
    class MyFragment extends Fragment {
        private String mText;
        public void setText(String text){
            mText = text;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView = new TextView(TabHostActivity.this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mText != null )
                textView.setText(mText);
            else
                textView.setText("unSet");
            return textView;
        }
    }
}
