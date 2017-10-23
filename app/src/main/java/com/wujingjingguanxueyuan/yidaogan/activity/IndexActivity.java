package com.wujingjingguanxueyuan.yidaogan.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.databinding.ActivityIndexBinding;

/**
 * Created by 光 on 2017/10/23.
 * 启动界面
 */

public class IndexActivity extends BaseActivity{

    private boolean animationComplete;
    private boolean loadTabComplete;
    private boolean loadContentComplete;

    private ActivityIndexBinding binding;
//启动activity_layout布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index);
        showAnimation();
    }
//启动显示动画
    private void showAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_yidaogan);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }
          // 动画演示完后500毫秒，执行goMainPage方法
            //sleep方法是在返回之前等待给定的毫秒数
            @Override
            public void onAnimationEnd(Animation animation) {
                SystemClock.sleep(500);
                animationComplete = true;
                goMainPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        SystemClock.sleep(200);
        binding.tvIndex.startAnimation(animation);
        loadTabData();
        loadContentData();
    }

    private void loadTabData() {
        // Call<List<Tab>>
    }

    private void loadContentData() {
    }



    private void goMainPage() {
       // if (animationComplete &&)
    }
}
