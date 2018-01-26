package com.atmeal.client.meactivity.setactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/20.
 */

public class ChangePasswordLoginActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private Button submit_but;
    private EditText edt_password_old;
    private EditText edt_password_new;
    private EditText edt_password_new_re;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_loginpassword);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("设置");

        submit_but = (Button)findViewById(R.id.submit_but);
        edt_password_old = (EditText)findViewById(R.id.edt_password_old);
        edt_password_new = (EditText)findViewById(R.id.edt_password_new);
        edt_password_new_re = (EditText)findViewById(R.id.edt_password_new_re);
        //确定 修改密码
        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = edt_password_old.getText().toString();
                String newPass = edt_password_new.getText().toString();
                String newPassre = edt_password_new_re.getText().toString();
                if (IsPhoneOrIsPhoneNull(newPass,oldPass,newPassre)){
                    return;
                }
                String loginurl = UrlCommon.changepassword+"?usertoken="+
                        StringCommon.Read_SpUtil(context,"userToken")
                        +"&oldpassword="+oldPass+"&newpassword="+newPass;

                OkHttpMannager.getInstance().Post_Data(loginurl,ChangePasswordLoginActivity.this,
                        ChangePasswordLoginActivity.this,true,"changePAssWord");
            }
        });
    }

    private boolean IsPhoneOrIsPhoneNull(String phone,String oldPhone,String passre){
        if (!StringCommon.StringNull(oldPhone)){
            StringCommon.String_Toast(context,"请输入旧密码");
            return true;
        }
        if (!StringCommon.StringNull(phone)){
            StringCommon.String_Toast(context,"请输入新密码");
            return true;
        }
        if (!StringCommon.StringLongSIX(phone)){
            StringCommon.String_Toast(context,"请输入6至20位新密码");
            return true;
        }
        if (phone.equals(oldPhone)){
            StringCommon.String_Toast(context,"新旧密码不能一样");
            return true;
        }
        if (!phone.equals(passre)){
            StringCommon.String_Toast(context,"两次新密码不一样");
            return true;
        }
        return false;
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        StringCommon.String_Toast(context,"更换登录密码成功");
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
        StringCommon.String_Toast(context,msg);
    }
}
