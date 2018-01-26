package com.atmeal.client.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.ihandler.IHandlerSMS;
import com.atmeal.client.ihandler.IHandlerSMSAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2018/1/14.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    public abstract void InitView();

    public TextView titleback;
    public TextView title;
    public TextView edit_title;
    public Context context;

    @Subscribe
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        IntentCommon.getIstance().IntentNULL();
    }

    @Subscribe
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 注册bus
        EventBus.getDefault().register(this);
    }

    @Subscribe
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        EventBus.getDefault().register(this);
    }

    //对公用标题 控件 初始化
    public void Init_TitleView(){
        titleback = (TextView) findViewById(R.id.titleback);
        title = (TextView) findViewById(R.id.title);
        edit_title = (TextView) findViewById(R.id.edit_title);
        titleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Span_Text(TextView textView ,String content,int start,int end){
        //改变字体颜色
        //先构造SpannableString
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给EditText显示出来
        textView.setText(spanString);
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone, final IHandlerSMS iHandlerSMS) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    iHandlerSMS.onSMSSuccess(event, result, data);
                } else{
                    // TODO 处理错误的结果
                    iHandlerSMS.onSMSFaile(event, result, data);
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }
    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(final String country, final String phone, final String code,
                           final IHandlerSMSAuth iHandlerSMSAuth) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        // 触发操作
        SMSSDK.registerEventHandler(new EventHandler() {
              public void afterEvent(int event, int result, Object data) {
                   if (result == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证成功的结果
                       iHandlerSMSAuth.onAuthSMSSuccess(event, result, data);
                    } else{
                         // TODO 处理错误的结果
                       iHandlerSMSAuth.onAuthSMSFaile(event, result, data);
                    }

              }
        }) ;
     SMSSDK.submitVerificationCode(country, phone, code);
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    };

}
