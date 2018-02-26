package com.atmeal.client.ui.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.adapter.OrderListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
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
 * Created by Administrator on 2018/2/26.
 * 订单分类
 */

@SuppressLint("Registered")
public class OrderClassifyActivity  extends BaseFragmentActivity implements OkHttp_CallResponse{

    private TextView text_host,text_bread,text_northeastern,text_snack;
    private ArrayList<Integer> textid = new ArrayList<>();
    private int shop_type = 1;

    private RecyclerView recycler_view;
    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_classify);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("订单");

        textid.add(R.id.text_host);
        textid.add(R.id.text_bread);
        textid.add(R.id.text_northeastern);
        textid.add(R.id.text_snack);

        text_host = (TextView)findViewById(R.id.text_host);
        text_bread = (TextView)findViewById(R.id.text_bread);
        text_northeastern = (TextView)findViewById(R.id.text_northeastern);
        text_snack = (TextView)findViewById(R.id.text_snack);

        recycler_view = findViewById(R.id.recycler_view);

        OnClickView(text_host);
        OnClickView(text_bread);
        OnClickView(text_northeastern);
        OnClickView(text_snack);
        getOrderList();
    }

    private void OnClickView(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.text_host:
                        shop_type = 1;
                        OnClick_Backround(R.id.text_host);
                        break;
                    case R.id.text_bread:
                        shop_type = 2;
                        OnClick_Backround(R.id.text_bread);
                        break;
                    case R.id.text_northeastern:
                        shop_type = 3;
                        OnClick_Backround(R.id.text_northeastern);
                        break;
                    case R.id.text_snack:
                        shop_type = 4;
                        OnClick_Backround(R.id.text_snack);
                        break;
                    case R.id.text_zjc:
                        shop_type = 5;
                        OnClick_Backround(R.id.text_zjc);
                        break;
                    case R.id.text_scc:
                        shop_type = 6;
                        OnClick_Backround(R.id.text_scc);
                        break;
                }
            }
        });

    }

    /**
     * 背景颜色的改变
     * @param textIdChecked
     */
    private void OnClick_Backround(int textIdChecked){

        for (int textID : textid){
            findViewById(textID).setBackgroundResource(R.drawable.home_time);
        }
        findViewById(textIdChecked).setBackgroundResource(R.drawable.border_red);
    }

    private void getOrderList(){
        String purchase = UrlCommon.getOrderList+"?userToken="
                + SPUtilsCommon.get(context,"userToken","").toString()
                ;
        OkHttpMannager.getInstance().Post_Data(purchase,context,
                this,true,"OrderList");
    }
    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag.equals("OrderList")){
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
                    shopListBeen.setOrderType(UtilTools.json_GetKeyReturnValue(json,"orderType"));
                    shopListBeen.setOrderTypeExplain(UtilTools.json_GetKeyReturnValue(json,"orderTypeExplain"));
                    shopListBeens.add(shopListBeen);
                    OrderListAdapter nearListAdapter = new OrderListAdapter(context,shopListBeens);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    recycler_view.setAdapter(nearListAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
