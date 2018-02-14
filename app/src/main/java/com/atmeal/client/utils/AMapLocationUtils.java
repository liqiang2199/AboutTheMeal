package com.atmeal.client.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.atmeal.client.ihandler.IHandlerAMapLocation;

/**
 * Created by empty cup on 2018/2/14.
 *  获取定位相关信息
 */

public class AMapLocationUtils {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private IHandlerAMapLocation iHandlerAMapLocation;
    //声明定位回调监听器
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (iHandlerAMapLocation != null){

                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        iHandlerAMapLocation.aMapLocationSure(amapLocation);
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        iHandlerAMapLocation.aMapLocationError(amapLocation.getErrorCode(),amapLocation.getErrorInfo());
                    }

                }

            }
        }
    };
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private Context context;
    //
    public AMapLocationUtils(Context context,IHandlerAMapLocation iHandlerAMapLocation){
        this.context = context;
        this.iHandlerAMapLocation = iHandlerAMapLocation;
        map_Location();
    }

    private void map_Location(){

        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(1000);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);

            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }
}
