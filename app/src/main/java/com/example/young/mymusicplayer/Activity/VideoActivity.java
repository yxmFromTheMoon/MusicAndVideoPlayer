package com.example.young.mymusicplayer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.young.mymusicplayer.R;
import com.example.young.mymusicplayer.Utils.LoadImage;
import com.example.young.mymusicplayer.Utils.VideoUtils;
import com.example.young.mymusicplayer.Bean.Video;
import com.example.young.mymusicplayer.Adapter.VideoAdapter;

import java.util.List;

/**
 * Created by Young on 2017/12/22.
 */

public class VideoActivity extends Activity{
    private MediaPlayer mediaPlayer=new MediaPlayer();
    public VideoActivity instance = null;
    ListView VideoListView;
    VideoAdapter VideoListViewAdapter;
    List<Video> listVideos;
    int videoSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videotab);
        instance = this;
        listVideos= VideoUtils.getVideoData(this);
        videoSize = listVideos.size();
        VideoListViewAdapter = new VideoAdapter(this, listVideos);
        VideoListView = (ListView)findViewById(R.id.videolist);
        VideoListView.setAdapter(VideoListViewAdapter);
        VideoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(VideoActivity.this, VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", listVideos.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        loadImages();
    }
    /**
     * Load images.
     */
    private void loadImages() {
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
            new LoadImagesFromSDCard().execute();
        } else {
            final LoadImage[] photos = (LoadImage[]) data;
            if (photos.length == 0) {
                new LoadImagesFromSDCard().execute();
            }
            for (LoadImage photo : photos) {
                addImage(photo);
            }
        }
    }
    private void addImage(LoadImage... value) {
        for (LoadImage image : value) {
            VideoListViewAdapter.addPhoto(image);
            VideoListViewAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public Object onRetainNonConfigurationInstance() {
        final ListView grid = VideoListView;
        final int count = grid.getChildCount();
        final LoadImage[] list = new LoadImage[count];

        for (int i = 0; i < count; i++) {
            final ImageView v = (ImageView) grid.getChildAt(i);
            list[i] = new LoadImage(
                    ((BitmapDrawable) v.getDrawable()).getBitmap());
        }
        return list;
    }
    /**
     * 获取视频缩略图
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    private Bitmap getVideoThumbnail(String videoPath, int width , int height, int kind){
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    class LoadImagesFromSDCard extends AsyncTask<Object, LoadImage, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            Bitmap bitmap = null;
            for (int i = 0; i < videoSize; i++) {
                bitmap = getVideoThumbnail(listVideos.get(i).getPath(), 120, 120, MediaStore.Video.Thumbnails.MINI_KIND);
                if (bitmap != null) {
                    publishProgress(new LoadImage(bitmap));
                }
            }
            return null;
        }
        @Override
        public void onProgressUpdate(LoadImage... value) {
            addImage(value);
        }
    }
}
