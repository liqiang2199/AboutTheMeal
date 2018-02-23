package com.atmeal.client.meactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.busbeen.LoginBusBeen;
import com.atmeal.client.common.DialogCommon;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.meactivity.setactivity.ChangeBindPhoneActivity;
import com.atmeal.client.meactivity.setactivity.ChangePasswordLoginActivity;
import com.atmeal.client.utils.UtilTools;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/1/16.
 * 设置界面
 */

public class SetActivity extends BaseFragmentActivity {

    private LinearLayout change_bind_phone;
    private TextView text_changePassWord,text_quite_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("我的");

        change_bind_phone = (LinearLayout) findViewById(R.id.change_bind_phone);
        text_changePassWord = (TextView) findViewById(R.id.text_changePassWord);
        text_quite_login = (TextView) findViewById(R.id.text_quite_login);

        OnClickView(change_bind_phone);
        OnClickView(text_changePassWord);
        OnClickView(text_quite_login);

        if (UtilTools.isStringNull(UtilTools.getSputils(context,"userToken"))){
            text_quite_login.setVisibility(View.GONE);
        }else{
            text_quite_login.setVisibility(View.VISIBLE);
        }
    }

    private void OnClickView(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.change_bind_phone:
                        //改变登录的手机号
                        IntentCommon.getIstance().StartIntent(context, ChangeBindPhoneActivity.class);
                        break;
                    case R.id.text_changePassWord:
                        //改变登录的密码
                        IntentCommon.getIstance().StartIntent(context, ChangePasswordLoginActivity.class);
                        break;
                    case R.id.text_quite_login:
                        //退出登录
                        SPUtilsCommon.put(context,"userToken","");
                        SPUtilsCommon.put(context,"userName","");
                        SPUtilsCommon.put(context,"userImage","");
                        SPUtilsCommon.put(context,"userPhone","");
                        EventBus.getDefault().post(new LoginBusBeen());
                        finish();
                        break;
                }
            }
        });
    }

}
