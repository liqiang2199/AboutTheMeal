package com.atmeal.client.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.loginactivity.LoginActivity;
import com.atmeal.client.utils.UtilTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/16.
 * 附近
 */

public class NearPageFragment extends BaseMealFragment implements OkHttp_CallResponse{

    private View nearView;
    private RecyclerView recycler_view;

    private TextView text_host,text_bread,text_northeastern,text_snack,text_zjc,text_scc;
    private ArrayList<Integer> textid = new ArrayList<>();
    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();
    private int shop_type = 1;
    private int pageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (nearView == null){
           nearView = inflater.inflate(R.layout.fragment_order,null);
       }
        InitView();
        return nearView;
    }

    @Override
    public void InitView() {
        recycler_view = (RecyclerView)nearView.findViewById(R.id.recycler_view);


        textid.add(R.id.text_host);
        textid.add(R.id.text_bread);
        textid.add(R.id.text_northeastern);
        textid.add(R.id.text_snack);
        textid.add(R.id.text_zjc);
        textid.add(R.id.text_scc);

        text_host = (TextView)nearView.findViewById(R.id.text_host);
        text_bread = (TextView)nearView.findViewById(R.id.text_bread);
        text_northeastern = (TextView)nearView.findViewById(R.id.text_northeastern);
        text_snack = (TextView)nearView.findViewById(R.id.text_snack);
        text_zjc = (TextView)nearView.findViewById(R.id.text_zjc);
        text_scc = (TextView)nearView.findViewById(R.id.text_scc);

        OnClickView(text_host);
        OnClickView(text_bread);
        OnClickView(text_northeastern);
        OnClickView(text_snack);
        OnClickView(text_zjc);
        OnClickView(text_scc);


        OnClick_Backround(R.id.text_host);
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
            nearView.findViewById(textID).setBackgroundResource(R.drawable.home_time);
        }
        nearView.findViewById(textIdChecked).setBackgroundResource(R.drawable.border_red);
        Http_GetShopList();
        pageIndex = 1;
    }

    /**
     * 加载图片
     */
    private void Http_GetShopList(){
        String shopUrl = UrlCommon.GetShopList+"?pageIndex="+pageIndex+"&pageSize="+10+"&shop_type="+shop_type;
        OkHttpMannager.getInstance().Post_Data(shopUrl,getContext(),
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
                NearListAdapter nearListAdapter = new NearListAdapter(getContext(),shopListBeens);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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
