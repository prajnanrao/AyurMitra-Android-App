package com.example.ayurmitra.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Dosha;

public class DoshaDetailActivity extends AppCompatActivity {

    private Dosha dosha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosha_detail);

        // ✅ Receive Data with Null Safety
        dosha = (Dosha) getIntent().getSerializableExtra("dosha_data");

        if (dosha == null) {
            Toast.makeText(this, "Error: Dosha data missing", Toast.LENGTH_SHORT).show();
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
        ImageView ivDoshaLarge = findViewById(R.id.ivDoshaLarge);
        TextView tvTitle = findViewById(R.id.tvDoshaTitle);
        TextView tvTagline = findViewById(R.id.tvDoshaTagline);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvCharacteristics = findViewById(R.id.tvCharacteristics);
        TextView tvSymptoms = findViewById(R.id.tvSymptoms);
        TextView tvDiet = findViewById(R.id.tvDiet);
        TextView tvLifestyle = findViewById(R.id.tvLifestyle);

        // ✅ Set Image
        if (dosha.getImageResId() != 0) {
            ivDoshaLarge.setImageResource(dosha.getImageResId());
        }

        // ✅ Set Text Fields with Default Values for Safety
        tvTitle.setText(checkValue(dosha.getName()));
        tvTagline.setText(checkValue(dosha.getTagline()));
        tvDescription.setText(checkValue(dosha.getDescription()));
        tvCharacteristics.setText(checkValue(dosha.getCharacteristics()));
        tvSymptoms.setText(checkValue(dosha.getSymptoms()));
        tvDiet.setText(checkValue(dosha.getDiet()));
        tvLifestyle.setText(checkValue(dosha.getLifestyle()));
    }

    private String checkValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "Information not available" : value;
    }
}
