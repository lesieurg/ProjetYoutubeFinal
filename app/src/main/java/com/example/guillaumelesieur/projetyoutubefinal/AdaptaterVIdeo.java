package com.example.guillaumelesieur.projetyoutubefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guillaumelesieur on 26/03/2017.
 */

class AdapterVideo extends ArrayAdapter<Video> {

    public AdapterVideo(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_video,parent, false);
        }

        VideoViewHolder viewHolder = (VideoViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new VideoViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.author = (TextView) convertView.findViewById(R.id.author);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.thumbnails = (ImageView) convertView.findViewById(R.id.thumbnails);
            convertView.setTag(viewHolder);
        }

        Video video = getItem(position);


        viewHolder.title.setText(video.getTitle());
        viewHolder.author.setText(video.getAuthor());
        viewHolder.date.setText(video.getDate());
        viewHolder.description.setText(video.getDescription());
        new DownloadImageTask(viewHolder.thumbnails).execute(video.getThumbnails());

        return convertView;
    }


    private class VideoViewHolder{
        public TextView title;
        public TextView author;
        public TextView date;
        public TextView description;
        public ImageView thumbnails;
    }
}