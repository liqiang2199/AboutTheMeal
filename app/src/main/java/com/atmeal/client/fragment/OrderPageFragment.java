package com.atmeal.client.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atmeal.client.R;
import com.atmeal.client.adapter.OrderListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/14.
 * 订单
 */

public class OrderPageFragment extends BaseMealFragment implements OkHttp_CallResponse{
    private View orderView;
//    private RecyclerView recycler_view;
//    private LinearLayout liner_find;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (orderView == null){
            orderView = inflater.inflate(R.layout.fragment_order_page,null);
        }
        InitView();
        return orderView;
    }

    @Override
    public void InitView() {
//        liner_find = (LinearLayout)orderView.findViewById(R.id.liner_find);
//        liner_find.setVisibility(View.GONE);
//
//        recycler_view = (RecyclerView)orderView.findViewById(R.id.recycler_view);
//        OrderListAdapter orderListAdapter = new OrderListAdapter();
//        GridLayoutManager layoutmanager = new GridLayoutManager(getActivity(),2);
//        //设置RecyclerView 布局
//        recycler_view.setLayoutManager(layoutmanager);
//        recycler_view.setAdapter(orderListAdapter);
        getOrderList();
    }

    private void getOrderList(){
        String purchase = UrlCommon.getOrderList+"?userToken="
                + SPUtilsCommon.get(getContext(),"userToken","").toString()
                ;
        OkHttpMannager.getInstance().Post_Data(purchase,getContext(),
                this,true,"Purchase");
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
