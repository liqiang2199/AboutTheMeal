package com.atmeal.client.meactivity.setactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.atmeal.client.loginactivity.LoginActivity;
import com.atmeal.client.utils.TimeCount;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/20.
 *
 */

public class ChangeBindPhoneActivity extends BaseFragmentActivity implements IHandlerSMS,IHandlerSMSAuth
,OkHttp_CallResponse{

    private Button btn_get_yzm;//发送验证码
    private Button submit_but;
    private EditText edt_yzm;
    private EditText edt_mobile_new;
    private EditText edt_mobile_old;

    private TimeCount timeCount;
    private String phone;
    private String oldPhone;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_bind_mobile_new);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("设置");

        btn_get_yzm = (Button)findViewById(R.id.btn_get_yzm);
        submit_but = (Button)findViewById(R.id.submit_but);
        edt_yzm = (EditText)findViewById(R.id.edt_yzm);
        edt_mobile_new = (EditText)findViewById(R.id.edt_mobile_new);
        edt_mobile_old = (EditText)findViewById(R.id.edt_mobile_old);

        submit_but.setText("确定");

        OnClickView(btn_get_yzm);
        OnClickView(submit_but);
    }
    private void OnClickView(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 phone = edt_mobile_new.getText().toString();
                 oldPhone = edt_mobile_old.getText().toString();
                 code = edt_yzm.getText().toString();
                switch (view.getId()){
                    case R.id.btn_get_yzm:
                        if (IsPhoneOrIsPhoneNull(phone,oldPhone)){
                            return;
                        }
                        DialogCommon.getIstance().CreateMyDialog(context);
                        sendCode("86",edt_mobile_new.getText().toString(),ChangeBindPhoneActivity.this);
                        break;
                    case R.id.submit_but:
                        if (IsPhoneOrIsPhoneNull(phone,oldPhone)){
                            return;
                        }
                        //先验证 短信验证码是否正确
                        if (!StringCommon.StringNull(code)){
                            StringCommon.String_Toast(context,"请输入验证码");
                            return;
                        }
                        DialogCommon.getIstance().CreateMyDialog(context);
                        submitCode("86",edt_mobile_new.getText().toString(),code,ChangeBindPhoneActivity.this);
                        break;
                }
            }
        });
    }

    private boolean IsPhoneOrIsPhoneNull(String phone,String oldPhone){
        if (!StringCommon.StringNull(oldPhone)){
            StringCommon.String_Toast(context,"请输入旧手机号");
            return true;
        }
        if (!StringCommon.StringNull(phone)){
            StringCommon.String_Toast(context,"请输入新手机号");
            return true;
        }
        if (!StringCommon.isMobileNO(oldPhone)){
            StringCommon.String_Toast(context,"旧手机号错误，请重新输入");
            return true;
        }
        if (!StringCommon.isMobileNO(phone)){
            StringCommon.String_Toast(context,"新手机号错误，请重新输入");
            return true;
        }
        if (phone.equals(oldPhone)){
            StringCommon.String_Toast(context,"新旧手机号不能一样");
            return true;
        }
        return false;
    }

    //发送短信验证码
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

    //认证
    @Override
    public void onAuthSMSSuccess(int event, int result, Object data) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                // 需要在主线程的操作。
                String loginurl = UrlCommon.changephone+"?usertoken="+
                        StringCommon.Read_SpUtil(context,"userToken")
                        +"&useOldPhone="+oldPhone+"&usePhone="+phone;

                OkHttpMannager.getInstance().Post_Data(loginurl,ChangeBindPhoneActivity.this,
                        ChangeBindPhoneActivity.this,false,"changePhone");

            }
        });
    }

    @Override
    public void onAuthSMSFaile(int event, int result, Object data) {

    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        StringCommon.String_Toast(context,"手机号更换成功");
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
