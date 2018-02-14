package com.atmeal.client.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.atmeal.client.R;
import com.atmeal.client.adapter.NearListAdapter;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.been.AdvBeen;
import com.atmeal.client.been.httpbeen.RecommendHttpBeen;
import com.atmeal.client.been.jsonbeen.ShopListBeen;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.GlideImageLoader;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.ihandler.IHandlerAMapLocation;
import com.atmeal.client.ui.mainactivity.JZCActivity;
import com.atmeal.client.ui.mainactivity.VouchersActivity;
import com.atmeal.client.utils.AMapLocationUtils;
import com.atmeal.client.utils.UtilTools;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/14.
 */

public class MainPageFragment extends BaseMealFragment implements OkHttp_CallResponse,IHandlerAMapLocation {
    private View mainView;
    private Banner banner;
    private ImageView image_1,image_2,image_3;
    private RecyclerView recycle_shop;

    private TextView page_vouchers,home_jzc,home_c_xck;
    private TextView home_address;
    private AMapLocationUtils aMapLocationUtils;//定位

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<RecommendHttpBeen> recommendHttpBeens = new ArrayList<>();
    private int shop_type = 1;
    private int pageIndex = 1;
    private ArrayList<ShopListBeen> shopListBeens = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (mainView == null){
           mainView = inflater.inflate(R.layout.fragment_page_main,null);
       }
       InitView();
        return mainView;
    }

    @Override
    public void InitView() {
        banner = (Banner) mainView.findViewById(R.id.banner);
        image_1 = (ImageView)mainView.findViewById(R.id.image_1);
        image_2 = (ImageView)mainView.findViewById(R.id.image_2);
        image_3 = (ImageView)mainView.findViewById(R.id.image_3);

        page_vouchers = (TextView) mainView.findViewById(R.id.page_vouchers);//代金券
        home_jzc = (TextView) mainView.findViewById(R.id.home_jzc);//江浙菜
        home_c_xck = (TextView) mainView.findViewById(R.id.home_c_xck);//小吃快餐

        home_address = (TextView) mainView.findViewById(R.id.home_address);//显示地址

        recycle_shop = mainView.findViewById(R.id.recycle_shop);//首页显示的商品列表
        aMapLocationUtils = new AMapLocationUtils(getContext(),this);


        OkHttpMannager.getInstance().Post_Data(UrlCommon.getadv,getContext(),this,true,"adv");
        OkHttpMannager.getInstance().Post_Data(UrlCommon.Recommend,getContext(),this,false,"Recommend");
        Http_GetShopList();

        OnClickView(page_vouchers);
        OnClickView(home_jzc);
        OnClickView(home_c_xck);
    }

    private void OnClickView(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.page_vouchers:
                        //代金券
                        IntentCommon.getIstance().StartIntent(getContext(),VouchersActivity.class);
                        break;
                    case R.id.home_jzc:
                        //江浙菜
                        IntentCommon.getIstance().StartIntent(getContext(),JZCActivity.class,"CaiType","5");
                        break;
                    case R.id.home_c_xck:
                        //小吃快餐
                        IntentCommon.getIstance().StartIntent(getContext(),JZCActivity.class,"CaiType","4");
                        break;
                }
            }
        });
    }

    private void Http_GetShopList(){
        String shopUrl = UrlCommon.GetShopList+"?pageIndex="+pageIndex+"&pageSize="+10+"&shop_type="+shop_type;
        OkHttpMannager.getInstance().Post_Data(shopUrl,getContext(),
                this,false,"ShopList");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        if (tag.equals("adv")){
            //获取 返回数据
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int jsarrayLength = jsonArray.length();
                for (int i =0;i<jsarrayLength;i++){
                    AdvBeen advBeen = new AdvBeen();
                    JSONObject json = jsonArray.getJSONObject(i);
                    advBeen.setAdvdetails(json.getString("advdetails"));
                    images.add(json.getString("advdetails"));
                }
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (tag.equals("Recommend")){
            try {
                String dataArray = jsonObject.getString("data");
                recommendHttpBeens = (ArrayList<RecommendHttpBeen>) JSON.parseArray(dataArray,RecommendHttpBeen.class);
                for (RecommendHttpBeen recommendHttpBeen:recommendHttpBeens){
                    if (recommendHttpBeen.getRecommend_index().equals("1")){
                        Picasso.with(getContext()).load(recommendHttpBeen.getRecommend_Url()).into(image_1);
                    }
                    if (recommendHttpBeen.getRecommend_index().equals("2")){
                        Picasso.with(getContext()).load(recommendHttpBeen.getRecommend_Url()).into(image_2);
                    }
                    if (recommendHttpBeen.getRecommend_index().equals("3")){
                        Picasso.with(getContext()).load(recommendHttpBeen.getRecommend_Url()).into(image_3);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

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
                recycle_shop.setLayoutManager(linearLayoutManager);
                recycle_shop.setAdapter(nearListAdapter);
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
    //定位
    @Override
    public void aMapLocationSure(AMapLocation amapLocation) {
        home_address.setText(amapLocation.getAddress());
    }

    @Override
    public void aMapLocationError(int errorCode, String errorInfo) {
        home_address.setText("定位失败");
    }
}
