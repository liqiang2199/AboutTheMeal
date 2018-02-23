package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.utils.UtilTools;

/**
 * Created by empty cup on 2018/2/23.
 * 我的钱包
 */

@SuppressLint("Registered")
public class WalletActivity extends BaseFragmentActivity {

    private LinearLayout waller_set_pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UtilTools.isStringNull(UtilTools.getSputils(context,"userToken"))){
            finish();
            return;
        }
        setContentView(R.layout.activity_wallet);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("我的钱包");

        waller_set_pay = findViewById(R.id.waller_set_pay);
        waller_set_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //支付设置
                IntentCommon.getIstance().StartIntent(context,WallerPaySetActivity.class);
            }
        });
    }
}
