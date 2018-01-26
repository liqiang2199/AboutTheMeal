package com.atmeal.client.loginactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.busbeen.LoginBusBeen;
import com.atmeal.client.been.jsonbeen.LoginBeen;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 * 登录界面
 */

public class LoginActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private Button submit_but;
    private EditText login_name;
    private EditText login_password;
    private TextView forget_password;
    private TextView text_span;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("登录");


        submit_but = (Button) findViewById(R.id.submit_but);
        login_name = (EditText) findViewById(R.id.login_name);
        login_password = (EditText) findViewById(R.id.login_password);
        forget_password = (TextView) findViewById(R.id.forget_password);
        text_span = (TextView) findViewById(R.id.text_span);
        Span_Text(text_span,"还没有账号？现在注册",6,10);
        submit_but.setText("登录");

        OnClick(submit_but);
        OnClick(text_span);
        OnClick(forget_password);
    }
    private void OnClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.submit_but:
                        //登录
                        String name = login_name.getText().toString();
                        String pass = login_password.getText().toString();
                        if (!StringCommon.StringNull(name)){
                            StringCommon.String_Toast(context,"请输入手机号");
                            return;
                        }
                        if (!StringCommon.StringNull(pass)){
                            StringCommon.String_Toast(context,"请输入登录密码");
                            return;
                        }
                        if (!StringCommon.StringLongSIX(pass)){
                            StringCommon.String_Toast(context,"请输入6至20位登录密码");
                            return;
                        }
                        if (!StringCommon.isMobileNO(name)){
                            StringCommon.String_Toast(context,"输入手机号有误");
                            return;
                        }
                        String loginurl =UrlCommon.Login+"?userphone="+name
                                +"&userpassword="+pass;

                        OkHttpMannager.getInstance().Post_Data(loginurl,LoginActivity.this,
                                LoginActivity.this,true,"login");
                        break;
                    case R.id.text_span:
                        IntentCommon.getIstance().StartIntent(context,RegisterActivity.class);
                        break;
                    case R.id.forget_password:
                        IntentCommon.getIstance().StartIntent(context,ForgetPassWordActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        try {
            String data = jsonObject.getString("data");
            LoginBeen loginBeen = JSON.parseObject(data,LoginBeen.class);
            SPUtilsCommon.put(context,"userToken",loginBeen.getUserToken());
            SPUtilsCommon.put(context,"userName",loginBeen.getUserName());
            SPUtilsCommon.put(context,"userImage",loginBeen.getUserHeader());
            SPUtilsCommon.put(context,"userPhone",loginBeen.getPhone());
            EventBus.getDefault().post(new LoginBusBeen());
            finish();
        }catch (Exception e){

        }
    }

    @Override
    public void OkHttp_CallonFailure(Call call) {

    }

    @Override
    public void OkHttp_CallNoData(String tag) {

    }

    @Override
    public void OkHttp_CallError(String tag) {

    }

    @Override
    public void OkHttp_CallToastShow(String msg, String tag) {
        StringCommon.String_Toast(context,msg);
    }
}
