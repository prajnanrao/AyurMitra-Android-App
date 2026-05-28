package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayurmitra.R;
import com.example.ayurmitra.adapters.VideoAdapter;
import com.example.ayurmitra.api.YouTubeApiService;
import com.example.ayurmitra.models.VideoModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView rvVideos;
    private VideoAdapter adapter;
    private List<VideoModel> videoList = new ArrayList<>();
    private ProgressBar progressBar;
    
    private TextInputEditText etSearch;
    private TextInputLayout tilSearch;
    private LinearLayout layoutNoResults;
    private MaterialButton btnSearchWeb;
    private TextView tvNoResultsMsg;

    private static final String YOUTUBE_API_KEY = "AIzaSyC_3N6NMWTaIEI5B5q-AJLZovFQfeyjgz8";
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupSearch();
        
        String initialQuery = getIntent().getStringExtra("SEARCH_QUERY");
        if (initialQuery != null && !initialQuery.isEmpty()) {
            etSearch.setText(initialQuery);
            fetchVideos(initialQuery);
        } else {
            fetchVideos("Ayurveda health tips");
        }
    }

    private void initViews() {
        rvVideos = findViewById(R.id.rvVideos);
        progressBar = findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearchVideo);
        tilSearch = findViewById(R.id.tilSearchVideo);
        layoutNoResults = findViewById(R.id.layoutNoResults);
        btnSearchWeb = findViewById(R.id.btnSearchWeb);
        tvNoResultsMsg = findViewById(R.id.tvNoResultsMsg);

        btnSearchWeb.setOnClickListener(v -> performWebSearch());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, videoList);
        rvVideos.setAdapter(adapter);
    }

    private void setupSearch() {
        // Search on keyboard action
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });

        // Search on end icon click (Rocket/Send icon)
        tilSearch.setEndIconOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        if (!query.isEmpty()) {
            String fullQuery = query;
            if (!query.toLowerCase().contains("ayurveda") && !query.toLowerCase().contains("yoga")) {
                fullQuery = "Ayurveda " + query;
            }
            fetchVideos(fullQuery);
        } else {
            fetchVideos("Ayurveda health tips");
        }
    }

    private void performWebSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) query = "Ayurveda health tips";
        
        String url = "https://www.youtube.com/results?search_query=" + query.replace(" ", "+");
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "YouTube: " + query);
        startActivity(intent);
    }

    private void fetchVideos(String query) {
        progressBar.setVisibility(View.VISIBLE);
        layoutNoResults.setVisibility(View.GONE);
        rvVideos.setVisibility(View.VISIBLE);
        videoList.clear();
        adapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeApiService service = retrofit.create(YouTubeApiService.class);
        Call<VideoModel.YouTubeResponse> call = service.searchVideos(
                "snippet",
                query,
                "video",
                50,
                YOUTUBE_API_KEY
        );

        call.enqueue(new Callback<VideoModel.YouTubeResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoModel.YouTubeResponse> call, @NonNull Response<VideoModel.YouTubeResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<VideoModel.YouTubeItem> items = response.body().items;
                    if (items != null && !items.isEmpty()) {
                        for (VideoModel.YouTubeItem item : items) {
                            videoList.add(new VideoModel(
                                    item.id.videoId,
                                    item.snippet.title,
                                    item.snippet.thumbnails.high.url,
                                    item.snippet.channelTitle
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmptyState(query);
                    }
                } else {
                    showEmptyState(query);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoModel.YouTubeResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                showEmptyState(query);
            }
        });
    }

    private void showEmptyState(String query) {
        rvVideos.setVisibility(View.GONE);
        layoutNoResults.setVisibility(View.VISIBLE);
        tvNoResultsMsg.setText("Could not find videos for '" + query + "'");
        btnSearchWeb.setText("Search YouTube 🚀");
    }
}
