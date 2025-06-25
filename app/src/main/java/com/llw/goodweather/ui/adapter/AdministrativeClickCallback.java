package com.llw.goodweather.ui.adapter;

import android.view.View;

import com.llw.goodweather.utils.AdministrativeType;

//行政区 点击事件
public interface AdministrativeClickCallback {
    void onAdministrativeItemClick(View view, int position, AdministrativeType type);
    //view  点击视图，position 点击位置，type     行政区类型
}

