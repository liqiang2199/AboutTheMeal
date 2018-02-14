package com.atmeal.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/24.
 */

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.ViewVouchersHolder> {

    private ArrayList<ShopListBeen> shopListBeens;
    private Context context;

    public VouchersAdapter(ArrayList<ShopListBeen> shopListBeens,Context context ){
        this.shopListBeens = shopListBeens;
        this.context = context;
    }

    @Override
    public ViewVouchersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_vouchers_adapter,parent,false);
        VouchersAdapter.ViewVouchersHolder holder = new VouchersAdapter.ViewVouchersHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewVouchersHolder holder, int position) {
        ShopListBeen shopListBeen = shopListBeens.get(position);
        holder.shop_name.setText(shopListBeen.getShopName());
        holder.voucher_address.setText(shopListBeen.getShop_Address());
        holder.voucher_explain.setText(shopListBeen.getShop_Distance_Explain());
        holder.voucher_price.setText("￥"+shopListBeen.getShopPrice());

        Picasso.with(context).load(shopListBeen.getShopUrl()).into(holder.shop_url);
    }

    @Override
    public int getItemCount() {
        return shopListBeens.size();
    }

    class ViewVouchersHolder extends RecyclerView.ViewHolder{
        private TextView shop_name;
        private TextView voucher_address;
        private TextView voucher_robbed;
        private TextView voucher_explain;
        private TextView voucher_use_time;
        private TextView voucher_price;
        private TextView voucher_robbed_btn;//点击抢劵

        private ImageView shop_url;

        public ViewVouchersHolder(View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            voucher_address = itemView.findViewById(R.id.voucher_address);
            voucher_robbed = itemView.findViewById(R.id.voucher_robbed);
            voucher_explain = itemView.findViewById(R.id.voucher_explain);
            voucher_use_time = itemView.findViewById(R.id.voucher_use_time);
            voucher_price = itemView.findViewById(R.id.voucher_price);
            voucher_robbed_btn = itemView.findViewById(R.id.voucher_robbed_btn);

            shop_url = itemView.findViewById(R.id.shop_url);

        }
    }
}
