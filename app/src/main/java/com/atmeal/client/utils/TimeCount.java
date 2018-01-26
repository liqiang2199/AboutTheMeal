package com.atmeal.client.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by admin on 2016/11/11.
 */
public class TimeCount extends CountDownTimer {

    private Button btn;
    private Activity context;//
    private int indexac;//界面显示
    private TextView textView;//更新的进度

    /**
     * 前两个时间millisInFuture/countDownInterval
     * @param millisInFuture
     * @param countDownInterval
     * @param bton
     */
    public TimeCount(long millisInFuture, long countDownInterval, Button bton) {
        super(millisInFuture, countDownInterval);
        btn = bton;
        indexac = 0;
    }
    public TimeCount(long millisInFuture, long countDownInterval, Activity context, int index, TextView textindex){
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.indexac = index;
        this.textView = textindex;
    }

    @Override
    public void onFinish() {
        if (indexac == 0){
            btn.setText("重新验证");
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn.setClickable(true);
//            SMSMessgeCode.getIstance().SmsCodeNo="";
        }else{
            //第一次安装 也要显示 停留页
//            if (SPUtilsCommon.get(context,"pagestart","").toString().equals("")){
//                IntentCommon.getIstance().StartIntent(context,StartPageActivity.class);
//            }
//            else{
//                IntentCommon.getIstance().StartIntent(context,KeXinMain_Activity.class);
//            }
            context.finish();
        }


    }

    public void restart() {
        this.cancel();
        if (indexac == 0){
            btn.setText("获取验证码");
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn.setClickable(true);
        }


    }

    @Override
    public void onTick(long millisUntilFinished) {
        String millis = "倒计" + millisUntilFinished / 1000 + "秒";
        if (indexac == 0){
            btn.setClickable(false);
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn.setText(millis);
        }else{
            textView.setText(millis);
        }

    }
}
