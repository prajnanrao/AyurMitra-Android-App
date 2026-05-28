package com.example.ayurmitra.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Expert;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.button.MaterialButton;

public class ExpertDetailActivity extends AppCompatActivity {

    private Expert expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail);

        expert = (Expert) getIntent().getSerializableExtra("expert_data");

        if (expert == null) {
            Toast.makeText(this, "Error: Expert data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ Log expert view
        FirebaseRealtimeHelper.saveUserAction(this, "VIEW_EXPERT", "Expert: " + expert.getName());

        initToolbar();
        setupUI();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupUI() {
        ImageView ivExpertLarge = findViewById(R.id.ivExpertLarge);
        TextView tvTitle = findViewById(R.id.tvExpertTitle);
        TextView tvSpecialty = findViewById(R.id.tvSpecialty);
        TextView tvRating = findViewById(R.id.tvRating);
        TextView tvExperience = findViewById(R.id.tvExperience);
        TextView tvFee = findViewById(R.id.tvFee);
        TextView tvAbout = findViewById(R.id.tvAbout);
        TextView tvQualifications = findViewById(R.id.tvQualifications);
        TextView tvAvailability = findViewById(R.id.tvAvailability);

        MaterialButton btnWebsite = findViewById(R.id.btnWebsite);
        MaterialButton btnCall = findViewById(R.id.btnCall);
        MaterialButton btnEmail = findViewById(R.id.btnEmail);
        MaterialButton btnConsult = findViewById(R.id.btnConsult);

        if (expert.getImageResId() != 0) {
            ivExpertLarge.setImageResource(expert.getImageResId());
        }

        tvTitle.setText(checkValue(expert.getName()));
        tvSpecialty.setText(checkValue(expert.getSpecialization()));
        tvRating.setText("⭐ " + checkValue(expert.getRating()));
        tvExperience.setText(checkValue(expert.getExperience()));
        tvFee.setText(checkValue(expert.getConsultationFee()));
        tvAbout.setText(checkValue(expert.getAbout()));
        tvQualifications.setText(checkValue(expert.getQualifications()));
        tvAvailability.setText(checkValue(expert.getAvailability()));

        if (expert.getWebsite() != null && !expert.getWebsite().isEmpty()) {
            btnWebsite.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "EXPERT_WEBSITE_CLICK", "Expert: " + expert.getName());
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra("url", expert.getWebsite());
                i.putExtra("title", expert.getName() + "'s Website");
                startActivity(i);
            });
        } else {
            btnWebsite.setVisibility(View.GONE);
        }

        if (expert.getPhone() != null && !expert.getPhone().isEmpty()) {
            btnCall.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "EXPERT_CALL_CLICK", "Expert: " + expert.getName());
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + expert.getPhone()));
                startActivity(i);
            });
        } else {
            btnCall.setVisibility(View.GONE);
        }

        if (expert.getEmail() != null && !expert.getEmail().isEmpty()) {
            btnEmail.setOnClickListener(v -> {
                FirebaseRealtimeHelper.saveUserAction(this, "EXPERT_EMAIL_CLICK", "Expert: " + expert.getName());
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + expert.getEmail()));
                startActivity(Intent.createChooser(i, "Send Email"));
            });
        } else {
            btnEmail.setVisibility(View.GONE);
        }

        btnConsult.setOnClickListener(v -> {
            FirebaseRealtimeHelper.saveUserAction(this, "EXPERT_CONSULT_CLICK", "Expert: " + expert.getName());
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("expert_name", expert.getName());
            intent.putExtra("expert_url", expert.getWebsite());
            startActivity(intent);
        });
    }

    private String checkValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "N/A" : value;
    }
}
