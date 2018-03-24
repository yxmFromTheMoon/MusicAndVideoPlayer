package com.example.young.mymusicplayer.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.young.mymusicplayer.R;
import com.example.young.mymusicplayer.Bean.Video;

/**
 * Created by Young on 2017/12/25.
 */

public class VideoPlayerActivity extends AppCompatActivity{
    private Video video;
    private VideoView play;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplay);
        play=(VideoView)findViewById(R.id.playvideo);
        Thread thread=new Thread(runnable);
        thread.start();
        MediaController mediaController=new MediaController(this);
        play.setMediaController(mediaController);
        mediaController.setMediaPlayer(play);
   }
   Runnable runnable=new Runnable() {
       @Override
       public void run() {
           Intent intent = getIntent();
           Bundle data = intent.getExtras();
           video = (Video) data.getSerializable("video");
           play.setVideoPath(video.path);
           play.start();
       }
   };
}

