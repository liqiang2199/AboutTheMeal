package com.atmeal.client.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by admin on 2016/11/7.
 * 界面跳转管理
 */
public class IntentCommon {

    public  Intent intent;
    private static IntentCommon intentmange;

//IntentMange管理
    public IntentCommon(){

        intentmange = this;
    }

    public static IntentCommon getApplication(){
        return intentmange;
    }

    public static IntentCommon getIstance(){
        if(intentmange==null){
            synchronized (IntentCommon.class){
                if (intentmange == null){
                    intentmange = new IntentCommon();
                }
            }
        }
        return intentmange;
    }


    public  void IntentNULL(){
        if(intent==null){
            intent = new Intent();
        }
    }

    /**
     *
     * @param context
     * @param activityClass
     */
    public void StartIntent(Context context,Class<?> activityClass){

        intent.setClass(context,activityClass);
        intent.putExtra("pay","pay");//银行卡专用
        context.startActivity(intent);


    }
    /**
     *
     * @param context
     * @param activityClass
     */
    public void StartIntent(Context context,Class<?> activityClass,String key){

        intent.setClass(context,activityClass);
        intent.putExtra("redbag",key);
        context.startActivity(intent);


    }

    /**
     * 评估界面
     * @param context
     * @param activityClass
     * @param key
     * @param time
     * @param money
     */
    public void StartIntent(Context context,Class<?> activityClass,String key,String time,String money){

        intent.setClass(context,activityClass);
        intent.putExtra("redbag",key);
        intent.putExtra("time",time);
        intent.putExtra("money",money);
        context.startActivity(intent);


    }
    /**
     * 还款计划    还款记录
     * @param context
     * @param activityClass
     * @param key
     * @param index
     */
    public void StartIntent(Context context,Class<?> activityClass,String key,int index){

        intent.setClass(context,activityClass);
        intent.putExtra("rapaycordID",key);
        intent.putExtra("indexActivity",index);
        context.startActivity(intent);


    }


    /**
     * 预约分期
     * @param context
     * @param activityClass
     * @param key
     */
    public void StartIntentHomeStage(Context context,Class<?> activityClass,String key,String keyID){
        intent.setClass(context,activityClass);
        intent.putExtra("homestage",key);
        intent.putExtra("stageKeyID",keyID);
        context.startActivity(intent);
    }

    /**
     * 预约分期列表
     * @param context
     * @param activityClass
     * @param keyID
     */
    public void StartIntentStageList(Context context,Class<?> activityClass,String keyID){
        intent.setClass(context,activityClass);
        intent.putExtra("stagepage",keyID);
        context.startActivity(intent);
    }



//    /**
//     *有参数返回
//     * @param context
//     * @param activityClass
//     */
//    public void StartIntentResult(Context context,Class<?> activityClass){
//
//        intent.setClass(context,activityClass);
//        context.startActivity(intent);
//
//
//    }


    /**
     * 说明类 带参数explaintag
     * @param context
     * @param activityClass
     * @param key
     */
    public void StartExplainIntent(Context context,Class<?> activityClass,String key){
        intent.setClass(context,activityClass);
        intent.putExtra("explaintag",key);
        context.startActivity(intent);
    }

    /**
     *密码管理 safemodify
     * @param context
     * @param activityClass
     * @param key
     */
    public void StartModifyIntent(Context context,Class<?> activityClass,String key){
        intent.setClass(context,activityClass);
        intent.putExtra("safemodify",key);
        context.startActivity(intent);
    }


    /**
     * 账单界面  allbillkey跳转KEY值
     * @param context
     * @param activity
     * @param key
     */
    public void StartIntentBillAll(Context context,Class<?> activity,String key){
        intent.setClass(context,activity);
        intent.putExtra("allbillkey",key);
        context.startActivity(intent);
    }


    /**
     * 首页广告位点击进入详情
     * @param context
     * @param activity
     * @param key
     * @param title
     */
    public void StartIntentHomeWeb(Context context,Class<?> activity,String key,String title){
        intent.setClass(context, activity);
        intent.putExtra("ContentUrl",key);
        intent.putExtra("Title",title);
        context.startActivity(intent);
    }

    /***
     * 账单 延期 和立即还款说明
     *
     * @param activityClass
     */
    public void StartIntentParamsHash(HashMap<String, String> params,Context context, Class<?> activityClass) {
        intent.setClass(context, activityClass);
        Iterator iter = params.entrySet().iterator();
        Bundle b = new Bundle();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            b.putString(String.valueOf(entry.getKey()),
                    String.valueOf(entry.getValue()));
        }
        intent.putExtras(b);
        context.startActivity(intent);
    }
    /***
     * 打开新线程窗体,同时清除以前的task
     *
     */
//    public void NewTaskIntent(Context context,Class<?> activityClass) {
//        intent.setClass(context, activityClass)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }




}
