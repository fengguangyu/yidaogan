package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 光 on 2017/11/13.
 */

public class WhereActivity extends BaseActivity{
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
//初始化位置用于多次定位,否则只能定位一次
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
//    销毁

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
    }
//onResume()是onPause()（通常是当前的acitivty被暂停了，比如被另一个透明或者Dialog样式的Activity覆盖了），之后dialog取消，activity回到可交互状态，调用onResume()。

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    //请求许可
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"请同意0",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this,"gun",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void setActivityTitle() {

    }

    @Override
    protected void getLayoutToView() {
        setContentView(R.layout.activity_where);
    }

    @Override
    protected void initValues() {


    }

    @Override
    protected void initViews() {
        positionText = (TextView) findViewById(R.id.position_text_view);
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
    }

    @Override
    protected void setViewsListener() {

    }

    @Override
    protected void setViewsFunction() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new myLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(WhereActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(WhereActivity.this, android.Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(WhereActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WhereActivity.this,permissions,1);
        } else {
            requestLocation();
        }
    }
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            Toast.makeText(this, "nav to " + location.getAddrStr(), Toast.LENGTH_SHORT).show();
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
    }

    public class myLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    StringBuilder currentPosition = new StringBuilder();
//                    currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
//                    currentPosition.append("经线：").append(location.getLongitude()).append("\n");
//                    currentPosition.append("国家: ").append(location.getCountry()).append("\n");
//                    currentPosition.append("省: ").append(location.getProvince()).append("\n");
//                    currentPosition.append("市: ").append(location.getCity()).append("\n");
//                    currentPosition.append("街道: ").append(location.getStreet()).append("\n");
//                    currentPosition.append("定位方式");
//                    if (location.getLocType()==BDLocation.TypeGpsLocation){
//                        currentPosition.append("GPS");
//                    } else if (location.getLocType()==BDLocation.TypeNetWorkLocation){
//                        currentPosition.append("网络");
//                    }
//                    positionText.setText(currentPosition);
//
//                }
//            });
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }
    }
}
