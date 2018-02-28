package com.atmeal.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atmeal.client.R;

/**
 * Created by empty cup on 2018/2/28.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View discountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount_adapter,null);
        ViewHolder viewHolder = new ViewHolder(discountView);
        return viewHolder;
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
