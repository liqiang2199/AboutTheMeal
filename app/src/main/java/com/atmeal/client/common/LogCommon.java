package com.atmeal.client.common;

import com.atmeal.client.application.MealApplication;

/**
 * Created by admin on 2016/11/7.
 */
public class LogCommon {

//    private static String log = "this";
    public static void LogShowPrint(String thelog){
        if(MealApplication.getIstance().logflag){

            android.util.Log.v("this",thelog);
        }


    }
}
