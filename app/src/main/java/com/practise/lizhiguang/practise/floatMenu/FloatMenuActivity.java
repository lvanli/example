package com.practise.lizhiguang.practise.floatMenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.practise.lizhiguang.practise.R;

public class FloatMenuActivity extends AppCompatActivity {
    boolean isMenuOut = false;
    ImageView views[] = new ImageView[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu);
        bindWidget();
        init();
    }
    void bindWidget() {
        views[7] = (ImageView) findViewById(R.id.float_menu_contract);
        views[1] = (ImageView) findViewById(R.id.float_menu_house);
        views[2] = (ImageView) findViewById(R.id.float_menu_keys);
        views[3] = (ImageView) findViewById(R.id.float_menu_lands);
        views[4] = (ImageView) findViewById(R.id.float_menu_map);
        views[5] = (ImageView) findViewById(R.id.float_menu_search);
        views[6] = (ImageView) findViewById(R.id.float_menu_swimming);
        views[0] = (ImageView) findViewById(R.id.float_menu_red);
    }
    void init() {

    }
    public void doClick (View view){
        switch (view.getId()) {
            case R.id.float_menu_red:
                startAnimator();
                isMenuOut = !isMenuOut;
                break;
            default:
                break;
        }
    }
    public void startAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        for (int i=1;i<views.length;i++) {
            ObjectAnimator animator = new ObjectAnimator();
            if (isMenuOut)
                animator.setFloatValues(-i*200,0);
            else
                animator.setFloatValues(0,-i*200);
            animator.setDuration(1000);
            animator.setStartDelay(i*200);
            animator.setInterpolator(new BounceInterpolator());
            animator.setPropertyName("translationY");
            animator.setTarget(views[i]);
            animatorSet.playTogether(animator);
        }
        animatorSet.start();
    }
}
