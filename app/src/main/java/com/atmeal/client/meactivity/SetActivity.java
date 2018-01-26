package com.atmeal.client.meactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.meactivity.setactivity.ChangeBindPhoneActivity;
import com.atmeal.client.meactivity.setactivity.ChangePasswordLoginActivity;

/**
 * Created by Administrator on 2018/1/16.
 * 设置界面
 */

public class SetActivity extends BaseFragmentActivity {

    private LinearLayout change_bind_phone;
    private TextView text_changePassWord;

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

        OnClickView(change_bind_phone);
        OnClickView(text_changePassWord);
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
                }
            }
        });
    }

}
