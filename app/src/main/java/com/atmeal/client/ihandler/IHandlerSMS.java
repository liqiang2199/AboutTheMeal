package com.atmeal.client.ihandler;

/**
 * Created by Administrator on 2018/1/20.
 */

public interface IHandlerSMS {
    void onSMSSuccess(int event, int result, Object data);
    void onSMSFaile(int event, int result, Object data);
}
