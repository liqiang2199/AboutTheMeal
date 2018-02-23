package com.atmeal.client.ui.mainactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.TakeOutAdapter;
import com.atmeal.client.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2018/2/21.
 * 外卖
 */

@SuppressLint("Registered")
public class TakeOutActivity  extends BaseFragmentActivity{
    private RecyclerView recycler_view;
    private TextView text_shop_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recyle);
        InitView();
    }


    @Override
    public void InitView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        text_shop_type = findViewById(R.id.text_shop_type);
        text_shop_type.setText("外卖");
        Init_TitleView();
        title.setText("外卖");

        TakeOutAdapter takeOutAdapter = new TakeOutAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(takeOutAdapter);
    }
}
