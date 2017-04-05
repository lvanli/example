package com.practise.lizhiguang.componentlibrary.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lizhiguang on 2016/12/10.
 */

public class AnimView extends View {
    public AnimView(Context context) {
        this(context,null);
    }

    public AnimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

}
