package com.atmeal.client.loginactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.DialogCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.ihandler.IHandlerSMS;
import com.atmeal.client.ihandler.IHandlerSMSAuth;
import com.atmeal.client.meactivity.setactivity.ChangeBindPhoneActivity;
import com.atmeal.client.utils.TimeCount;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/20.
 */

public class ForgetPassWordActivity extends BaseFragmentActivity implements IHandlerSMS,IHandlerSMSAuth
        ,OkHttp_CallResponse {

    private Button btn_get_yzm;//发送验证码
    private Button submit_but;
    private EditText edt_yzm;
    private EditText edt_mobile;
    private EditText edt_forget_pass;
    private EditText edt_forget_pass_re;

    private TimeCount timeCount;
    private String phone;
    private String pass;
    private String passre;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("登录");

        btn_get_yzm = (Button)findViewById(R.id.btn_get_yzm);
        submit_but = (Button)findViewById(R.id.submit_but);

        edt_yzm = (EditText)findViewById(R.id.edt_yzm);
        edt_mobile = (EditText)findViewById(R.id.edt_mobile);
        edt_forget_pass = (EditText)findViewById(R.id.edt_forget_pass);
        edt_forget_pass_re = (EditText)findViewById(R.id.edt_forget_pass_re);

        OnClickView(btn_get_yzm);
        OnClickView(submit_but);
    }

    private void OnClickView(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = edt_mobile.getText().toString();
                pass = edt_forget_pass.getText().toString();
                passre = edt_forget_pass_re.getText().toString();
                code = edt_yzm.getText().toString();
                switch (view.getId()){
                    case R.id.btn_get_yzm:
                        if (IsPhoneOrIsPhoneNull(phone,pass,passre)){
                            return;
                        }
                        DialogCommon.getIstance().CreateMyDialog(context);
                        sendCode("86",edt_mobile.getText().toString(),ForgetPassWordActivity.this);
                        break;
                    case R.id.submit_but:
                        //先验证 短信验证码是否正确
                        if (!StringCommon.StringNull(code)){
                            StringCommon.String_Toast(context,"请输入验证码");
                            return;
                        }
                        if (IsPhoneOrIsPhoneNull(phone,pass,passre)){
                            return;
                        }
                        if (!StringCommon.StringNull(pass)){
                            StringCommon.String_Toast(context,"请输入密码");
                            return ;
                        }

                        if (!pass.equals(passre)){
                            StringCommon.String_Toast(context,"两次密码不一致");
                            return ;
                        }
                        DialogCommon.getIstance().CreateMyDialog(context);
                        submitCode("86",edt_mobile.getText().toString(),code,ForgetPassWordActivity.this);
                        break;
                }
            }
        });
    }
    private boolean IsPhoneOrIsPhoneNull(String phone,String pass,String passre){

        if (!StringCommon.StringNull(phone)){
            StringCommon.String_Toast(context,"请输入手机号");
            return true;
        }
        if (!StringCommon.isMobileNO(phone)){
            StringCommon.String_Toast(context,"手机号错误，请重新输入");
            return true;
        }

        return false;
    }

    @Override
    public void onSMSSuccess(int event, int result, Object data) {
        DialogCommon.getIstance().MyDialogCanle();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                // 需要在主线程的操作。

                timeCount = new TimeCount(60000,1000,btn_get_yzm);
                timeCount.start();


            }
        });
    }

    @Override
    public void onSMSFaile(int event, int result, Object data) {
        DialogCommon.getIstance().MyDialogCanle();
//        StringCommon.String_Toast(context,"发送失败，重新发送");
        LogCommon.LogShowPrint("data 发送失败   "+data);
    }

    @Override
    public void onAuthSMSSuccess(int event, int result, Object data) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                // 需要在主线程的操作。
                String loginurl = UrlCommon.forgetpassword+"?usertoken="+
                        StringCommon.Read_SpUtil(context,"userToken")
                        +"&usepassword="+pass;

                OkHttpMannager.getInstance().Post_Data(loginurl,ForgetPassWordActivity.this,
                        ForgetPassWordActivity.this,false,"forgetPassword");

            }
        });
    }

    @Override
    public void onAuthSMSFaile(int event, int result, Object data) {

    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        StringCommon.String_Toast(context,"密码修改成功");
        finish();
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

    }
}
