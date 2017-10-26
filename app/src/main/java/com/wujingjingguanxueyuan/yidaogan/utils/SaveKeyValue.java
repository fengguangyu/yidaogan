package com.wujingjingguanxueyuan.yidaogan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wujingjingguanxueyuan.yidaogan.activity.BaseActivity;

/**
 * Created by 光 on 2017/10/26.
 */

public class SaveKeyValue extends BaseActivity{

    public static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


//    初始化SharedPreferences
    public static void createSharePreferences(Context context){
        String appName = context.getPackageName();
        Log.e( "储存的文件名",appName );
        sharedPreferences = context.getSharedPreferences(appName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
//    判断SharedPreferences是否被创建
    public static boolean isUnCreate(){
        boolean result = (sharedPreferences == null) ? true : false;
        if (result){
            Log.e( "提醒 ", "SharedPreferences未被创建");
        }
        return result;
    }
}
