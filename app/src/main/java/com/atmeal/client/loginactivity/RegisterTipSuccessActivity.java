package com.atmeal.client.loginactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2018/1/20.
 *
 */

public class RegisterTipSuccessActivity extends BaseFragmentActivity {

    private TextView login_tip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tip);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("注册");
        login_tip = (TextView) findViewById(R.id.login_tip);
        login_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
