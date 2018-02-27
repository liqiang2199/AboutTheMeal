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
import com.atmeal.client.common.StringCommon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/14.
 * 订单列表的 界面数据
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private ArrayList<ShopListBeen> shopListBeens;
    private Context context;
    public OrderListAdapter(Context context,ArrayList<ShopListBeen> shopListBeens){
        this.shopListBeens = shopListBeens;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_order_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * 1 待支付
     *  2 支付成功
     *  -1 取消订单
     *  -10 删除订单
     *  -100 退款
     *  10 待完成
     *  20 完成
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShopListBeen shopListBeen = shopListBeens.get(position);
        Picasso.with(context).load(shopListBeen.getShopUrl()).into(holder.image_shop);
        holder.shop_name.setText(shopListBeen.getShopName());
        holder.shop_price.setText(shopListBeen.getShopPrice()+"元");
        String typeOrder = shopListBeen.getOrderType();
        if (typeOrder.equals("-100")||typeOrder.equals("-10")){
            holder.textView.setVisibility(View.GONE);
        }else{
            holder.textView.setVisibility(View.VISIBLE);
            if (typeOrder.equals("1")){
                holder.textView.setText("取消订单");
            }else{
                holder.textView.setText("删除订单");
            }
        }
        holder.textView2.setText(shopListBeen.getOrderTypeExplain());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringCommon.String_Toast(context,"没有找到对应的接口");
            }
        });
        holder.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringCommon.String_Toast(context,"没有找到对应的接口");
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopListBeens.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image_shop;
        private TextView shop_name;
        private TextView shop_price;
        private TextView textView2;
        private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                image_shop = itemView.findViewById(R.id.image_shop);
                shop_name = itemView.findViewById(R.id.shop_name);
                shop_price = itemView.findViewById(R.id.shop_price);
                textView = itemView.findViewById(R.id.textView);
                textView2 = itemView.findViewById(R.id.textView2);
            }
        }
}
