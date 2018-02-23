package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.utils.UtilTools;

/**
 * Created by empty cup on 2018/2/23.
 * 支付设置
 */

@SuppressLint("Registered")
public class WallerPaySetActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UtilTools.isStringNull(UtilTools.getSputils(context,"userToken"))){
            finish();
            return;
        }
        setContentView(R.layout.activity_wallet_pay_set);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("支付设置");
    }
}
