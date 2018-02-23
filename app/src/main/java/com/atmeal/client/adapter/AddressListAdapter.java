package com.atmeal.client.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.been.jsonbeen.AddressListBeen;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/22.
 * 地址列表
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder>{

    private ArrayList<AddressListBeen> addressListBeens;
    public AddressListAdapter(ArrayList<AddressListBeen> addressListBeens){
        this.addressListBeens = addressListBeens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AddressListBeen addressListBeen = addressListBeens.get(position);
        holder.address_detail.setText(addressListBeen.getAddressDetail()+addressListBeen.getAddressDoorNum());
        holder.address_phone.setText(addressListBeen.getAddressPhone());
        holder.address_lj.setText(addressListBeen.getAddressSex());
        holder.address_use_name.setText(addressListBeen.getAddressName());
    }

    @Override
    public int getItemCount() {
        return addressListBeens.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView address_detail;
        private TextView address_use_name;
        private TextView address_lj;
        private TextView address_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            address_detail = itemView.findViewById(R.id.address_detail);
            address_use_name = itemView.findViewById(R.id.address_use_name);
            address_lj = itemView.findViewById(R.id.address_lj);
            address_phone = itemView.findViewById(R.id.address_phone);
        }
    }
}
