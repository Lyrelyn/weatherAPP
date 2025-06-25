package com.llw.goodweather.location;

import com.baidu.location.BDLocation;

/**
 * 定位接口
 */
public interface LocationCallback {

    void onReceiveLocation(BDLocation bdLocation);//接收定位
}
