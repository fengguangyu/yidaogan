package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.BaseActivity;
import com.wujingjingguanxueyuan.yidaogan.fragment.DiscoverFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.MineFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.SportFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.TrainingFragment;
import com.wujingjingguanxueyuan.yidaogan.utils.Constant;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValues;


/**
 * 功能界面
 */
public class FunctionActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    //变量
    private long exitTime;//第一次单机退出键的时间
    private int load_values;//判断加载fragment的变量
    //控件
    private RadioGroup radioGroup;//切换按钮的容器
    private RadioButton sport_btn,training_btn,weather_btn,mine_btn;//切换按钮
    //碎片
    private SportFragment sportFragment;//运动
    private TrainingFragment trainingFragment;//发现
    private DiscoverFragment discoverFragment;//心率
    private MineFragment mineFragment;//我的

    private DrawerLayout mDrawerLayout;
    /**
     * 设置标题
     */
    @Override
    protected void setActivityTitle() {

    }

    /**
     * 初始化界面
     */
    @Override
    protected void getLayoutToView() {
        setContentView(R.layout.activity_function);
    }

    /**
     * 初始化相关变量
     */
    @Override
    protected void initValues() {

        //如果这个值等于1就加载运动界面，等于2就加载发现界面
        load_values = SaveKeyValues.getIntValues("launch_which_fragment",0);
        Log.e("加载判断值", load_values + "");
        //实例化相关碎片
        sportFragment = new SportFragment();
        trainingFragment = new TrainingFragment();
        discoverFragment = new DiscoverFragment();
        mineFragment = new MineFragment();
        //初始化界面
        if (load_values == Constant.TURN_MAIN){
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_launch",true);
            sportFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.frag_home,sportFragment,Constant.SPORT_TAG).commit();
        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.frag_home,trainingFragment,Constant.FIND_TAG).commit();
        }
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        radioGroup = (RadioGroup) findViewById(R.id.ui_btn_group);
        sport_btn = (RadioButton) findViewById(R.id.sport_btn);
        training_btn = (RadioButton) findViewById(R.id.training_btn);
        weather_btn = (RadioButton) findViewById(R.id.weather_btn);
        mine_btn = (RadioButton) findViewById(R.id.mine_btn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_location:
                        startActivity(new Intent(FunctionActivity.this,WhereActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置监听
     */
    @Override
    protected void setViewsListener() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 设置功能
     */
    @Override
    protected void setViewsFunction() {
        if (load_values == Constant.TURN_MAIN){
            sport_btn.setChecked(true);
        }else {
            training_btn.setChecked(true);
        }
    }
    private Fragment mCurrentFragment = null;
    /**
     * 切换界面
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (checkedId){
            case R.id.sport_btn://运动

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("is_launch", false);
                    sportFragment.setArguments(bundle);
                    switchFragment(mCurrentFragment, sportFragment);

                break;
            case R.id.training_btn://发现

                    switchFragment(mCurrentFragment, trainingFragment);

                break;
            case R.id.weather_btn://心率

                    switchFragment(mCurrentFragment, discoverFragment);

                break;
            case R.id.mine_btn://我的

                switchFragment(mCurrentFragment, mineFragment);

                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 按两次退出按钮退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // System.currentTimeMillis()无论何时调用，肯定大于2000
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    public void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment == null) {
            transaction.add(R.id.frag_home, to).commit();
            mCurrentFragment = to;
            return;
        }
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            if (!to.isAdded()) {
                if (from.isAdded()) {
                    transaction.hide(from).add(R.id.frag_home, to).commit();
                } else {
                    transaction.add(R.id.frag_home, to).commit();
                }
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }
}
