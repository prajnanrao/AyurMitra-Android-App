package com.example.ayurmitra.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurmitra.R;
import com.example.ayurmitra.api.ApiCallback;
import com.example.ayurmitra.api.FirebaseHealthScoreApi;
import com.example.ayurmitra.database.HealthScoreRepository;
import com.example.ayurmitra.models.HealthScore;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.example.ayurmitra.utils.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SymptomsActivity extends AppCompatActivity {

    private static final String TAG = "HEALTH";

    private AutoCompleteTextView dropdownSymptoms;
    private ChipGroup chipGroupSelected;
    
    private CheckBox cbFatigue, cbHeadache, cbSleepDist, cbAnxiety, cbDigestion,
            cbBloating, cbConstipation, cbReflux, cbJointPain, cbWeightGain,
            cbIllness, cbFocus;

    private Slider sliderWater, sliderActivity, sliderSleep, sliderStress;
    private TextView tvWaterVal, tvActivityVal, tvSleepVal, tvStressVal;
    
    private AutoCompleteTextView dropdownDiet, dropdownJunk;
    
    private TextView tvLiveScore, tvScoreStatus, tvBMIResult;
    private MaterialCardView cardScore;
    
    private MaterialButton btnCalculateBMI, btnAnalyze, btnReset;

    private float userBMI = -1;
    private int currentScore = 0;
    private PreferenceManager prefManager;
    private HealthScoreRepository repository;
    private boolean isRestoring = false;

    private final String[] allSymptoms = {
            "Fatigue / Low Energy", "Frequent Headaches", "Sleep Disturbances",
            "Anxiety / Stress", "Poor Digestion", "Bloating / Gas",
            "Constipation", "Acid Reflux", "Joint Pain",
            "Weight Gain", "Frequent Illness", "Lack of Focus"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_symptoms);

            prefManager = new PreferenceManager(this);
            repository = new HealthScoreRepository(this, new FirebaseHealthScoreApi());
            
            initToolbar();
            initViews();
            setupListeners();
            restoreSavedState();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void initViews() {
        dropdownSymptoms = findViewById(R.id.dropdownSymptoms);
        chipGroupSelected = findViewById(R.id.chipGroupSelected);

        cbFatigue = findViewById(R.id.cbFatigue);
        cbHeadache = findViewById(R.id.cbHeadache);
        cbSleepDist = findViewById(R.id.cbSleepDist);
        cbAnxiety = findViewById(R.id.cbAnxiety);
        cbDigestion = findViewById(R.id.cbDigestion);
        cbBloating = findViewById(R.id.cbBloating);
        cbConstipation = findViewById(R.id.cbConstipation);
        cbReflux = findViewById(R.id.cbReflux);
        cbJointPain = findViewById(R.id.cbJointPain);
        cbWeightGain = findViewById(R.id.cbWeightGain);
        cbIllness = findViewById(R.id.cbIllness);
        cbFocus = findViewById(R.id.cbFocus);

        sliderWater = findViewById(R.id.sliderWater);
        sliderActivity = findViewById(R.id.sliderActivity);
        sliderSleep = findViewById(R.id.sliderSleep);
        sliderStress = findViewById(R.id.sliderStress);

        tvWaterVal = findViewById(R.id.tvWaterVal);
        tvActivityVal = findViewById(R.id.tvActivityVal);
        tvSleepVal = findViewById(R.id.tvSleepVal);
        tvStressVal = findViewById(R.id.tvStressVal);

        dropdownDiet = findViewById(R.id.dropdownDiet);
        dropdownJunk = findViewById(R.id.dropdownJunk);

        cardScore = findViewById(R.id.cardScore);
        tvLiveScore = findViewById(R.id.tvLiveScore);
        tvScoreStatus = findViewById(R.id.tvScoreStatus);
        tvBMIResult = findViewById(R.id.tvBMIResult);

        btnCalculateBMI = findViewById(R.id.btnCalculateBMI);
        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnReset = findViewById(R.id.btnReset);

        if (dropdownSymptoms != null) {
            dropdownSymptoms.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allSymptoms));
        }

        String[] dietOptions = {"0 Servings", "1 Serving", "2+ Servings"};
        if (dropdownDiet != null) {
            dropdownDiet.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dietOptions));
        }

        String[] junkOptions = {"0-2 times/week", "3-4 times/week", "5+ times/week"};
        if (dropdownJunk != null) {
            dropdownJunk.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, junkOptions));
        }
    }

    private void setupListeners() {
        if (dropdownSymptoms != null) {
            dropdownSymptoms.setOnItemClickListener((parent, view, position, id) -> {
                addSymptomChip(allSymptoms[position]);
                dropdownSymptoms.setText("Select more symptoms", false);
                updateLiveScore();
                FirebaseRealtimeHelper.saveUserAction(this, "SYMPTOM_SELECTED", allSymptoms[position]);
            });
        }

        Slider.OnChangeListener sliderListener = (slider, value, fromUser) -> {
            if (slider.getId() == R.id.sliderWater) tvWaterVal.setText(String.format(Locale.getDefault(), "%.1f L", value));
            if (slider.getId() == R.id.sliderActivity) tvActivityVal.setText(String.format(Locale.getDefault(), "%d Min", (int) value));
            if (slider.getId() == R.id.sliderSleep) tvSleepVal.setText(String.format(Locale.getDefault(), "%.1f Hrs", value));
            if (slider.getId() == R.id.sliderStress) tvStressVal.setText(String.valueOf((int) value));
            if (!isRestoring) updateLiveScore();
        };

        if (sliderWater != null) sliderWater.addOnChangeListener(sliderListener);
        if (sliderActivity != null) sliderActivity.addOnChangeListener(sliderListener);
        if (sliderSleep != null) sliderSleep.addOnChangeListener(sliderListener);
        if (sliderStress != null) sliderStress.addOnChangeListener(sliderListener);

        if (dropdownDiet != null) dropdownDiet.setOnItemClickListener((p, v, pos, id) -> updateLiveScore());
        if (dropdownJunk != null) dropdownJunk.setOnItemClickListener((p, v, pos, id) -> updateLiveScore());

        if (btnCalculateBMI != null) btnCalculateBMI.setOnClickListener(v -> showBMIDialog());
        if (btnAnalyze != null) btnAnalyze.setOnClickListener(v -> performFinalAnalysis());
        if (btnReset != null) btnReset.setOnClickListener(v -> resetAssessment());
    }

    private void addSymptomChip(String symptomName) {
        if (chipGroupSelected == null) return;
        for (int i = 0; i < chipGroupSelected.getChildCount(); i++) {
            if (((Chip) chipGroupSelected.getChildAt(i)).getText().toString().equals(symptomName)) return;
        }

        chipGroupSelected.setVisibility(View.VISIBLE);
        Chip chip = new Chip(this);
        chip.setText(symptomName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {
            chipGroupSelected.removeView(chip);
            syncCheckBoxes();
            updateLiveScore();
            if (chipGroupSelected.getChildCount() == 0) chipGroupSelected.setVisibility(View.GONE);
        });
        chipGroupSelected.addView(chip);
        syncCheckBoxes();
    }

    private void syncCheckBoxes() {
        if (chipGroupSelected == null) return;
        Set<String> selected = new HashSet<>();
        for (int i = 0; i < chipGroupSelected.getChildCount(); i++) {
            selected.add(((Chip) chipGroupSelected.getChildAt(i)).getText().toString());
        }

        if (cbFatigue != null) cbFatigue.setChecked(selected.contains("Fatigue / Low Energy"));
        if (cbHeadache != null) cbHeadache.setChecked(selected.contains("Frequent Headaches"));
        if (cbSleepDist != null) cbSleepDist.setChecked(selected.contains("Sleep Disturbances"));
        if (cbAnxiety != null) cbAnxiety.setChecked(selected.contains("Anxiety / Stress"));
        if (cbDigestion != null) cbDigestion.setChecked(selected.contains("Poor Digestion"));
        if (cbBloating != null) cbBloating.setChecked(selected.contains("Bloating / Gas"));
        if (cbConstipation != null) cbConstipation.setChecked(selected.contains("Constipation"));
        if (cbReflux != null) cbReflux.setChecked(selected.contains("Acid Reflux"));
        if (cbJointPain != null) cbJointPain.setChecked(selected.contains("Joint Pain"));
        if (cbWeightGain != null) cbWeightGain.setChecked(selected.contains("Weight Gain"));
        if (cbIllness != null) cbIllness.setChecked(selected.contains("Frequent Illness"));
        if (cbFocus != null) cbFocus.setChecked(selected.contains("Lack of Focus"));
    }

    private void updateLiveScore() {
        int score = calculateWaterPoints() + calculateActivityPoints() + calculateSleepPoints() +
                     calculateDietPoints() + calculateJunkPoints() + calculateStressPoints() + calculateBmiPoints();

        animateScore(currentScore, score);
        currentScore = score;
        updateScoreStatus(score);

        autoSave();
    }

    private void autoSave() {
        if (prefManager == null) return;
        Set<String> symptoms = new HashSet<>();
        if (chipGroupSelected != null) {
            for (int i = 0; i < chipGroupSelected.getChildCount(); i++) {
                symptoms.add(((Chip) chipGroupSelected.getChildAt(i)).getText().toString());
            }
        }

        String dosha = getCalculatedDosha();
        
        String dietText = dropdownDiet != null ? dropdownDiet.getText().toString() : "";
        int dietVal = dietText.contains("2+") ? 2 : dietText.contains("1") ? 1 : 0;
        String junkText = dropdownJunk != null ? dropdownJunk.getText().toString() : "";
        int junkVal = junkText.contains("5+") ? 2 : junkText.contains("3-4") ? 1 : 0;
        int stress = sliderStress != null ? (int) sliderStress.getValue() : 1;

        prefManager.saveHealthAssessment(
                symptoms,
                sliderWater != null ? sliderWater.getValue() : 0,
                sliderActivity != null ? (int) sliderActivity.getValue() : 0,
                sliderSleep != null ? sliderSleep.getValue() : 0,
                dietVal, junkVal, stress, userBMI, currentScore, dosha
        );

        FirebaseRealtimeHelper.updateCurrentState(
                this, symptoms,
                sliderWater != null ? sliderWater.getValue() : 0,
                sliderActivity != null ? (int) sliderActivity.getValue() : 0,
                sliderSleep != null ? sliderSleep.getValue() : 0,
                dietVal, junkVal, stress, userBMI, currentScore, dosha
        );
    }

    private void restoreSavedState() {
        if (prefManager == null) return;
        isRestoring = true;
        
        Set<String> symptoms = prefManager.getSelectedSymptoms();
        if (chipGroupSelected != null) {
            chipGroupSelected.removeAllViews();
            for (String s : symptoms) if (!s.isEmpty()) addSymptomChip(s);
        }

        if (sliderWater != null) sliderWater.setValue(prefManager.getWaterIntake());
        if (sliderActivity != null) sliderActivity.setValue(prefManager.getActivity());
        if (sliderSleep != null) sliderSleep.setValue(prefManager.getSleep());
        if (sliderStress != null) sliderStress.setValue(prefManager.getStress());

        String[] dietOptions = {"0 Servings", "1 Serving", "2+ Servings"};
        int dIdx = prefManager.getDiet();
        if (dropdownDiet != null && dIdx >= 0 && dIdx < dietOptions.length) dropdownDiet.setText(dietOptions[dIdx], false);

        String[] junkOptions = {"0-2 times/week", "3-4 times/week", "5+ times/week"};
        int jIdx = prefManager.getJunk();
        if (dropdownJunk != null && jIdx >= 0 && jIdx < junkOptions.length) dropdownJunk.setText(junkOptions[jIdx], false);

        userBMI = prefManager.getBMI();
        if (tvBMIResult != null && userBMI != -1) tvBMIResult.setText(String.format(Locale.getDefault(), "BMI Status: %.1f", userBMI));

        currentScore = prefManager.getLastScore();
        if (tvLiveScore != null && currentScore != -1) {
            tvLiveScore.setText(String.valueOf(currentScore));
            updateScoreStatus(currentScore);
        }

        isRestoring = false;
    }

    private void resetAssessment() {
        FirebaseRealtimeHelper.saveUserAction(this, "ASSESSMENT_RESET", "User cleared health data");
        if (prefManager != null) prefManager.clearHealthData();
        recreate();
    }

    private String getCalculatedDosha() {
        int vata = 0, pitta = 0, kapha = 0;
        if (cbAnxiety != null && cbAnxiety.isChecked()) vata++;
        if (cbSleepDist != null && cbSleepDist.isChecked()) vata++;
        if (cbConstipation != null && cbConstipation.isChecked()) vata++;
        if (cbFatigue != null && cbFatigue.isChecked()) vata++;
        if (cbReflux != null && cbReflux.isChecked()) pitta++;
        if (cbHeadache != null && cbHeadache.isChecked()) pitta++;
        if (cbAnxiety != null && cbAnxiety.isChecked()) pitta++; 
        if (cbWeightGain != null && cbWeightGain.isChecked()) kapha++;
        if (cbDigestion != null && cbDigestion.isChecked()) kapha++;
        if (cbIllness != null && cbIllness.isChecked()) kapha++;

        if (pitta > vata && pitta >= kapha) return "Pitta";
        else if (kapha > vata && kapha > pitta) return "Kapha";
        else return "Vata";
    }

    private void animateScore(int from, int to) {
        if (tvLiveScore != null) tvLiveScore.setText(String.valueOf(to));
        if (cardScore != null) {
            cardScore.animate().scaleX(1.05f).scaleY(1.05f).setDuration(100).withEndAction(() -> 
                cardScore.animate().scaleX(1f).scaleY(1f).setDuration(100).start()).start();
        }
    }

    private void updateScoreStatus(int score) {
        if (tvScoreStatus == null || cardScore == null) return;
        if (score >= 85) {
            tvScoreStatus.setText("Excellent ✨");
            cardScore.setCardBackgroundColor(getResources().getColor(R.color.primary));
        } else if (score >= 60) {
            tvScoreStatus.setText("Good 👍");
            cardScore.setCardBackgroundColor(getResources().getColor(R.color.primary_variant));
        } else if (score >= 40) {
            tvScoreStatus.setText("Fair 😐");
            cardScore.setCardBackgroundColor(getResources().getColor(R.color.secondary));
        } else {
            tvScoreStatus.setText("Needs Attention ⚠️");
            cardScore.setCardBackgroundColor(getResources().getColor(R.color.error));
        }
    }

    private void showBMIDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_bmi, null);
        android.widget.EditText etW = dialogView.findViewById(R.id.etWeight);
        android.widget.EditText etH = dialogView.findViewById(R.id.etHeight);
        new AlertDialog.Builder(this).setTitle("BMI Calculator").setView(dialogView)
            .setPositiveButton("Calculate", (d, w) -> {
                try {
                    float weight = Float.parseFloat(etW.getText().toString());
                    float height = Float.parseFloat(etH.getText().toString()) / 100;
                    userBMI = weight / (height * height);
                    if (tvBMIResult != null) tvBMIResult.setText(String.format(Locale.getDefault(), "BMI Status: %.1f", userBMI));
                    updateLiveScore();
                    FirebaseRealtimeHelper.saveUserAction(this, "BMI_CALCULATED", "BMI: " + userBMI);
                } catch (Exception ignored) {}
            }).setNegativeButton("Cancel", null).show();
    }

    private void performFinalAnalysis() {
        if (btnAnalyze != null) btnAnalyze.setEnabled(false);
        
        FirebaseRealtimeHelper.saveUserAction(this, "FINAL_ANALYSIS_CLICKED", "Score: " + currentScore);
        
        HealthScore scoreData = new HealthScore(
                currentScore,
                calculateWaterPoints(),
                calculateActivityPoints(),
                calculateSleepPoints(),
                calculateDietPoints(),
                calculateJunkPoints(),
                calculateStressPoints(),
                calculateBmiPoints()
        );
        
        scoreData.setWater_intake(sliderWater != null ? sliderWater.getValue() : 0);
        scoreData.setActivity_min(sliderActivity != null ? (int) sliderActivity.getValue() : 0);
        scoreData.setSleep_hrs(sliderSleep != null ? sliderSleep.getValue() : 0);
        
        String dietText = dropdownDiet != null ? dropdownDiet.getText().toString() : "";
        scoreData.setDiet_servings(dietText.contains("2+") ? 2 : dietText.contains("1") ? 1 : 0);
        
        String junkText = dropdownJunk != null ? dropdownJunk.getText().toString() : "";
        scoreData.setJunk_freq(junkText.contains("5+") ? 2 : junkText.contains("3-4") ? 1 : 0);
        
        scoreData.setStress_level(sliderStress != null ? (int) sliderStress.getValue() : 1);
        scoreData.setBmi(userBMI);
        String dosha = getCalculatedDosha();
        scoreData.setDominant_dosha(dosha);

        Set<String> symptoms = new HashSet<>();
        if (chipGroupSelected != null) {
            for (int i = 0; i < chipGroupSelected.getChildCount(); i++) {
                symptoms.add(((Chip) chipGroupSelected.getChildAt(i)).getText().toString());
            }
        }
        scoreData.setSymptoms_list(new ArrayList<>(symptoms));

        repository.saveScore(FirebaseRealtimeHelper.getUserIdentifier(this), scoreData, new ApiCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Log.d(TAG, "History saved successfully");
                navigateToResult(dosha);
            }
            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Sync failed: " + errorMessage);
                navigateToResult(dosha);
            }
        });
    }

    private void navigateToResult(String dosha) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("HEALTH_SCORE", currentScore);
        intent.putExtra("DOMINANT_DOSHA", dosha);
        startActivity(intent);
        finish();
    }

    private int calculateWaterPoints() {
        float water = sliderWater != null ? sliderWater.getValue() : 0;
        if (water >= 2 && water <= 4) return 20;
        if (water >= 1 && water < 2) return 12;
        if (water > 4) return 5;
        return 2;
    }

    private int calculateActivityPoints() {
        float activity = sliderActivity != null ? sliderActivity.getValue() : 0;
        if (activity >= 30 && activity <= 60) return 20;
        if (activity >= 10 && activity < 30) return 12;
        return 4;
    }

    private int calculateSleepPoints() {
        float sleep = sliderSleep != null ? sliderSleep.getValue() : 0;
        if (sleep >= 6 && sleep <= 9) return 15;
        if (sleep > 9) return 8;
        return 2;
    }

    private int calculateDietPoints() {
        String diet = dropdownDiet != null ? dropdownDiet.getText().toString() : "";
        if (diet.contains("2+")) return 15;
        if (diet.contains("1")) return 8;
        return 2;
    }

    private int calculateJunkPoints() {
        String junk = dropdownJunk != null ? dropdownJunk.getText().toString() : "";
        if (junk.contains("0-2")) return 10;
        if (junk.contains("3-4")) return 5;
        return 0;
    }

    private int calculateStressPoints() {
        int stress = sliderStress != null ? (int) sliderStress.getValue() : 1;
        return (10 - stress);
    }

    private int calculateBmiPoints() {
        if (userBMI >= 18.5 && userBMI <= 24.9) return 10;
        if (userBMI >= 30) return 2;
        if (userBMI != -1) return 6;
        return 10;
    }
}
