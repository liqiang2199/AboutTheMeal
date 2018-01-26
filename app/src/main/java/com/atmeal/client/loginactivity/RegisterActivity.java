package com.atmeal.client.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/19.
 * 注册
 */

public class RegisterActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private EditText register_name;
    private EditText register_password;
    private EditText register_password_re,register_passwordyan;
    private Button submit_but;
    private TextView login_tip,yanzheng,queren;
    private CountDownTimer timer;
    EventHandler eventHandler;
    int T=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitView();
        yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86",register_name.getText().toString().trim());
            }
        });
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                yanzheng.setEnabled(false);
                yanzheng.setText((millisUntilFinished / 1000) + "秒后可重发");
            }

            @Override
            public void onFinish() {
                yanzheng.setEnabled(true);
                yanzheng.setText("验证");
            }
        };

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //{"status":603,"detail":"请填写正确的手机号码"}
                            if (!TextUtils.isEmpty(msg)){
                                try {
                                    JSONObject json = new JSONObject(msg);
                                    if (json.has("detail")){
                                        String msgtip = json.getString("detail");
                                        Toast.makeText(RegisterActivity.this, msgtip, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.v("yan","发送成功");
                        timer.start();
                    }else if (event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        Log.v("yan","验证成功");
                        Message msg=new Message();
                        chandle.sendMessage(msg);
                        T=200;
                    }
                }

            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }
    Handler chandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(RegisterActivity.this,"验证通过",Toast.LENGTH_LONG).show();
            queren.setEnabled(false);
        }
    };
    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("注册");
        yanzheng=findViewById(R.id.yanzheng);
        register_name = (EditText) findViewById(R.id.register_name);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_re = (EditText) findViewById(R.id.register_password_re);
        register_passwordyan=findViewById(R.id.register_password_yan);
        queren=findViewById(R.id.queren);
        submit_but = (Button) findViewById(R.id.submit_but);
        login_tip = (TextView) findViewById(R.id.login_tip);
        Span_Text(login_tip,"已有账号点此登录",4,8);
        submit_but.setText("注册");
        submit_but.setEnabled(false);
        InitOnClick(submit_but);
        InitOnClick(login_tip);
        InitOnClick(queren);
    }
    private void InitOnClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.submit_but:
                        //注册
                        String name = register_name.getText().toString();
                        String pass = register_password.getText().toString();
                        String passre = register_password_re.getText().toString();
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
                        if (!passre.equals(pass)){
                            StringCommon.String_Toast(context,"两次登录密码不一致");
                            return;
                        }
                        if (!StringCommon.isMobileNO(name)){
                            StringCommon.String_Toast(context,"输入手机号有误");
                            return;
                        }
                        if (T==0){
                            StringCommon.String_Toast(context,"验证失败！");
                            return;
                        }
                        String loginurl = UrlCommon.Regist+"?phone="+name
                                +"&password="+pass+"&registerType=1";

                        OkHttpMannager.getInstance().Post_Data(loginurl,context,
                                RegisterActivity.this,true,"register");
                        break;
                    case R.id.login_tip:
                        finish();
                        break;
                    case R.id.queren:
                        SMSSDK.submitVerificationCode("86",register_name.getText().toString().trim(),register_passwordyan.getText().toString().trim());
                        break;
                }
            }
        });
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag .equals("register")){
            String tipmsg = null;
            try {
                tipmsg = jsonObject.getString("tipMessge");
//                StringCommon.String_Toast(context,tipmsg);
                IntentCommon.getIstance().StartIntent(context,RegisterTipSuccessActivity.class);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }


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
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
