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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.adapter.OrderListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.utils.UtilTools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/14.
 * 订单
 */

public class OrderPageFragment extends BaseMealFragment implements OkHttp_CallResponse{
    private View orderView;

    private LinearLayout liner_order_no;
    private LinearLayout liner_order_have_data;

    private LinearLayout waller_set_pay;
    private LinearLayout waller_set_pay1;

    private ImageView image_shop;
    private TextView shop_name;
    private TextView shop_price;

    private ImageView image_shop1;
    private TextView shop_name1;
    private TextView shop_price1;

    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();

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
        liner_order_no = orderView.findViewById(R.id.liner_order_no);
        liner_order_have_data = orderView.findViewById(R.id.liner_order_have_data);

        waller_set_pay = orderView.findViewById(R.id.waller_set_pay);
        waller_set_pay1 = orderView.findViewById(R.id.waller_set_pay1);

        image_shop = orderView.findViewById(R.id.image_shop);
        shop_name = orderView.findViewById(R.id.shop_name);
        shop_price = orderView.findViewById(R.id.shop_price);

        image_shop1 = orderView.findViewById(R.id.image_shop1);
        shop_name1 = orderView.findViewById(R.id.shop_name1);
        shop_price1 = orderView.findViewById(R.id.shop_price1);

        getOrderList();
    }

    private void getOrderList(){
        String purchase = UrlCommon.getOrderList+"?userToken="
                + SPUtilsCommon.get(getContext(),"userToken","").toString()
                ;
        OkHttpMannager.getInstance().Post_Data(purchase,getContext(),
                this,true,"OrderList");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag.equals("ShopList")){
            shopListBeens.clear();
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
                    shopListBeen.setShop_isFightAlone(UtilTools.json_GetKeyReturnValue(json,"shop_isFightAlone"));
                    shopListBeen.setShop_isTakeout_food(UtilTools.json_GetKeyReturnValue(
                            json,"shop_isTakeout_food"
                    ));
                    shopListBeen.setShopUrl(UtilTools.json_GetKeyReturnValue(json,"shopUrl"));
                    shopListBeen.setShop_Distance_Explain(UtilTools.json_GetKeyReturnValue(
                            json,"shop_Fightalone_Explain"
                    ));
                    shopListBeen.setShop_Fightalone_Explain(UtilTools.json_GetKeyReturnValue(
                            json,"shop_Distance_Explain"
                    ));
                    shopListBeen.setShopID(UtilTools.json_GetKeyReturnValue(
                            json,"shopID"
                    ));
                    shopListBeen.setShopPrice(UtilTools.json_GetKeyReturnValue(json,"shopPrice"));
                    shopListBeens.add(shopListBeen);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int shopSize = shopListBeens.size();
            if (shopSize <2){
                waller_set_pay1.setVisibility(View.GONE);
            }else{
                waller_set_pay1.setVisibility(View.VISIBLE);
            }
            for (int i = 0 ; i<shopSize;i++){
                if (i == 0){
                    Picasso.with(getContext()).load(shopListBeens.get(0).getShopUrl()).into(image_shop);
                    shop_name.setText(shopListBeens.get(0).getShopName());
                    shop_price.setText(shopListBeens.get(0).getShopPrice());
                }
                if (i == 1){
                    Picasso.with(getContext()).load(shopListBeens.get(1).getShopUrl()).into(image_shop1);
                    shop_name1.setText(shopListBeens.get(1).getShopName());
                    shop_price1.setText(shopListBeens.get(1).getShopPrice());
                }
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
