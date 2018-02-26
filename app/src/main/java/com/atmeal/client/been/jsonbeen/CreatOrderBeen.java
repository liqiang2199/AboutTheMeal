package com.atmeal.client.been.jsonbeen;

/**
 * Created by Administrator on 2018/2/25.
 * 创建订单成功
 */

public class CreatOrderBeen {
    private String OrderId;
    private String IsFightAlone;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getIsFightAlone() {
        return IsFightAlone;
    }

    public void setIsFightAlone(String isFightAlone) {
        IsFightAlone = isFightAlone;
    }
}
