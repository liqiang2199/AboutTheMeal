package com.atmeal.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atmeal.client.R;

/**
 * Created by Administrator on 2018/1/14.
 * 订单列表的 界面数据
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_order,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
}
