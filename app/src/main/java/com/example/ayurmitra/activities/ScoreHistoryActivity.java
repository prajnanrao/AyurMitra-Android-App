package com.example.ayurmitra.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurmitra.R;
import com.example.ayurmitra.adapters.HistoryAdapter;
import com.example.ayurmitra.api.ApiCallback;
import com.example.ayurmitra.api.FirebaseHealthScoreApi;
import com.example.ayurmitra.database.HealthScoreRepository;
import com.example.ayurmitra.models.HealthScore;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class ScoreHistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private List<HealthScore> historyList = new ArrayList<>();
    private HealthScoreRepository repository;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    
    private LinearLayout layoutBreakdown;
    private ImageView ivExpand;
    private MaterialCardView cardHowCalculated;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        repository = new HealthScoreRepository(this, new FirebaseHealthScoreApi());
        initViews();
        setupToolbar();
        setupRecyclerView();
        setupCalculationCard();
        fetchHistory();
    }

    private void initViews() {
        rvHistory = findViewById(R.id.rvHistory);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);
        cardHowCalculated = findViewById(R.id.cardHowCalculated);
        layoutBreakdown = findViewById(R.id.layoutBreakdown);
        ivExpand = findViewById(R.id.ivExpand);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void setupRecyclerView() {
        adapter = new HistoryAdapter(historyList, this::showScoreBreakdown);
        rvHistory.setAdapter(adapter);
    }

    private void setupCalculationCard() {
        if (cardHowCalculated != null) {
            cardHowCalculated.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                if (layoutBreakdown != null) layoutBreakdown.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                if (ivExpand != null) ivExpand.setImageResource(isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float);
            });
        }
    }

    private void fetchHistory() {
        String identifier = FirebaseRealtimeHelper.getUserIdentifier(this);
        
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        repository.getScoreHistory(identifier, new ApiCallback<List<HealthScore>>() {
            @Override
            public void onSuccess(List<HealthScore> result) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (result != null && !result.isEmpty()) {
                    historyList.clear();
                    historyList.addAll(result);
                    adapter.notifyDataSetChanged();
                    if (tvEmpty != null) tvEmpty.setVisibility(View.GONE);
                } else {
                    if (tvEmpty != null) tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(ScoreHistoryActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showScoreBreakdown(HealthScore score) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_score_breakdown, null);
        
        TextView tvTitle = view.findViewById(R.id.tvBreakdownTitle);
        TextView tvTotalLabel = view.findViewById(R.id.tvTotalScoreLabel);
        
        if (tvTitle != null) tvTitle.setText("Score: " + score.getTotalScore());
        if (tvTotalLabel != null) tvTotalLabel.setText("Detailed breakdown of this specific assessment.");

        setTextAndProgress(view, R.id.tvWaterScore, R.id.progressWater, score.getWaterScore(), 20);
        setTextAndProgress(view, R.id.tvActivityScore, R.id.progressActivity, score.getActivityScore(), 20);
        setTextAndProgress(view, R.id.tvSleepScore, R.id.progressSleep, score.getSleepScore(), 15);
        setTextAndProgress(view, R.id.tvNutritionScore, R.id.progressNutrition, score.getNutritionScore(), 15);
        setTextAndProgress(view, R.id.tvStressScore, R.id.progressStress, score.getStressScore(), 10);
        setTextAndProgress(view, R.id.tvJunkScore, R.id.progressJunk, score.getJunkScore(), 10);
        setTextAndProgress(view, R.id.tvBmiScore, R.id.progressBmi, score.getBmiScore(), 10);

        View closeBtn = view.findViewById(R.id.btnCloseBreakdown);
        if (closeBtn != null) closeBtn.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void setTextAndProgress(View root, int textId, int progressId, int score, int max) {
        TextView tv = root.findViewById(textId);
        LinearProgressIndicator progress = root.findViewById(progressId);
        if (tv != null) tv.setText(score + " / " + max + " pts");
        if (progress != null) {
            progress.setMax(max);
            progress.setProgress(score);
        }
    }
}
