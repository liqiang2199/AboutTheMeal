package com.atmeal.client.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mob.MobSDK;

/**
 * Created by Administrator on 2018/1/17.
 */

public class MealApplication extends Application {
    private static MealApplication istance;
    public boolean logflag = true;
    //Application 的 静态 调用
    public MealApplication(){
        istance=this;
    }


    public static MealApplication getIstance(){
        if(istance==null){
            synchronized (MealApplication.class){
                if (istance == null){
                    istance = new MealApplication();
                }
            }
        }
        return istance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(getApplicationContext(), "23cd244ed3350", "c28eac6a20df877c5045a9868d714553");
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] ni = cm.getAllNetworkInfo();
        if(ni!=null){
            for(int i=0;i<ni.length;i++){
                if(ni[i].getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }

        // 获取当前的网络连接是否可用
        return false;
    }
}
