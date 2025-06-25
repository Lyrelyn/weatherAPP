package com.llw.goodweather.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.llw.goodweather.R;
import com.llw.goodweather.WeatherApp;
import com.llw.goodweather.db.bean.Music;
import com.llw.goodweather.ui.adapter.MusicAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MusicListActivity extends AppCompatActivity {

    private MusicAdapter musicAdapter;
    private ListView listView;
    private List<Music> musicList=new ArrayList<>();
    private WeatherApp App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        setFullScreenImmersion();
        //请求权限
        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        },0);

        //设置listView
        listView=findViewById(R.id.listView);
        musicAdapter=new MusicAdapter(this,musicList);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (musicList.size()!=0){
                    if (App.getService() != null) {
                        App.getService().setPosition(position);
                        startActivity(new Intent(MusicListActivity.this,MusicActivity.class));
                    }else{
                        Log.d("MUSIC","不行啊");
                    }

                }
            }
        });

    }



        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            boolean granted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                // 权限被授予，获取音乐
                Log.d("Music", "权限通过!");
                getMusic();
            } else {
                // 权限被拒绝，显示提示信息
                showPermissionDeniedDialog();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    // 显示权限被拒绝的提示对话框
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限被拒绝");
        builder.setMessage("您拒绝了访问外部存储的权限，这可能会导致应用无法正常工作。请在设置中授予权限。");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到应用设置界面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户取消了，可以根据实际情况进行相应处理
            }
        });
        builder.setNeutralButton("重新请求权限", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 重新请求权限
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }, 0);
            }
        });
        builder.show();
    }



    //使用ContentResolver获取音乐
//    private void getMusic(){
//        ContentResolver contentResolver = getContentResolver();
//       Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        if (cursor!=null){
//            while (cursor.moveToNext()){
//                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                if (duration==null)continue;
//                if (Integer.parseInt(duration)>5000){
//                    int i = Integer.parseInt(duration);
//                    String time = String.format(getString(R.string.s_s), format(i / 1000 / 60), format(i / 1000 % 60));
//                    Music music = new Music(title, artist, data, time,i);
//                    musicList.add(music);
//                }
//                musicAdapter.notifyDataSetChanged();
//                // 检查 MusicService 对象是否为 null，然后再调用 setMusicList 方法
//                if (App.getService() != null) {
//                   App.getService().setMusicList(musicList);
//                }
//            }
//            cursor.close();
//        }
//
//    }

    private void getMusic() {
        // 获取 res/raw 文件夹下的所有资源 ID
        Field[] fields = R.raw.class.getFields();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        for (Field field : fields) {
            try {
                // 获取资源 ID
                int resId = field.getInt(null);

                // 使用资源 ID 创建 URI
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resId);

                // 设置数据源并获取元数据
                retriever.setDataSource(this, uri);

                // 获取歌曲标题（如果没有标题，则使用资源名称）
                String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                if (title == null || title.isEmpty()) {
                    title = field.getName();
                }

                // 获取艺术家信息
                String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                if (artist == null || artist.isEmpty()) {
                    artist = "Unknown";
                }

                // 获取歌曲时长（毫秒）
                String durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int duration = (durationStr != null) ? Integer.parseInt(durationStr) : 0;

                // 格式化时长
                String time = formatDuration(duration);

                if (duration > 5000) {
                    Music music = new Music(title, artist, String.valueOf(resId), time, duration);
                    musicList.add(music);
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        // 释放资源
        //retriever.release();

        musicAdapter.notifyDataSetChanged();

        // 检查 MusicService 对象是否为 null，然后再调用 setMusicList 方法
        if (App.getService() != null) {
            App.getService().setMusicList(musicList);
        }
    }

    // 格式化时长的辅助方法
    private String formatDuration(int durationMs) {
        int totalSeconds = durationMs / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private String format(int day) {
        if (day < 10) {
            return getString(R.string._0) + day;
        }
        return String.valueOf(day);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getService()!=null&&App.getService().isPlaying()){
            musicAdapter.setPlayingPosition(App.getService().getPosition());
            musicAdapter.notifyDataSetChanged();
        }
    }
    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

