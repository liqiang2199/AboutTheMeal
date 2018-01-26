package com.atmeal.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/16.
 */

public class NearListAdapter extends RecyclerView.Adapter<NearListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ShopListBeen> shopListBeens;
    public NearListAdapter(Context context,ArrayList<ShopListBeen> shopListBeens){
        this.context = context;
        this.shopListBeens = shopListBeens;
    }

    @Override
    public NearListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_nearpage_adapter,parent,false);
        NearListAdapter.ViewHolder holder = new NearListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NearListAdapter.ViewHolder holder, int position) {
        ShopListBeen shopListBeen = shopListBeens.get(position);

        Picasso.with(context).load(shopListBeen.getShopUrl()).into(holder.shop_url);
        holder.shop_name.setText(shopListBeen.getShopName());
        holder.shop_Address.setText(shopListBeen.getShop_Address());
        holder.fight_alone_explain.setText(shopListBeen.getShop_Fightalone_Explain());
        holder.discount_explain.setText(shopListBeen.getShop_Distance_Explain());
        if (shopListBeen.getShop_isFightAlone().equals("1")){
            holder.is_fight_alone.setText("支持拼单");
        }

        holder.address_shopType.setText(shopListBeen.getShop_TypeExplain()+"|"+shopListBeen.getShop_Address());

    }

    @Override
    public int getItemCount() {
        return shopListBeens.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shop_url;
        TextView shop_name;
        TextView address_shopType;
        TextView shop_Address;
        TextView is_fight_alone;
        TextView text_go_fight;//去拼单
        TextView fight_alone_explain;//t拼单说明
        TextView discount_explain;//优惠说明
        public ViewHolder(View itemView) {
            super(itemView);
            shop_url = (ImageView)itemView.findViewById(R.id.shop_url);
            shop_name = (TextView)itemView.findViewById(R.id.shop_name);
            address_shopType = (TextView)itemView.findViewById(R.id.address_shopType);
            shop_Address = (TextView)itemView.findViewById(R.id.shop_Address);
            is_fight_alone = (TextView)itemView.findViewById(R.id.is_fight_alone);
            text_go_fight = (TextView)itemView.findViewById(R.id.text_go_fight);
            fight_alone_explain = (TextView)itemView.findViewById(R.id.fight_alone_explain);
            discount_explain = (TextView)itemView.findViewById(R.id.discount_explain);
        }
    }
}
