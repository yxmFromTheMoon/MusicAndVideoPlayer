package com.example.young.mymusicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.young.mymusicplayer.Activity.MusicActivity;
import com.example.young.mymusicplayer.Bean.Song;
import com.example.young.mymusicplayer.Utils.MusicUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/12/23.
 */
public class MusicService extends Service {
    private MyBinder myBinder=new MyBinder();
    private Song song;
    public  MediaPlayer mediaPlayer=new MediaPlayer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
    //跳到指定位置
    public void seekToPosition(int position){
        mediaPlayer.seekTo(position);
    }
    //获取当前位置
    public int getPlayPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    public void playMusic(Song song){
        this.song=song;
        Thread thread=new Thread(runnable);
        thread.start();
    }
    public boolean isPause(){
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            return false;
        }
        return true;
    }
    public void pause(){
        if(!isPause()){
            mediaPlayer.pause();
        }else
            mediaPlayer.start();
    }
    public void resume(){
        if(isPause()){
            mediaPlayer.start();
        }
    }
    public void clear(){
        mediaPlayer.release();
        mediaPlayer.stop();
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(song.path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    };
}
