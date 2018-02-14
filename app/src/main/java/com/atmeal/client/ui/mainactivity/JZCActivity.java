package com.atmeal.client.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.utils.UtilTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by empty cup on 2018/2/14.
 *
 * 小吃快餐
 * 江浙菜
 *
 */

public class JZCActivity extends BaseFragmentActivity implements OkHttp_CallResponse{
    private RecyclerView recycler_view;
    private TextView text_shop_type;
    private int shop_type = 5;
    private int pageIndex = 1;
    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();

    private String  caiType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recyle);
        InitView();
    }

    @Override
    public void InitView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        text_shop_type = findViewById(R.id.text_shop_type);

        Intent intent = getIntent();
        if (intent != null){
            caiType = intent.getStringExtra("CaiType");
            shop_type = Integer.valueOf(caiType);

            if (shop_type == 4){
                text_shop_type.setText("小吃快餐");
            }else{
                text_shop_type.setText("江浙菜");
            }
        }



        Init_TitleView();
        title.setText("江浙菜");
        http_JZC();
    }

    private void http_JZC(){
        String shopUrl = UrlCommon.GetShopList+"?pageIndex="+pageIndex+"&pageSize="+10+"&shop_type="+shop_type;
        OkHttpMannager.getInstance().Post_Data(shopUrl,context,
                this,true,"ShopList");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag.equals("ShopList")){
            if (pageIndex == 1){
                shopListBeens.clear();
            }
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
                    shopListBeens.add(shopListBeen);
                }
                NearListAdapter nearListAdapter = new NearListAdapter(context,shopListBeens);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(linearLayoutManager);
                recycler_view.setAdapter(nearListAdapter);
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
