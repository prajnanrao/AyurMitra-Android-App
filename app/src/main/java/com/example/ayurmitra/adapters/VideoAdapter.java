package com.example.ayurmitra.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.ayurmitra.R;
import com.example.ayurmitra.activities.WebViewActivity;
import com.example.ayurmitra.models.VideoModel;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoModel> videoList;
    private final Context context;

    public VideoAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);
        holder.tvTitle.setText(video.getTitle());
        holder.tvChannel.setText(video.getChannelTitle());

        Glide.with(context)
                .load(video.getThumbnailUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(v -> {
            String videoId = video.getVideoId();
            // Try opening in YouTube App first
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            
            // Web view fallback
            Intent webIntent = new Intent(context, WebViewActivity.class);
            webIntent.putExtra("url", "https://www.youtube.com/watch?v=" + videoId);
            webIntent.putExtra("title", "Ayurveda Videos"); // Changed title here

            if (appIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(appIntent);
            } else {
                context.startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvChannel;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvChannel = itemView.findViewById(R.id.tvChannel);
        }
    }
}
