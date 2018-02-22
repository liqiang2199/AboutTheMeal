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

/**
 * Created by Administrator on 2018/1/14.
 * 订单
 */

public class OrderPageFragment extends BaseMealFragment{
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
    }
}
