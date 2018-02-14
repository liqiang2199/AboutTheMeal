package com.atmeal.client.ui.meactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atmeal.client.R;
import com.atmeal.client.base.BaseFragmentActivity;
import com.atmeal.client.common.SPUtilsCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;

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
    }

    private void onClick(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.submit_but:
                        http_Address();
                        break;
                }
            }
        });
    }

    private void http_Address(){
        String name = address_contact.getText().toString();
        String phoneNum = address_phoneNum.getText().toString();
        String door = address_door_num.getText().toString();

        String addaddress = UrlCommon.addAddress+"?usertoken="+ SPUtilsCommon.get(context,"userToken","").toString()
                +"&addressName="+address_contact.getText().toString()+"&addressPhone="+phoneNum+"&addressSex="+"女士"
                +"&addressDetail="+"ererererer"+"&addressTag="+"1"+"&addressDoorNum="+"5楼4号";

        OkHttpMannager.getInstance().Post_Data(addaddress,context,
                this,true,"AddAddress");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {

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
