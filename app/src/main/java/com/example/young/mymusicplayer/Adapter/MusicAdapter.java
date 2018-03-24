package com.example.young.mymusicplayer.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.young.mymusicplayer.Activity.MusicActivity;
import com.example.young.mymusicplayer.R;
import com.example.young.mymusicplayer.Bean.Song;
import com.example.young.mymusicplayer.Utils.MusicUtils;

import java.util.List;

/**
 * Created by Young on 2017/12/23.
 */

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<Song> list;
    public MusicAdapter(MusicActivity musicActivity, List<Song> list){
        this.context=musicActivity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            holder=new ViewHolder();
            view=View.inflate(context, R.layout.musicitem,null);
            //实例化对象
            holder.song = (TextView) view.findViewById(R.id.songname);
            holder.singer = (TextView) view.findViewById(R.id.singer);
            holder.duration = (TextView) view.findViewById(R.id.time);
            holder.position = (TextView) view.findViewById(R.id.position);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.song.setText(list.get(i).song.toString());
        holder.singer.setText(list.get(i).singer.toString());
        //时间需要转换一下
        int duration = list.get(i).duration;
        String time = MusicUtils.formatTime(duration);
        holder.duration.setText(time);
        holder.position.setText(i+1+"");
        return view;
        }
    class ViewHolder{
        TextView song;//歌曲名
        TextView singer;//歌手
        TextView duration;//时长
        TextView position;//序号
    }
}
