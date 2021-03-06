package com.atmeal.client.ui.mainactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.adapter.VouchersAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.LogCommon;
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
 * Created by Administrator on 2018/1/24.
 * 代金劵
 */

public class VouchersActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private RecyclerView recycler_view;
    private int pageIndex = 1;
    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();

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
        title.setText("代金劵");

        http_VouchersList();

    }

    private void http_VouchersList(){
        String shopUrl = UrlCommon.VouchersList+"?pageIndex="+pageIndex+"&pageSize="+10;
        OkHttpMannager.getInstance().Post_Data(shopUrl,context,
                this,true,"VoucherList");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag.equals("VoucherList")){
            if (pageIndex == 1){
                shopListBeens.clear();
            }
            try {
                String data = jsonObject.getString("data");
                JSONArray jsonArray = new JSONArray(data);
                int length = jsonArray.length();

                for (int i=0;i<length;i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    ShopListBeen shopListBeen = new ShopListBeen();
                    shopListBeen.setShopName(UtilTools.json_GetKeyReturnValue(json,"shopName"));
                    shopListBeen.setShop_Address(UtilTools.json_GetKeyReturnValue(json,"shop_Address"));
                    shopListBeen.setShop_TypeExplain(UtilTools.json_GetKeyReturnValue(json,"shop_TypeExplain"));
                    shopListBeen.setShopUrl(UtilTools.json_GetKeyReturnValue(json,"shopUrl"));
                    shopListBeen.setShop_Distance_Explain(UtilTools.json_GetKeyReturnValue(json,"shop_Distance_Explain"));
                    shopListBeen.setShopPrice(UtilTools.json_GetKeyReturnValue(json,"shopPrice"));

                    shopListBeens.add(shopListBeen);
                }

                VouchersAdapter vouchersAdapter = new VouchersAdapter(shopListBeens,this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recycler_view.setLayoutManager(linearLayoutManager);

                recycler_view.setAdapter(vouchersAdapter);

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

    }
}
