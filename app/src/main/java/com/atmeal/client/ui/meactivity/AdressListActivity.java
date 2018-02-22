package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.atmeal.client.R;
import com.atmeal.client.adapter.AddressListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.IntentCommon;

/**
 * Created by Administrator on 2018/2/22.
 * 地址列表
 */

@SuppressLint("Registered")
public class AdressListActivity extends BaseFragmentActivity {

    private RecyclerView recycler_view;
    private Button submit_but;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_recyle);
        InitView();
    }

    @Override
    public void InitView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        submit_but = findViewById(R.id.submit_but);
        submit_but.setText("+添加地址");

        AddressListAdapter addressListAdapter = new AddressListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(addressListAdapter);

        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加 地址
                IntentCommon.getIstance().StartIntent(context,AddAdressActivity.class);
            }
        });
    }

}
