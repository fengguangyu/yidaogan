package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.romainpiel.shimmer.Shimmer;
import com.wujingjingguanxueyuan.yidaogan.MainActivity;
import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.databinding.ActivityIndexBinding;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValue;

/**
 * Created by 光 on 2017/10/23.
 * 启动界面
 */

public class IndexActivity extends BaseActivity{
    private ActivityIndexBinding binding;
    private boolean isFirst;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1){
                if (isFirst){
                    startActivity(new Intent(IndexActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(IndexActivity.this,MainActivity.class));
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
        Shimmer shimmer = new Shimmer();
        shimmer.start(binding.tvIndex);
    }
//启动显示动画
    private void showAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_yidaogan);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                goMainPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.tvIndex.startAnimation(animation);

    }

    private void goMainPage() {
        int  count = SaveKeyValue.getIntValues("count",0);
        isFirst = (count == 0)? true:false;
        handler.sendEmptyMessageDelayed(1,3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return false;
    }
}
