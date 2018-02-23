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
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.jsonbeen.AddressListBeen;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.utils.UtilTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    private ArrayList<AddressListBeen> addressListBeens = new ArrayList<>();

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



        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加 地址
                IntentCommon.getIstance().StartIntent(context,AddAdressActivity.class);
            }
        });

    }

    private void http_AddressList(){
        String addressUrl = UrlCommon.getAddressList+"?usertoken="+ SPUtilsCommon.get(context,"userToken","").toString();
        OkHttpMannager.getInstance().Post_Data(addressUrl,context,
                this,true,"AddressList");
    }

    @Override
    protected void onResume() {
        super.onResume();
        http_AddressList();
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {

        if (tag.equals("AddressList")){

            addressListBeens.clear();

            try {
                String data = jsonObject.getString("data");
                JSONArray jsonArray = new JSONArray(data);
                int length = jsonArray.length();

                for (int i=0;i<length;i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    AddressListBeen addressListBeen = new AddressListBeen();
                    addressListBeen.setAddressName(UtilTools.json_GetKeyReturnValue(json,"addressName"));
                    addressListBeen.setAddressPhone(UtilTools.json_GetKeyReturnValue(json,"addressPhone"));
                    addressListBeen.setAddressDetail(UtilTools.json_GetKeyReturnValue(json,"addressDetail"));
                    addressListBeen.setAddressSex(UtilTools.json_GetKeyReturnValue(json,"addressSex"));
                    addressListBeen.setAddressDoorNum(UtilTools.json_GetKeyReturnValue(json,"addressDoorNum"));
                    addressListBeens.add(addressListBeen);
                }
                AddressListAdapter addressListAdapter = new AddressListAdapter(addressListBeens);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(linearLayoutManager);
                recycler_view.setAdapter(addressListAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        StringCommon.String_Toast(context,msg);
    }
}
