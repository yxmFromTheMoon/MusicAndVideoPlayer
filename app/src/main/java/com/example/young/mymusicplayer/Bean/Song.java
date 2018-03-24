package com.example.young.mymusicplayer.Bean;

import java.io.Serializable;

/**
 * Created by Young on 2017/12/23.
 */

public class Song implements Serializable{
    /**
     * 歌手
     */
    public String singer;
    /**
     * 歌曲名
     */
    public String song;
    /**
     * 歌曲的地址
     */
    public String path;
    /**
     * 歌曲长度
     */
    public int duration;
    /**
     * 歌曲的大小
     */
    public long size;

    public Song[] newArray(int size){
        return new Song[size];
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getDuration() {
        return duration;
    }

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public String getSinger() {
        return singer;
    }

    public String getSong() {
        return song;
    }

}
