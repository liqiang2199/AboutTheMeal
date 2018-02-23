package com.atmeal.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseMealFragment;
import com.atmeal.client.been.busbeen.LoginBusBeen;
import com.atmeal.client.been.busbeen.UpHeaderBus;
import com.atmeal.client.common.IntentCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.loginactivity.HeaderSetActivity;
import com.atmeal.client.loginactivity.LoginActivity;
import com.atmeal.client.meactivity.SetActivity;
import com.atmeal.client.test_webview_demo.BrowserActivity;
import com.atmeal.client.ui.meactivity.AddAdressActivity;
import com.atmeal.client.ui.meactivity.AdressListActivity;
import com.atmeal.client.utils.UtilTools;
import com.atmeal.client.weigth.myImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2018/1/14.
 */

public class MePageFragment extends BaseMealFragment {
    private View meView;

    private ImageView image_set;
    private TextView login_is_no,fragvideo;
    private myImageView iv_head_set;
    private LinearLayout liner_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (meView == null){
            meView = inflater.inflate(R.layout.fragment_me,null);
        }
        InitView();
        return meView;
    }

    @Override
    public void InitView() {
        image_set = (ImageView)meView.findViewById(R.id.image_set);
        login_is_no = (TextView) meView.findViewById(R.id.login_is_no);
        fragvideo=meView.findViewById(R.id.fragvideo);
        iv_head_set = (myImageView)meView.findViewById(R.id.iv_head_set);

        liner_address = meView.findViewById(R.id.liner_address);

        login_TextTip();


        InitOnClick(image_set);
        InitOnClick(login_is_no);
        InitOnClick(iv_head_set);
        InitOnClick(fragvideo);
        InitOnClick(liner_address);
    }

    @Override
    public void onResume() {
        super.onResume();
        login_TextTip();
    }

    private void InitOnClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.image_set:
                        //设置
                        IntentCommon.getIstance().StartIntent(getContext(),SetActivity.class);
                        break;
                    case R.id.login_is_no:
                        //登录
                        if (SPUtilsCommon.contains(getContext(),"userToken")){
                            if (UtilTools.isStringNull(SPUtilsCommon.get(getContext(),"userToken","").toString())){
                                IntentCommon.getIstance().StartIntent(getContext(),LoginActivity.class);
                            }
                        }else{
                            IntentCommon.getIstance().StartIntent(getContext(),LoginActivity.class);
                        }

                        break;
                    case R.id.iv_head_set:
                        //头像设置
                        if (UtilTools.isStringNull(UtilTools.getSputils(getContext(),"userToken"))){
                            StringCommon.String_Toast(getContext(),"请先登录");
                            return;
                        }
                        IntentCommon.getIstance().StartIntent(getContext(),HeaderSetActivity.class);
                        break;
                    case R.id.fragvideo:
                        //视频跳转
                        IntentCommon.getIstance().StartIntent(getContext(),BrowserActivity.class);
                        break;
                    case R.id.liner_address:
                        //收货地址
                        if (UtilTools.isStringNull(UtilTools.getSputils(getContext(),"userToken"))){
                            StringCommon.String_Toast(getContext(),"请先登录");
                            return;
                        }
                        IntentCommon.getIstance().StartIntent(getContext(),AdressListActivity.class);
                        break;
                }
            }
        });
    }
    private void login_TextTip(){
        if (login_is_no != null){
            String token = StringCommon.Read_SpUtil(getContext(),"userToken");
            if (StringCommon.StringNull(token)){
                login_is_no.setText(StringCommon.Read_SpUtil(getContext(),"userName"));
//                login_is_no.setOnClickListener(null);
            }else{
                login_is_no.setText("未登录");
                iv_head_set.setImageResource(R.mipmap.bg_like_users_extend_pressed);
            }
        }
//        Picasso.with(getContext()).load("http://www.cup812.cn/fightalone/uploadheader/1111111.jpg").into(iv_head_set);
        String headImage = StringCommon.Read_SpUtil(getContext(),"userImage");
        if (!UtilTools.isStringNull(headImage)){
            Picasso.with(getContext())
                    .load(headImage)
                    .error(R.mipmap.bg_like_users_extend_pressed)
                    .placeholder(R.mipmap.bg_like_users_extend_pressed)
                    .into(iv_head_set);
        }else{
            iv_head_set.setImageResource(R.mipmap.bg_like_users_extend_pressed);
        }

    }

    @Subscribe
    public void onMessageEvent(LoginBusBeen event) {
        login_TextTip();
    }

    @Subscribe
    public void onMessageEvent(UpHeaderBus event) {
        if (event != null){
            String header = event.headerUrl;
            LogCommon.LogShowPrint("上传头像 地址  11  "+header);
            if (!UtilTools.isStringNull(event.headerUrl)){
                SPUtilsCommon.put(getContext(),"userImage",header);
                Picasso.with(getContext())
                        .load(header)
                        .error(R.mipmap.bg_like_users_extend_pressed)
                        .placeholder(R.mipmap.bg_like_users_extend_pressed)
                        .into(iv_head_set);
            }
        }
    }
}
