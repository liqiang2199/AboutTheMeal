package com.atmeal.client.ui.mainactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.VouchersAdapter;
import com.atmeal.client.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2018/1/24.
 * 代金劵
 */

public class VouchersActivity extends BaseFragmentActivity{

    private RecyclerView recycler_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);
        InitView();
    }

    @Override
    public void InitView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        VouchersAdapter vouchersAdapter = new VouchersAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);

        recycler_view.setAdapter(vouchersAdapter);

    }
}
