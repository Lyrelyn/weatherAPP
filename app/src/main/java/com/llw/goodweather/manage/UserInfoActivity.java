package com.llw.goodweather.manage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.llw.goodweather.R;
import com.llw.goodweather.ui.MainActivity;
import com.llw.goodweather.ui.MusicListActivity;

import androidx.appcompat.app.AppCompatActivity;

//个人信息界面
public class UserInfoActivity extends AppCompatActivity {

    private TextView tvUsername, tvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setFullScreenImmersion();

        tvUsername = findViewById(R.id.tv_username);
        tvUserId = findViewById(R.id.tv_user_id);
        Button btnLogout = findViewById(R.id.btn_logout);
//        Button btnLogout1 = findViewById(R.id.btn_logout1);


        // 获取传递过来的用户信息
        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");
        int userId = intent.getIntExtra("userId", 0);
        boolean isChecked = intent.getBooleanExtra("isChecked", true); // 获取 isChecked 状态

        // 显示用户信息
        tvUsername.setText("用户名: " + username);
        tvUserId.setText("用户ID: " + userId);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出登录，跳转到登录页面
                Intent intent = new Intent(UserInfoActivity.this, ManagerActivity.class);
                startActivity(intent);
                finish();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 设置点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_home:
                    // 处理首页点击事件
                    Intent intentMain = new Intent(UserInfoActivity.this, MainActivity.class);
                    intentMain.putExtra("isChecked", isChecked);
                    intentMain.putExtra("userId", userId);
                    intentMain.putExtra("userName", username);
                    startActivity(intentMain);
                    break;
                case R.id.nav_music:
                    // 跳转到音乐页面
                    Intent musicIntent = new Intent(UserInfoActivity.this, MusicListActivity.class);
                    musicIntent.putExtra("isChecked", isChecked);
                    musicIntent.putExtra("userId", userId);
                    musicIntent.putExtra("userName", username);
                    startActivity(musicIntent);
                    break;
                case R.id.nav_user_info:

                    break;
            }
            return true;
        });
    }

    protected void setFullScreenImmersion() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }
}
