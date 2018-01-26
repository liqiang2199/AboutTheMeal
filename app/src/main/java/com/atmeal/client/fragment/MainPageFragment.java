package com.atmeal.client.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atmeal.client.R;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.been.AdvBeen;
import com.atmeal.client.been.httpbeen.RecommendHttpBeen;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.GlideImageLoader;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.ui.mainactivity.VouchersActivity;
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

public class MainPageFragment extends BaseMealFragment implements OkHttp_CallResponse{
    private View mainView;
    private Banner banner;
    private ImageView image_1,image_2,image_3;

    private TextView page_vouchers;

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<RecommendHttpBeen> recommendHttpBeens = new ArrayList<>();

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

        OkHttpMannager.getInstance().Post_Data(UrlCommon.getadv,getContext(),this,true,"adv");
        OkHttpMannager.getInstance().Post_Data(UrlCommon.Recommend,getContext(),this,false,"Recommend");

        OnClickView(page_vouchers);
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
                }
            }
        });
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
