package com.atmeal.client.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.atmeal.client.ui.mainactivity.ReservationActivity;
import com.atmeal.client.ui.mainactivity.TakeOutActivity;
import com.atmeal.client.ui.mainactivity.VouchersActivity;
import com.atmeal.client.utils.AMapLocationUtils;
import com.atmeal.client.utils.UtilTools;
import com.atmeal.client.weigth.ObservableScrollView;
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

public class MainPageFragment extends BaseMealFragment implements OkHttp_CallResponse,IHandlerAMapLocation
,ObservableScrollView.OnObservableScrollViewListener {
    private View mainView;
    private Banner banner;
    private ImageView image_1,image_2,image_3;
    private RecyclerView recycle_shop;

    private ObservableScrollView mObservableScrollView;
    private int mHeight;
    private LinearLayout mHeaderContent;

    private TextView page_vouchers,home_jzc,home_c_xck;
    private TextView home_address,main_take_out,main_reservation;
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
        main_take_out = (TextView) mainView.findViewById(R.id.main_take_out);//外卖
        main_reservation = (TextView) mainView.findViewById(R.id.main_reservation);//订座

        home_address = (TextView) mainView.findViewById(R.id.home_address);//显示地址

        recycle_shop = mainView.findViewById(R.id.recycle_shop);//首页显示的商品列表
        aMapLocationUtils = new AMapLocationUtils(getContext(),this);


        //初始化控件
        mObservableScrollView = (ObservableScrollView) mainView.findViewById(R.id.sv_main_content);
        mHeaderContent = mainView.findViewById(R.id.mHeaderContent);


        OkHttpMannager.getInstance().Post_Data(UrlCommon.getadv,getContext(),this,true,"adv");
        OkHttpMannager.getInstance().Post_Data(UrlCommon.Recommend,getContext(),this,false,"Recommend");
        Http_GetShopList();

        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = banner.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = banner.getHeight() - mHeaderContent.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                mObservableScrollView.setOnObservableScrollViewListener(MainPageFragment.this);
            }
        });


        OnClickView(page_vouchers);
        OnClickView(home_jzc);
        OnClickView(home_c_xck);
        OnClickView(main_take_out);
        OnClickView(main_reservation);
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
                    case R.id.main_take_out:
                        //外卖
                        IntentCommon.getIstance().StartIntent(getContext(),TakeOutActivity.class);
                        break;
                    case R.id.main_reservation:
                        //订座
                        IntentCommon.getIstance().StartIntent(getContext(),ReservationActivity.class);
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
                    shopListBeen.setShopID(UtilTools.json_GetKeyReturnValue(
                            json,"shopID"
                    ));
                    shopListBeen.setShopPrice(UtilTools.json_GetKeyReturnValue(json,"shopPrice"));
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

    //标题的颜色变化
    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            mHeaderContent.setBackgroundColor(Color.argb(0, 0, 185, 198));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            mHeaderContent.setBackgroundColor(Color.argb((int) alpha, 0, 185, 198));
        } else {
            //过顶部图区域，标题栏定色
            mHeaderContent.setBackgroundColor(Color.argb(255, 0, 185, 198));
        }

    }
}
