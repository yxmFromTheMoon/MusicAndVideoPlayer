package com.example.young.mymusicplayer.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.young.mymusicplayer.Bean.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/12/25.
 */

public class VideoUtils {
    public static List<Video> getVideoData(Context context){
        List<Video> list=new ArrayList<Video>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                Video video = new Video();
                video.name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                video.path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                video.videoTime=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                video.size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                list.add(video);
            }
            cursor.close();
        }
        return list;
    }
}
