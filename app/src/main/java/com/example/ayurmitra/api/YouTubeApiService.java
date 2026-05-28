package com.example.ayurmitra.api;

import com.example.ayurmitra.models.VideoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    @GET("search")
    Call<VideoModel.YouTubeResponse> searchVideos(
            @Query("part") String part,
            @Query("q") String query,
            @Query("type") String type,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}
