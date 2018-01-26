package com.atmeal.client.ihandler;

/**
 * Created by Administrator on 2018/1/20.
 */

public interface IHandlerSMSAuth {
    void onAuthSMSSuccess(int event, int result, Object data);
    void onAuthSMSFaile(int event, int result, Object data);
}
