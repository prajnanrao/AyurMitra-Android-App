package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Yoga;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

public class YogaDetailActivity extends AppCompatActivity {

    private Yoga yoga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_detail);

        yoga = (Yoga) getIntent().getSerializableExtra("YOGA_DATA");

        if (yoga == null) {
            finish();
            return;
        }

        setupToolbar();
        initViews();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitleEnabled(false);
        }
    }

    private void initViews() {
        ImageView ivYoga = findViewById(R.id.ivYogaDetail);
        TextView tvTitle = findViewById(R.id.tvYogaTitle);
        TextView tvCategory = findViewById(R.id.tvCategoryLabel);
        Chip chipDosha = findViewById(R.id.chipDosha);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvBenefits = findViewById(R.id.tvBenefits);
        TextView tvSteps = findViewById(R.id.tvSteps);
        TextView tvPrecautions = findViewById(R.id.tvPrecautions);
        MaterialButton btnWatchVideo = findViewById(R.id.btnWatchVideo);
        MaterialButton btnReadMore = findViewById(R.id.btnReadMore);

        if (yoga.getImageResId() != 0) {
            ivYoga.setImageResource(yoga.getImageResId());
        }
        
        tvTitle.setText(yoga.getName());
        tvCategory.setText(yoga.getCategory());
        
        if (yoga.getSuitableDosha() != null) {
            chipDosha.setText("Suitable for: " + yoga.getSuitableDosha());
            chipDosha.setVisibility(View.VISIBLE);
        } else {
            chipDosha.setVisibility(View.GONE);
        }

        tvDescription.setText(yoga.getDescription());
        tvBenefits.setText(yoga.getBenefits());
        tvSteps.setText(yoga.getSteps());
        tvPrecautions.setText(yoga.getPrecautions());

        btnWatchVideo.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("SEARCH_QUERY", yoga.getYoutubeQuery());
            startActivity(intent);
        });

        if (btnReadMore != null) {
            btnReadMore.setOnClickListener(v -> {
                String query = yoga.getName() + " yoga benefits and steps";
                String url = "https://www.google.com/search?q=" + query.replace(" ", "+");
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", yoga.getName());
                startActivity(intent);
            });
        }
    }
}
