package com.atmeal.client.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;


import com.alibaba.fastjson.JSON;
import com.atmeal.client.application.MealApplication;
import com.atmeal.client.been.httpbeen.HttpBeen;
import com.atmeal.client.common.DialogCommon;
import com.atmeal.client.common.LogCommon;
import com.atmeal.client.common.StringCommon;
import com.atmeal.client.common.UrlCommon;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/1/3.
 * OkHttp请求
 */
public class OkHttpMannager {

    public OkHttpClient mOkHttpClient;
    private static OkHttpMannager mInstance;
    public static final MediaType mt = MediaType
            .parse("application/x-www-form-urlencoded; charset=utf-8");
    private final static int WHAT_CALLBACK = 1;

    private Call call;

//    private Context mcontext;
//    private int maxLoadTimes = 5;//总的连接次数
//    private int serversLoadTimes = 0;// 每次连接次数

    private OkHttpMannager() {

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)//读取超时
                .connectTimeout(50, TimeUnit.SECONDS)//连接超时
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时
                .build();

    }

    public static OkHttpMannager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpMannager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpMannager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 异步的post请求
     *
     * @param url
    //	 * @param callback
     * @param params
     */
    public void _postAsyn(String url, Object params, Callback c) {
        call = mOkHttpClient.newCall(buildRequest(url, params));
        call.enqueue(c);
    }



    private Request buildRequest(String url, final Object _req) {
        String myurl = url+_req;


        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//        formBody.add("username","zhangsan");//传递键值对参数
//        formBody.add("password","000000");//传递键值对参数
        RequestBody requestBody= formBody.build();
//        RequestBody requestBody = null;
//        try {
//            requestBody = RequestBody.create(mt, URLEncoder.encode(req.getData(),"utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        Request request = new Request.Builder()
                .url(myurl)
                .addHeader("Accept", "application/x-www-form-urlencoded")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .post(requestBody).build();
        return request;
    }
    /***
     * 构造http请求
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildRequest(String url, Object params, String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add("search","Jurassic Park")
                .build();


        // Post请求类型
        if (type.equals("post")) {
            return new Request.Builder().url(url).post(requestBody).build();
        }
        // Get请求类型
        if (type.equals("get")) {
            Log.d("请求地址：", url);
            return new Request.Builder().url(url).build();
        }
        // Put请求类型
        if (type.equals("put")) {
            return new Request.Builder().url(url).put(requestBody).build();
        }
        // Delete请求类型
        if (type.equals("delete")) {
            return new Request.Builder().url(url).delete(requestBody).build();
        }
        return null;

    }

    /***
     * Post异步流请求
     *
     * @param /p
     * @param context
     */
    public void Post_Data(Object params, final Context context,
                          final OkHttp_CallResponse okHttp_callResponse, final boolean isshowtip
    , final String tag) {
//        mcontext = context;
        // 网络判断
        if (!MealApplication.getIstance().isNetworkConnected()) {
            StringCommon.String_Toast(context,"网络异常！请检测网络连接");
            return;
        }
        if (isshowtip) {
            // 开始加载
            DialogCommon.getIstance().CreateMyDialog(context);
        }
//        serversLoadTimes = 0;
        OkHttpMannager.getInstance()._postAsyn(UrlCommon.URL, params,
                new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {

                        if (isshowtip) {
                            // 加载完毕
                            DialogCommon.getIstance().MyDialogCanle();
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                // 需要在主线程的操作。

                                StringCommon.String_Toast(context,"请求超时");
                                okHttp_callResponse.OkHttp_CallonFailure(call);
                                okHttp_callResponse.OkHttp_CallError(tag);
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        if (isshowtip) {
                            // 加载完毕
                            DialogCommon.getIstance().MyDialogCanle();
                        }
                        if (response.isSuccessful()){
                            String msg = response.body().string();
                            LogCommon.LogShowPrint("      服务器数据      "+msg);
                            final JSONObject jsonObject = jsonObjectXutils(msg);
                            final HttpBeen httpBeen = JSON.parseObject(msg,HttpBeen.class);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    // 需要在主线程的操作。
                                    if (httpBeen .getCode() == 200){
                                        okHttp_callResponse.OkHttp_ResponseSuccse(call,response,jsonObject,tag);
                                    }else{
                                        if (!StringCommon.StringNull(httpBeen.getData())){
                                            okHttp_callResponse.OkHttp_CallNoData(tag);
                                        }
                                        okHttp_callResponse.OkHttp_CallToastShow(httpBeen.getTipMessge(),tag);
                                    }
                                }
                            });

                        }else{
                            if (isshowtip) {
                                // 加载完毕
                                DialogCommon.getIstance().MyDialogCanle();
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    // 需要在主线程的操作。

                                    okHttp_callResponse.OkHttp_CallonFailure(call);


                                }
                            });
                        }


                    }

                });
    }




    //取消一个网络请求
    public void Cancel_OkHttp(){
        if (mOkHttpClient != null){
            mOkHttpClient.dispatcher().cancelAll();
            if (call != null){
                call.cancel();
            }

        }
    }

    private JSONObject jsonObjectXutils(String msg){
        JSONObject jsonObject =null;
        if (!msg.equals("")||msg != null){
            try {
                jsonObject=new JSONObject(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
