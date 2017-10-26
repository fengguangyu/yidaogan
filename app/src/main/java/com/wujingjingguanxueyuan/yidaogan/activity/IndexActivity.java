package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wujingjingguanxueyuan.yidaogan.MainActivity;
import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.databinding.ActivityIndexBinding;

/**
 * Created by 光 on 2017/10/23.
 * 启动界面
 */

public class IndexActivity extends BaseActivity{
    private boolean animationComplete;
    private ActivityIndexBinding binding;
    private boolean isFirst;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1){
                if (isFirst){
                    startActivity(new Intent(IndexActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(IndexActivity.this,FunctionActivity.class));
                }
                finish();
            }
            return false;
        }
    });

//启动activity_layout布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    }

    private void goMainPage() {

    }
}
