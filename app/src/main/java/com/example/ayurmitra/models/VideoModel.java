package com.example.ayurmitra.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VideoModel {
    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String channelTitle;

    public VideoModel(String videoId, String title, String thumbnailUrl, String channelTitle) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() { return videoId; }
    public String getTitle() { return title; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getChannelTitle() { return channelTitle; }

    // YouTube API Response classes
    public static class YouTubeResponse {
        @SerializedName("items")
        public List<YouTubeItem> items;
    }

    public static class YouTubeItem {
        @SerializedName("id")
        public VideoId id;
        @SerializedName("snippet")
        public Snippet snippet;
    }

    public static class VideoId {
        @SerializedName("videoId")
        public String videoId;
    }

    public static class Snippet {
        @SerializedName("title")
        public String title;
        @SerializedName("channelTitle")
        public String channelTitle;
        @SerializedName("thumbnails")
        public Thumbnails thumbnails;
    }

    public static class Thumbnails {
        @SerializedName("high")
        public Thumbnail high;
    }

    public static class Thumbnail {
        @SerializedName("url")
        public String url;
    }
}
