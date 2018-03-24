package com.example.young.mymusicplayer.Bean;

import java.io.Serializable;

/**
 * Created by Young on 2017/12/25.
 */

public class Video implements Serializable{
    public String name;
    public String path;
    public long size;
    public long videoTime;

    public void setSize(long size) {
        this.size = size;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVideoTime(long videoTime) {
        this.videoTime = videoTime;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public long getVideoTime() {
        return videoTime;
    }
}
