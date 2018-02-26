package com.atmeal.client.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/25.
 *
 * 订单支付
 */

@SuppressLint("Registered")
public class OrderPayActivity extends BaseFragmentActivity implements OkHttp_CallResponse{

    private ImageView image_shop;
    private TextView shop_name;
    private TextView shop_price;
    private Button submit;

    private ShopListBeen shopListBeen;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        InitView();
    }

    @Override
    public void InitView() {
        image_shop = findViewById(R.id.image_shop);
        shop_name = findViewById(R.id.shop_name);
        shop_price = findViewById(R.id.shop_price);
        submit = findViewById(R.id.submit_but);
        submit.setText("确认支付");
        Init_TitleView();
        titleback.setText("订单支付");

        Intent intent = getIntent();
        if (intent != null){
            shopListBeen = (ShopListBeen) intent.getSerializableExtra("ShopPay");
            orderId = (String) intent.getStringExtra("orderId");
        }

        Picasso.with(context).load(shopListBeen.getShopUrl()).into(image_shop);
        shop_name.setText(shopListBeen.getShopName());
        shop_price.setText(shopListBeen.getShopPrice()+"元");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_OrderPay();
            }
        });
    }

    private void post_OrderPay(){
        String pay = UrlCommon.payOrder+"?shopId="+shopListBeen.getShopID()+
                "&userToken="+ SPUtilsCommon.get(context,"userToken","").toString()+
                "&orderId="+orderId+
                "&isFightAlone="+"true"+
                "&ShopPrice="+shopListBeen.getShopPrice();
        OkHttpMannager.getInstance().Post_Data(pay,context,
                this,true,"PayOrder");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        try {
            if (tag.equals("PayOrder")){
                StringCommon.String_Toast(context,"付款成功，请前往订单中心查看");
                finish();
            }
        }catch (Exception e){

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
