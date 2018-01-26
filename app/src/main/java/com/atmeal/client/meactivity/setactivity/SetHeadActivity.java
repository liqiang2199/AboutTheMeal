package com.atmeal.client.meactivity.setactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2018/1/20.
 * 相册选择 上传
 */

public class SetHeadActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_set);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("我的");
    }
}
