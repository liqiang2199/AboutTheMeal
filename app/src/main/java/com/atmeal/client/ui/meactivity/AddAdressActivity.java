package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.been.busbeen.LoginBusBeen;
import com.atmeal.client.been.jsonbeen.LoginBeen;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.utils.UtilTools;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by empty cup on 2018/2/14.
 * 添加地址
 */

@SuppressLint("Registered")
public class AddAdressActivity extends BaseFragmentActivity implements OkHttp_CallResponse{


    private EditText address_contact;
    private TextView text_lads,text_jtm;
    private TextView text_home,address_type,address_school;
    private EditText address_phoneNum;
    private EditText address_door_num;
    private Button submit_but;

    private String textlads_jt = "女士";
    private String addressTag = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        InitView();
    }

    @Override
    public void InitView() {
        Init_TitleView();
        titleback.setText("收货地址");

        address_contact = findViewById(R.id.address_contact);
        text_lads = findViewById(R.id.text_lads);
        text_jtm = findViewById(R.id.text_jtm);
        text_home = findViewById(R.id.text_home);
        address_type = findViewById(R.id.address_type);
        address_school = findViewById(R.id.address_school);
        address_phoneNum = findViewById(R.id.address_phoneNum);
        address_door_num = findViewById(R.id.address_door_num);
        submit_but = findViewById(R.id.submit_but);

        onClick(submit_but);
        onClick(text_lads);
        onClick(text_jtm);
        onClick(text_home);
        onClick(address_type);
        onClick(address_school);
    }

    private void onClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.submit_but:
                        http_Address();
                        break;
                    case R.id.text_lads:
                        textlads_jt= "女士";

                        break;
                    case R.id.text_jtm:
                        textlads_jt= "男士";
                        break;
                    case R.id.text_home:
                        addressTag = "1";
                        break;
                    case R.id.address_type:
                        addressTag = "2";
                        break;
                    case R.id.address_school:
                        addressTag = "3";
                        break;
                }
            }
        });
    }

    private void http_Address(){
        String name = address_contact.getText().toString();
        String phoneNum = address_phoneNum.getText().toString();
        String door = address_door_num.getText().toString();
        if (!UtilTools.isPhoneHomeNum(phoneNum)){
            StringCommon.String_Toast(context,"输入的电话号码不正确");
            return;
        }

        String addaddress = UrlCommon.addAddress+"?usertoken="+
                SPUtilsCommon.get(context,"userToken","").toString()
                +"&addressName="+name+
                "&addressPhone="+phoneNum+"&addressSex="+textlads_jt
                +"&addressDetail="+"ererererer"+"&addressTag="+"1"+"&addressDoorNum="+door;

        OkHttpMannager.getInstance().Post_Data(addaddress,context,
                this,true,"AddAddress");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {
        try {
            String tipMessge = jsonObject.getString("tipMessge");
            StringCommon.String_Toast(context,tipMessge);
            finish();
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
