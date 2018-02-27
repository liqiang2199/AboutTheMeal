package com.atmeal.client.loginactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/19.
 * 注册
 */

public class RegisterActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private EditText register_name;
    private EditText register_password;
    private EditText register_password_re;
    private Button submit_but;
    private TextView login_tip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("注册");

        register_name = (EditText) findViewById(R.id.register_name);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_re = (EditText) findViewById(R.id.register_password_re);
        submit_but = (Button) findViewById(R.id.submit_but);
        login_tip = (TextView) findViewById(R.id.login_tip);
        Span_Text(login_tip,"已有账号点此登录",4,8);
        submit_but.setText("注册");

        InitOnClick(submit_but);
        InitOnClick(login_tip);
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
                        String loginurl = UrlCommon.Regist+"?phone="+name
                                +"&password="+pass+"&registerType=1";

                        OkHttpMannager.getInstance().Post_Data(loginurl,context,
                                RegisterActivity.this,true,"register");
                        break;
                    case R.id.login_tip:
                        finish();
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
}
