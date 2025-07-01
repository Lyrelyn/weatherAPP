package com.llw.goodweather.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.MapView;
import com.llw.goodweather.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.baidu.mapapi.map.MapView;
import com.llw.goodweather.R;

/**
 * 地图天气
 */
public class MapWeatherActivity extends AppCompatActivity {
    private MapView mapView; // 地图控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_weather);

        // 手动查找视图
        mapView = findViewById(R.id.map_view);

        // 初始化数据
        initData(savedInstanceState);
    }

    //@Override
    public int getLayoutId() {
        return R.layout.activity_map_weather;
    }

    private void initData(Bundle savedInstanceState) {
        // 透明状态栏
        setStatusBarTransparent();
        // 状态栏黑色字体
        setStatusBarLightMode();
    }

    /**
     * 透明状态栏
     */
    private void setStatusBarTransparent() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = window.getDecorView().getSystemUiVisibility() |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
    }

    /**
     * 状态栏黑色字体
     */
    private void setStatusBarLightMode() {
        View decor = getWindow().getDecorView();
        int ui = decor.getSystemUiVisibility();
        ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decor.setSystemUiVisibility(ui);
    }

    private void setResultAndFinish() {
        Intent resultIntent = new Intent();
        // 可以在这里添加需要返回的数据
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume(); // 地图控件恢复
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause(); // 地图控件暂停
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy(); // 地图控件销毁
        }
    }
}