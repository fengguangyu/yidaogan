package com.wujingjingguanxueyuan.yidaogan.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wujingjingguanxueyuan.yidaogan.R;

/**
 * Created by 光 on 2017/10/26.
 */

public class FunctionActivity extends BaseActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

    }
    private Animation showAnimation(View view,int animID) {
        Animation animation = AnimationUtils.loadAnimation(this,animID);
        view.setAnimation(animation);
        animation.start();
        return animation;
    }
    }

