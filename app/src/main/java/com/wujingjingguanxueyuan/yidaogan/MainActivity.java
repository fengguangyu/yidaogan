package com.wujingjingguanxueyuan.yidaogan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wujingjingguanxueyuan.yidaogan.activity.BaseActivity;
import com.wujingjingguanxueyuan.yidaogan.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Map;

import mrkj.library.wheelview.pickerView.PickerView;
import mrkj.library.wheelview.scalerulerview.ScaleRulerView;
import mrkj.library.wheelview.utils.DateViewHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener,PickerView.onSelectListener,RadioGroup.OnCheckedChangeListener,DateViewHelper.OnResultMessageListener,View.OnFocusChangeListener{
//声明各方面的对象与变量
//    TAG
    private static final String TAG = MainActivity.class.getSimpleName();
//    功能
    private DateViewHelper dateViewHelper;//日历操作
    private LayoutInflater inflater;//布局填充器
    private boolean closeDataPicker;//判断显示或隐藏日历
    private List<String> height_list;//身高的集合
    private boolean closeHeightPicker;//判断显示或隐藏数字选择器
    private boolean nextShow;//判断按钮是否显示
//     控件
    private LinearLayout personal_information_page_one;//完善资料1/2布局
    private RadioGroup group;//性别选择
    private EditText input_nick;//属性昵称
    private TextView input_birthday,input_height;//生日、身高
    private Button next_action;//下一步
    private LinearLayout choose_date;//日期选择
    private LinearLayout choose_height;//选择身高
    private PickerView height_picker;//横下滑动选择身高
    private ImageView back;//返回上一步
    private LinearLayout personal_information_page_two;//完善资料2/2布局
    private ScaleRulerView input_weight;//选择体重
    private TextView show_weight;//显示选择的体重
    private ScaleRulerView input_length;//选择体重
    private TextView show_length;//显示选择的体重
    private Button go_walk;//先去逛逛
    private Button go_make;//制定计划
//  信息
    private String gender_str;//性别
    private String nick_str;//昵称
    private String birthday_str;//生日
    private String height_str;//身高
    private int custom_age;//年龄
    private String weight_str;//体重文字
    private int weight;//体重数值
    private String length_str;//步长文字
    private int length;//步长数值

    private int year;
    private int month;
    private  int day;
    private ActivityMainBinding binding;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

//主界面的选择逻辑

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.input_birthday:
               // openPickerOrClose();
        }
    }



    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

    }

    @Override
    public void onSelect(String s) {

    }

    @Override
    public void getMessage(Map<String, Object> map) {

    }

//    设置日期选择器
    private void openPickerOrClose(boolean flag) {
        if (flag){
            Log.e(TAG, "用户输入生日" );
            hideKeyBoard();
            choose_date.setVisibility(View.VISIBLE);
            hideNextBtn();
            showAnimation(choose_date,R.anim.push_up_in);
            closeDataPicker = true;

        }
    }

//    设置身高选择器
private void openHeightPickerOrClose(boolean flag){
    if (flag){
        Log.e(TAG, "获取身高-->获取焦点了");
        hideKeyBoard();
        choose_height.setVisibility(View.VISIBLE);
        hideNextBtn();
        showAnimation(choose_height, R.anim.push_left_in);
        closeHeightPicker = true;
    }else {
        if (closeHeightPicker == true){
            showAnimation(choose_height,R.anim.push_left_out);
            choose_height.setVisibility(View.GONE);
            showNextBtn();
            closeHeightPicker = false;
        }
    }
}

    private void showNextBtn() {
        if (closeDataPicker == false && closeHeightPicker ==false){
            next_action.setVisibility(View.VISIBLE);
            showAnimation(next_action,R.anim.fade_in);
            nextShow = true;
      }
    }

    private Animation showAnimation(View view,int animID) {
        Animation animation = AnimationUtils.loadAnimation(this,animID);
        view.setAnimation(animation);
        animation.start();
        return animation;
    }
    private void hideNextBtn() {
    }

    private void hideKeyBoard() {
    }

}
