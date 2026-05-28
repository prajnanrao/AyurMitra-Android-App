package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurmitra.R;
import com.example.ayurmitra.adapters.RecommendedExpertsAdapter;
import com.example.ayurmitra.models.Expert;
import com.example.ayurmitra.utils.DataProvider;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.example.ayurmitra.utils.PreferenceManager;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private PreferenceManager prefManager;

    private TextView tvWelcome, tvPersonalizedMsg, tvLastScore, tvLastDosha, tvViewHistory;
    private MaterialCardView cardLastReport;
    private ImageView ivLogout, ivHistory;
    
    private LinearLayout layoutRecommended;
    private RecyclerView rvRecommended;
    private RecommendedExpertsAdapter recommendedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefManager = new PreferenceManager(this);

        initViews();
        setupPersonalization();
        setupClickListeners();

        // ✅ Log App Home View
        FirebaseRealtimeHelper.saveUserAction(this, "HOME_VIEWED", "User arrived at Home");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPersonalization();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvPersonalizedMsg = findViewById(R.id.tvPersonalizedMsg);
        tvLastScore = findViewById(R.id.tvLastScore);
        tvLastDosha = findViewById(R.id.tvLastDosha);
        tvViewHistory = findViewById(R.id.tvViewHistory);
        cardLastReport = findViewById(R.id.cardLastReport);
        ivLogout = findViewById(R.id.ivLogout);
        ivHistory = findViewById(R.id.ivHistory);
        
        layoutRecommended = findViewById(R.id.layoutRecommended);
        rvRecommended = findViewById(R.id.rvRecommendedExperts);
    }

    private void setupPersonalization() {
        try {
            String dosha = prefManager.getDominantDosha();
            int score = prefManager.getLastScore();

            if (dosha != null && score != -1) {
                tvPersonalizedMsg.setText("Your balance: " + dosha + " is dominant");
                cardLastReport.setVisibility(View.VISIBLE);
                tvLastScore.setText("Score: " + score);
                tvLastDosha.setText("Dominant: " + dosha);
                setupRecommendations(dosha);
            } else {
                tvPersonalizedMsg.setText("Find your balance with Ayurveda");
                cardLastReport.setVisibility(View.GONE);
                layoutRecommended.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupRecommendations(String userDosha) {
        List<Expert> recommendedList = DataProvider.getRecommendedExperts(userDosha);
        if (recommendedList != null && !recommendedList.isEmpty()) {
            layoutRecommended.setVisibility(View.VISIBLE);
            recommendedAdapter = new RecommendedExpertsAdapter(this, recommendedList);
            rvRecommended.setAdapter(recommendedAdapter);
        } else {
            layoutRecommended.setVisibility(View.GONE);
        }
    }

    private void setupClickListeners() {
        if (ivHistory != null) {
            ivHistory.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "History Icon");
                startActivity(new Intent(HomeActivity.this, ScoreHistoryActivity.class));
            });
        }
        if (tvViewHistory != null) {
            tvViewHistory.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "History Text Link");
                startActivity(new Intent(HomeActivity.this, ScoreHistoryActivity.class));
            });
        }

        if (ivLogout != null) {
            ivLogout.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "LOGOUT_CLICKED", "User logging out");
                FirebaseAuth.getInstance().signOut();
                prefManager.clearAll();
                prefManager.setLoggedIn(false);
                Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        View videoCard = findViewById(R.id.cardVideos);
        if (videoCard != null) {
            videoCard.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Video Activity");
                startActivity(new Intent(HomeActivity.this, VideoActivity.class));
            });
        }

        View fab = findViewById(R.id.fabScore);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Health Assessment");
                startActivity(new Intent(HomeActivity.this, HealthAssessmentActivity.class));
            });
        }

        View yoga = findViewById(R.id.cardYoga);
        if (yoga != null) {
            yoga.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Yoga Activity");
                startActivity(new Intent(HomeActivity.this, YogaActivity.class));
            });
        }

        View meditation = findViewById(R.id.cardMeditation);
        if (meditation != null) {
            meditation.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Meditation Activity");
                startActivity(new Intent(HomeActivity.this, MeditationActivity.class));
            });
        }

        View remedies = findViewById(R.id.cardRemedies);
        if (remedies != null) {
            remedies.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Herb Activity");
                startActivity(new Intent(HomeActivity.this, HerbActivity.class));
            });
        }

        View consult = findViewById(R.id.cardConsult);
        if (consult != null) {
            consult.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Experts Activity");
                startActivity(new Intent(HomeActivity.this, ExpertsActivity.class));
            });
        }

        View diet = findViewById(R.id.cardDiet);
        if (diet != null) {
            diet.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Dosha Activity");
                startActivity(new Intent(HomeActivity.this, DoshaActivity.class));
            });
        }

        if (cardLastReport != null) {
            cardLastReport.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE", "Last Report Result");
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("HEALTH_SCORE", prefManager.getLastScore());
                intent.putExtra("DOMINANT_DOSHA", prefManager.getDominantDosha());
                startActivity(intent);
            });
        }
    }
}
