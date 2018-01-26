package com.atmeal.client.http;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2017/1/3.
 * OkHttp 请求回掉接口
 */
public interface OkHttp_CallResponse {
    void OkHttp_ResponseSuccse(Call call, Response response, JSONObject jsonObject, String tag);

    void OkHttp_CallonFailure(Call call);

    void OkHttp_CallNoData(String tag);//没有数据是显示 布局
    void OkHttp_CallError(String tag);//请求发送错误时
    void OkHttp_CallToastShow(String msg,String tag);//提示框
}
