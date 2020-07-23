package com.videoplayerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

    private Context context;
    ArrayList<File> videoArrayList;

    public VideoAdapter(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);

        return new VideoHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {

        holder.txtVideoName.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.imgVideo.setImageBitmap(bitmap);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,VideoPlayerActivity.class);
                intent.putExtra("pos",holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (videoArrayList.size() > 0)
        {
            return videoArrayList.size();
        }
        else {return 1;}
    }
}

class VideoHolder extends RecyclerView.ViewHolder {

    TextView txtVideoName;
    ImageView imgVideo;
    CardView cardView;

    VideoHolder(View view) {
        super(view);

        txtVideoName = view.findViewById(R.id.txtVideoName);
        imgVideo = view.findViewById(R.id.imgVideo);
        cardView = view.findViewById(R.id.cardView);
    }
}
