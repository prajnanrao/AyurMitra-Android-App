package com.example.ayurmitra.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Herb;
import com.google.android.material.chip.Chip;

public class HerbDetailActivity extends AppCompatActivity {

    private Herb herb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herb_detail);

        // ✅ Receive Data with Null Safety
        herb = (Herb) getIntent().getSerializableExtra("herb_data");

        if (herb == null) {
            Toast.makeText(this, "Error: Herb data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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
        // Find Views
        ImageView ivHerbLarge = findViewById(R.id.ivHerbLarge);
        TextView tvTitle = findViewById(R.id.tvHerbTitle);
        TextView tvTagline = findViewById(R.id.tvTagline);
        Chip chipDosha = findViewById(R.id.chipDosha);
        TextView tvBenefits = findViewById(R.id.tvBenefits);
        TextView tvUsage = findViewById(R.id.tvUsage);
        TextView tvPrecautions = findViewById(R.id.tvPrecautions);

        // ✅ Set Image
        if (herb.getImageResId() != 0) {
            ivHerbLarge.setImageResource(herb.getImageResId());
        }

        // ✅ Set Text Fields with Default Values for Safety
        tvTitle.setText(checkValue(herb.getName()));
        tvTagline.setText(checkValue(herb.getTagline()));
        chipDosha.setText("Balance: " + checkValue(herb.getDoshaType()));
        
        tvBenefits.setText(checkValue(herb.getBenefits()));
        tvUsage.setText(checkValue(herb.getUsage()));
        tvPrecautions.setText(checkValue(herb.getPrecautions()));
    }

    private String checkValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "Information not available" : value;
    }
}