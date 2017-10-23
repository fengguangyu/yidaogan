package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 光 on 2017/10/23.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}
