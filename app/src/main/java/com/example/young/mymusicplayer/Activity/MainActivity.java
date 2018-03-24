package com.example.young.mymusicplayer.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    private TabHost tabHost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("音乐").setContent(new Intent().setClass(MainActivity.this, MusicActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("视频").setContent(new Intent().setClass(MainActivity.this, VideoActivity.class)));
    }
}
