package com.example.young.mymusicplayer.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.young.mymusicplayer.Service.MusicService;
import com.example.young.mymusicplayer.Adapter.MusicAdapter;
import com.example.young.mymusicplayer.R;
import com.example.young.mymusicplayer.Bean.Song;
import com.example.young.mymusicplayer.Utils.MusicUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/12/22.
 */

public class MusicActivity extends AppCompatActivity {
    private MusicService service;
    private Handler handler=new Handler();
    private ListView myListView;
    private List<Song> list;
    private MusicService.MyBinder binder;
    private MusicAdapter adapter;
    private Button play, next, pre;
    private SeekBar seekBar;
    private TextView currenttime, totaltime;
    private Song song;
    private int currentPosition=0 ;
    private Intent intent;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musictab);
        MusicActivity.verifyStoragePermissions(this);
        initView();
        initCompent();
        seekBar.setMax(0);
        intent=new Intent(this,MusicService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        play.setBackgroundResource(R.drawable.btn_audio_play_normal);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition==0){
                    service.playMusic(list.get(0));
                }
                if(!service.isPause()) {
                    service.pause();
                    play.setBackgroundResource(R.drawable.selector_audio_btn_play);
                }else {
                    service.resume();
                    play.setBackgroundResource(R.drawable.selector_audio_btn_pause);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    next();
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pre();
            }
        });
    }
    public void pre(){
        currentPosition--;
        if(currentPosition<0){
            Toast.makeText(this,"已经是第一首了",Toast.LENGTH_LONG).show();
        }
        else if(service.isPause()) {
            service.playMusic(list.get(currentPosition));
            play.setBackgroundResource(R.drawable.selector_audio_btn_pause);
        }
        else{
            service.playMusic(list.get(currentPosition));
        }
    }
    public void next() {
        currentPosition++;
        if(currentPosition+1>list.size()){
            Toast.makeText(this,"已经是最后一首了",Toast.LENGTH_LONG).show();
        }
         else if (service.isPause()){
            service.playMusic(list.get(currentPosition));
            play.setBackgroundResource(R.drawable.selector_audio_btn_pause);
        }
        else {
            service.playMusic(list.get(currentPosition));
        }
    }
    public void initView() {
        myListView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();
        list = MusicUtils.getMusicData(this);
        adapter = new MusicAdapter(this, list);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentPosition = i;
                song=list.get(currentPosition);
                totaltime.setText(MusicUtils.formatTime(song.getDuration()));
                seekBar.setMax(song.getDuration());
                service.playMusic(song);
                play.setBackgroundResource(R.drawable.selector_audio_btn_pause);
            }
        });
    }
    public static void verifyStoragePermissions(AppCompatActivity appCompatActivity) {

        try {
            //检测是否有读的权限
            int permission = ActivityCompat.checkSelfPermission(appCompatActivity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有读的权限，去申请读的权限，会弹出对话框
                ActivityCompat.requestPermissions(appCompatActivity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder=(MusicService.MyBinder)iBinder;
            service=binder.getService();
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(b==true){//此处一定要判断是否是用户改变的进度条进度
                        service.seekToPosition(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.post(seekBartime);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service=null;
        }
    };
    private void initCompent(){
        play = (Button) findViewById(R.id.play);
        next = (Button) findViewById(R.id.next);
        pre = (Button) findViewById(R.id.pre);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        currenttime = (TextView) findViewById(R.id.currenttime);
        totaltime = (TextView) findViewById(R.id.totaltime);
    }
    Runnable seekBartime=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(service.getPlayPosition());
            currenttime.setText(MusicUtils.formatTime(service.getPlayPosition()));
            handler.postDelayed(seekBartime,1000);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
