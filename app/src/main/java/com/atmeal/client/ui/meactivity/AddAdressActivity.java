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
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;
import com.atmeal.client.http.OkHttpMannager;
import com.atmeal.client.http.OkHttp_CallResponse;
import com.atmeal.client.utils.UtilTools;

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
    private EditText edit_address;
    private Button submit_but;

    private String addressTag = "1";
    private String textlads_jt = "女士";

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
        edit_address = findViewById(R.id.edit_address);

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
                        text_lads.setBackgroundResource(R.drawable.home_order_tip);
                        text_jtm.setBackground(null);
                        break;
                    case R.id.text_jtm:
                        textlads_jt= "男士";
                        text_jtm.setBackgroundResource(R.drawable.home_order_tip);
                        text_lads.setBackground(null);
                        break;
                    case R.id.text_home:
                        addressTag = "1";
                        text_home.setBackgroundResource(R.drawable.home_order_tip);
                        address_school.setBackground(null);
                        address_type.setBackground(null);

                        break;
                    case R.id.address_type:
                        addressTag = "2";
                        address_type.setBackgroundResource(R.drawable.home_order_tip);
                        text_home.setBackground(null);
                        address_school.setBackground(null);
                        break;
                    case R.id.address_school:
                        addressTag = "3";
                        address_school.setBackgroundResource(R.drawable.home_order_tip);
                        text_home.setBackground(null);
                        address_type.setBackground(null);
                        break;

                }
            }
        });
    }

    private void http_Address(){
        String name = address_contact.getText().toString();
        String phoneNum = address_phoneNum.getText().toString();
        String door = address_door_num.getText().toString();
        String address = edit_address.getText().toString();

        if (UtilTools.isStringNull(name)){
            StringCommon.String_Toast(context,"请输入联系人");
            return;
        }
        if (UtilTools.isStringNull(phoneNum)){
            StringCommon.String_Toast(context,"请输入电话号码");
            return;
        }
        if (!UtilTools.isPhoneHomeNum(phoneNum)){
            StringCommon.String_Toast(context,"电话号码不正确");
            return;
        }
        if (UtilTools.isStringNull(address)){
            StringCommon.String_Toast(context,"请输入地址");
            return;
        }
        if (UtilTools.isStringNull(door)){
            StringCommon.String_Toast(context,"请输入门牌号");
            return;
        }

        String addaddress = UrlCommon.addAddress+"?usertoken="+ SPUtilsCommon.get(context,"userToken","").toString()
                +"&addressName="+name+"&addressPhone="+phoneNum+"&addressSex="+textlads_jt
                +"&addressDetail="+address+"&addressTag="+addressTag+"&addressDoorNum="+door;

        OkHttpMannager.getInstance().Post_Data(addaddress,context,
                this,true,"AddAddress");
    }

    @Override
    public void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag) {

        finish();
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
        StringCommon.String_Toast(context,msg);
    }
}
