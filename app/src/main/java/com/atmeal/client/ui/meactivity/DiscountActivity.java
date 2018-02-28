package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.DiscountAdapter;
import com.atmeal.client.base.BaseFragmentActivity;

/**
 * Created by empty cup on 2018/2/28.
 * 优惠出
 */

@SuppressLint("Registered")
public class DiscountActivity  extends BaseFragmentActivity{
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
        Init_TitleView();
        title.setText("我的");

        DiscountAdapter discountAdapter = new DiscountAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);

        recycler_view.setAdapter(discountAdapter);
    }
}
