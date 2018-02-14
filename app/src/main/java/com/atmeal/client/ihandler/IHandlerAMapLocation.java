package com.atmeal.client.ihandler;

import com.amap.api.location.AMapLocation;

/**
 * Created by empty cup on 2018/2/14.
 */

public interface IHandlerAMapLocation {

    void aMapLocationSure(AMapLocation amapLocation);
    void aMapLocationError(int errorCode,String errorInfo);
}
