package com.atmeal.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atmeal.client.R;

/**
 * Created by Administrator on 2018/1/24.
 */

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.ViewVouchersHolder> {

    @Override
    public ViewVouchersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_vouchers_adapter,parent,false);
        VouchersAdapter.ViewVouchersHolder holder = new VouchersAdapter.ViewVouchersHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewVouchersHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewVouchersHolder extends RecyclerView.ViewHolder{
        public ViewVouchersHolder(View itemView) {
            super(itemView);
        }
    }
}
