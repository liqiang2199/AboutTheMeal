package com.atmeal.client.been.httpbeen;

/**
 * Created by Administrator on 2018/1/19.
 * 请求的主要参数
 */

public class HttpBeen {
    private int code;
    private String tipMessge;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTipMessge() {
        return tipMessge;
    }

    public void setTipMessge(String tipMessge) {
        this.tipMessge = tipMessge;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
