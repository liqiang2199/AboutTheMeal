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
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/22.
 * 地址列表
 */

@SuppressLint("Registered")
public class AdressListActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

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

        Init_TitleView();
        titleback.setText("地址列表");

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
        http_AddressList();
    }

    private void http_AddressList(){
        String addressUrl = UrlCommon.getAddressList+"?usertoken="+ SPUtilsCommon.get(context,"userToken","").toString();
        OkHttpMannager.getInstance().Post_Data(addressUrl,context,
                this,true,"AddAddress");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {

    }

    @Override
    public void OkHttp_CallonFailure(Call call) {

    }

    @Override
    public void OkHttp_CallNoData(String tag) {

    }

    @Override
    public void OkHttp_CallError(String tag) {

    }

    @Override
    public void OkHttp_CallToastShow(String msg, String tag) {

    }
}
