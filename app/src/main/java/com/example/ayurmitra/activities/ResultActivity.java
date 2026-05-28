package com.example.ayurmitra.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Herb;
import com.example.ayurmitra.utils.DataProvider;
import com.example.ayurmitra.utils.PreferenceManager;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "HEALTH";
    private TextView tvScoreNumber, tvConditionLabel, tvDominantDosha, tvHerbTitle, tvHerbSub, tvMeditationSub;
    private MaterialCardView cardHerbRec, cardMeditationRec, cardConsultRec;
    private Button btnDone, btnViewHistory;
    private Herb recommendedHerb;
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result);

            prefManager = new PreferenceManager(this);
            initViews();
            setupToolbar();

            int score = getIntent().getIntExtra("HEALTH_SCORE", 100);
            String dosha = getIntent().getStringExtra("DOMINANT_DOSHA");
            if (dosha == null) dosha = "Vata";

            // Persist the result
            if (prefManager != null) {
                prefManager.saveUserHealthData(score, dosha);
            }

            displayResults(score, dosha);
            setupClicks();
        } catch (Exception e) {
            Log.e(TAG, "Error in ResultActivity onCreate", e);
        }
    }

    private void initViews() {
        tvScoreNumber = findViewById(R.id.tvScoreNumber);
        tvConditionLabel = findViewById(R.id.tvConditionLabel);
        tvDominantDosha = findViewById(R.id.tvDominantDosha);
        tvHerbTitle = findViewById(R.id.tvHerbTitle);
        tvHerbSub = findViewById(R.id.tvHerbSub);
        tvMeditationSub = findViewById(R.id.tvMeditationSub);
        
        cardHerbRec = findViewById(R.id.cardHerbRec);
        cardMeditationRec = findViewById(R.id.cardMeditationRec);
        cardConsultRec = findViewById(R.id.cardConsultRec);
        btnDone = findViewById(R.id.btnDone);
        btnViewHistory = findViewById(R.id.btnViewHistory);
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

    private void displayResults(int score, String dosha) {
        if (tvScoreNumber != null) tvScoreNumber.setText(String.valueOf(score));
        if (tvDominantDosha != null) tvDominantDosha.setText(dosha);

        String severity;
        int color;
        int meditationTime;

        if (score >= 85) {
            severity = "Severity: EXCELLENT ✨";
            color = Color.parseColor("#4CAF50");
            meditationTime = 10;
        } else if (score >= 60) {
            severity = "Severity: GOOD 👍";
            color = Color.parseColor("#388E3C");
            meditationTime = 15;
        } else if (score >= 40) {
            severity = "Severity: FAIR 😐";
            color = Color.parseColor("#FFC107");
            meditationTime = 20;
        } else {
            severity = "Severity: NEEDS ATTENTION ⚠️";
            color = Color.parseColor("#F44336");
            meditationTime = 30;
        }

        if (tvConditionLabel != null) {
            tvConditionLabel.setText(severity);
            tvConditionLabel.setTextColor(color);
        }
        if (tvMeditationSub != null) {
            tvMeditationSub.setText(meditationTime + " Minutes Session");
        }

        List<Herb> allHerbs = DataProvider.getHerbs();
        List<Herb> matchingHerbs = new ArrayList<>();
        
        if (allHerbs != null) {
            for (Herb h : allHerbs) {
                if (h != null && h.getDoshaType() != null) {
                    if (h.getDoshaType().contains(dosha) || h.getDoshaType().equalsIgnoreCase("All Doshas")) {
                        matchingHerbs.add(h);
                    }
                }
            }
        }
        
        if (!matchingHerbs.isEmpty()) {
            recommendedHerb = matchingHerbs.get(new Random().nextInt(matchingHerbs.size()));
        }
        
        if (recommendedHerb != null) {
            if (tvHerbTitle != null) tvHerbTitle.setText("Herb: " + recommendedHerb.getName());
            if (tvHerbSub != null) tvHerbSub.setText("View " + recommendedHerb.getName() + " details");
        }
    }

    private void setupClicks() {
        if (cardHerbRec != null) {
            cardHerbRec.setOnClickListener(v -> {
                if (recommendedHerb != null) {
                    Intent intent = new Intent(this, HerbDetailActivity.class);
                    intent.putExtra("herb_data", recommendedHerb);
                    startActivity(intent);
                }
            });
        }

        if (cardMeditationRec != null) {
            cardMeditationRec.setOnClickListener(v -> startActivity(new Intent(this, MeditationActivity.class)));
        }
        if (cardConsultRec != null) {
            cardConsultRec.setOnClickListener(v -> startActivity(new Intent(this, ExpertsActivity.class)));
        }

        if (btnViewHistory != null) {
            btnViewHistory.setOnClickListener(v -> startActivity(new Intent(this, ScoreHistoryActivity.class)));
        }

        if (btnDone != null) {
            btnDone.setOnClickListener(v -> {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        }
    }
}
