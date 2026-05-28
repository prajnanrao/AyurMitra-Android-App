package com.example.ayurmitra.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class HealthAssessmentActivity extends AppCompatActivity {

    private static final String TAG = "HealthAssessmentActivity";
    private LinearLayout layoutSymptoms;
    private View dropdownHeader;
    
    private CheckBox cbFatigue, cbHeadache, cbSleepDist, cbAnxiety, cbDigestion,
            cbBloating, cbConstipation, cbReflux, cbJointPain, cbWeightGain,
            cbIllness, cbFocus;

    private Slider sliderWater, sliderActivity, sliderSleep, sliderStress;
    private TextView tvWaterVal, tvActivityVal, tvSleepVal, tvStressVal;
    
    private AutoCompleteTextView dropdownDiet, dropdownJunk;
    
    private TextView tvLiveScore, tvScoreStatus, tvBMIResult;
    private MaterialCardView cardScore;
    
    private MaterialButton btnCalculateBMI, btnAnalyze;

    private float userBMI = -1;
    private int currentScore = 0;
    private PreferenceManager prefManager;
    private HealthScoreRepository repository;
    private boolean isInitialLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_assessment);

        prefManager = new PreferenceManager(this);
        repository = new HealthScoreRepository(this, new FirebaseHealthScoreApi());
        
        initToolbar();
        initViews();
        setupListeners();
        
        loadSavedData();
        
        isInitialLoading = true;
        updateScore();
        isInitialLoading = false;
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
        dropdownHeader = findViewById(R.id.dropdownHeader);
        layoutSymptoms = findViewById(R.id.layoutSymptoms);

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

        String[] dietOptions = {"0 Servings", "1 Serving", "2+ Servings"};
        dropdownDiet.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dietOptions));

        String[] junkOptions = {"0-2 times/day", "3-4 times/day", "5+ times/day"};
        dropdownJunk.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, junkOptions));
    }

    private void loadSavedData() {
        userBMI = prefManager.getBMI();
        if (userBMI != -1) {
            updateBMIResultText();
        }

        sliderWater.setValue(prefManager.getWaterIntake());
        sliderActivity.setValue(prefManager.getActivity());
        sliderSleep.setValue(prefManager.getSleep());
        sliderStress.setValue(prefManager.getStress());

        tvWaterVal.setText(String.format(Locale.getDefault(), "%.1f L", sliderWater.getValue()));
        tvActivityVal.setText(String.format(Locale.getDefault(), "%d Min", (int) sliderActivity.getValue()));
        tvSleepVal.setText(String.format(Locale.getDefault(), "%.1f Hrs", sliderSleep.getValue()));
        tvStressVal.setText(String.valueOf((int) sliderStress.getValue()));

        Set<String> symptoms = prefManager.getSelectedSymptoms();
        if (cbFatigue != null) cbFatigue.setChecked(symptoms.contains("Fatigue"));
        if (cbHeadache != null) cbHeadache.setChecked(symptoms.contains("Headache"));
        if (cbSleepDist != null) cbSleepDist.setChecked(symptoms.contains("SleepDist"));
        if (cbAnxiety != null) cbAnxiety.setChecked(symptoms.contains("Anxiety"));
        if (cbDigestion != null) cbDigestion.setChecked(symptoms.contains("Digestion"));
        if (cbBloating != null) cbBloating.setChecked(symptoms.contains("Bloating"));
        if (cbConstipation != null) cbConstipation.setChecked(symptoms.contains("Constipation"));
        if (cbReflux != null) cbReflux.setChecked(symptoms.contains("Reflux"));
        if (cbJointPain != null) cbJointPain.setChecked(symptoms.contains("JointPain"));
        if (cbWeightGain != null) cbWeightGain.setChecked(symptoms.contains("WeightGain"));
        if (cbIllness != null) cbIllness.setChecked(symptoms.contains("Illness"));
        if (cbFocus != null) cbFocus.setChecked(symptoms.contains("Focus"));

        String diet = prefManager.getDiet() == 2 ? "2+ Servings" : prefManager.getDiet() == 1 ? "1 Serving" : "0 Servings";
        dropdownDiet.setText(diet, false);

        String junk = prefManager.getJunk() == 2 ? "5+ times/day" : prefManager.getJunk() == 1 ? "3-4 times/day" : "0-2 times/day";
        dropdownJunk.setText(junk, false);
    }

    private void setupListeners() {
        if (dropdownHeader != null && layoutSymptoms != null) {
            dropdownHeader.setOnClickListener(v -> {
                layoutSymptoms.setVisibility(layoutSymptoms.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });
        }

        sliderWater.addOnChangeListener((slider, value, fromUser) -> {
            tvWaterVal.setText(String.format(Locale.getDefault(), "%.1f L", value));
            updateScore();
        });

        sliderActivity.addOnChangeListener((slider, value, fromUser) -> {
            tvActivityVal.setText(String.format(Locale.getDefault(), "%d Min", (int) value));
            updateScore();
        });

        sliderSleep.addOnChangeListener((slider, value, fromUser) -> {
            tvSleepVal.setText(String.format(Locale.getDefault(), "%.1f Hrs", value));
            updateScore();
        });

        sliderStress.addOnChangeListener((slider, value, fromUser) -> {
            tvStressVal.setText(String.valueOf((int) value));
            updateScore();
        });

        dropdownDiet.setOnItemClickListener((parent, view, position, id) -> {
            updateScore();
            FirebaseRealtimeHelper.saveUserAction(this, "DIET_SELECTED", dropdownDiet.getText().toString());
        });
        
        dropdownJunk.setOnItemClickListener((parent, view, position, id) -> {
            updateScore();
            FirebaseRealtimeHelper.saveUserAction(this, "JUNK_SELECTED", dropdownJunk.getText().toString());
        });

        btnCalculateBMI.setOnClickListener(v -> showBMIDialog());
        
        if (cardScore != null) {
            cardScore.setOnClickListener(v -> {
                showScoreBreakdown();
                FirebaseRealtimeHelper.saveUserAction(this, "SCORE_BREAKDOWN_CLICKED", "Score: " + currentScore);
            });
        }

        btnAnalyze.setOnClickListener(v -> {
            btnAnalyze.setEnabled(false); // ✅ Debounce
            saveCurrentData();
            
            // ✅ COMMIT TO HISTORY
            HealthScore finalScore = new HealthScore(
                    currentScore,
                    calculateWaterPoints(),
                    calculateActivityPoints(),
                    calculateSleepPoints(),
                    calculateDietPoints(),
                    calculateJunkPoints(),
                    calculateStressPoints(),
                    calculateBmiPoints()
            );
            
            repository.saveScore(FirebaseRealtimeHelper.getUserIdentifier(this), finalScore, new ApiCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Log.d(TAG, "History saved to repository");
                }
                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG, "Error saving history: " + errorMessage);
                    btnAnalyze.setEnabled(true);
                }
            });

            // Legacy commit for detailed reports
            String dosha = calculateDosha();
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("score", currentScore);
            reportData.put("dosha", dosha);
            FirebaseRealtimeHelper.saveReport(this, "health_assessment_result", reportData);
            
            Set<String> symptoms = getSelectedSymptomsList();
            String dietText = dropdownDiet.getText().toString();
            int dietVal = dietText.contains("2+") ? 2 : dietText.contains("1") ? 1 : 0;
            String junkText = dropdownJunk.getText().toString();
            int junkVal = junkText.contains("5+") ? 2 : junkText.contains("3-4") ? 1 : 0;
            
            FirebaseRealtimeHelper.commitToHistory(this, symptoms, sliderWater.getValue(), (int) sliderActivity.getValue(),
                    sliderSleep.getValue(), dietVal, junkVal, (int) sliderStress.getValue(),
                    userBMI, currentScore, dosha);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("HEALTH_SCORE", currentScore);
            intent.putExtra("DOMINANT_DOSHA", dosha);
            startActivity(intent);
            finish();
        });
        
        View.OnClickListener checkboxListener = v -> {
            updateScore();
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                FirebaseRealtimeHelper.saveUserAction(this, "SYMPTOM_TOGGLE", cb.getText().toString() + ": " + cb.isChecked());
            }
        };
        CheckBox[] boxes = {cbFatigue, cbHeadache, cbSleepDist, cbAnxiety, cbDigestion, cbBloating, cbConstipation, cbReflux, cbJointPain, cbWeightGain, cbIllness, cbFocus};
        for (CheckBox cb : boxes) if (cb != null) cb.setOnClickListener(checkboxListener);
    }

    private void updateScore() {
        if (isInitialLoading && prefManager.getLastScore() == -1) {
            tvLiveScore.setText("0");
            tvScoreStatus.setText("Start your assessment! ✨");
            return;
        }

        int score = calculateWaterPoints() + calculateActivityPoints() + calculateSleepPoints() + 
                    calculateDietPoints() + calculateJunkPoints() + calculateStressPoints() + calculateBmiPoints();

        currentScore = score;
        tvLiveScore.setText(String.valueOf(currentScore));
        updateStatusLabel(currentScore);
        
        if (!isInitialLoading) {
            saveCurrentData();
        }
    }

    private Set<String> getSelectedSymptomsList() {
        Set<String> symptoms = new HashSet<>();
        if (cbFatigue.isChecked()) symptoms.add("Fatigue");
        if (cbHeadache.isChecked()) symptoms.add("Headache");
        if (cbSleepDist.isChecked()) symptoms.add("SleepDist");
        if (cbAnxiety.isChecked()) symptoms.add("Anxiety");
        if (cbDigestion.isChecked()) symptoms.add("Digestion");
        if (cbBloating.isChecked()) symptoms.add("Bloating");
        if (cbConstipation.isChecked()) symptoms.add("Constipation");
        if (cbReflux.isChecked()) symptoms.add("Reflux");
        if (cbJointPain.isChecked()) symptoms.add("JointPain");
        if (cbWeightGain.isChecked()) symptoms.add("WeightGain");
        if (cbIllness.isChecked()) symptoms.add("Illness");
        if (cbFocus.isChecked()) symptoms.add("Focus");
        return symptoms;
    }

    private void saveCurrentData() {
        Set<String> symptoms = getSelectedSymptomsList();

        String dietText = dropdownDiet.getText().toString();
        int dietVal = dietText.contains("2+") ? 2 : dietText.contains("1") ? 1 : 0;
        String junkText = dropdownJunk.getText().toString();
        int junkVal = junkText.contains("5+") ? 2 : junkText.contains("3-4") ? 1 : 0;

        prefManager.saveHealthAssessment(
                symptoms,
                sliderWater.getValue(),
                (int) sliderActivity.getValue(),
                sliderSleep.getValue(),
                dietVal,
                junkVal,
                (int) sliderStress.getValue(),
                userBMI,
                currentScore,
                calculateDosha()
        );

        // ✅ AUTO SYNC CURRENT DRAFT ONLY (Not history)
        FirebaseRealtimeHelper.updateCurrentState(
                this, symptoms, sliderWater.getValue(), (int) sliderActivity.getValue(),
                sliderSleep.getValue(), dietVal, junkVal, (int) sliderStress.getValue(),
                userBMI, currentScore, calculateDosha()
        );
    }

    private int calculateWaterPoints() {
        float water = sliderWater.getValue();
        if (water >= 2.0 && water <= 4.0) return 20;
        if (water >= 1.5) return 15;
        if (water >= 1.0) return 10;
        return 5;
    }

    private int calculateActivityPoints() {
        int mins = (int) sliderActivity.getValue();
        if (mins >= 45) return 20;
        if (mins >= 30) return 15;
        if (mins >= 15) return 10;
        return 5;
    }

    private int calculateSleepPoints() {
        float sleep = sliderSleep.getValue();
        if (sleep >= 7.0 && sleep <= 8.5) return 20;
        if (sleep >= 6.0 && sleep <= 9.5) return 15;
        if (sleep > 9.5) return 10;
        return 5;
    }

    private int calculateDietPoints() {
        String diet = dropdownDiet.getText().toString();
        if (diet.contains("2+")) return 15;
        if (diet.contains("1")) return 8;
        return 2;
    }

    private int calculateJunkPoints() {
        String junk = dropdownJunk.getText().toString();
        if (junk.contains("0-2")) return 10;
        if (junk.contains("3-4")) return 5;
        return 0;
    }

    private int calculateStressPoints() {
        int stress = (int) sliderStress.getValue();
        if (stress <= 2) return 10;
        if (stress <= 5) return 7;
        if (stress <= 7) return 4;
        return 1;
    }

    private int calculateBmiPoints() {
        if (userBMI >= 18.5 && userBMI <= 24.9) return 5;
        if (userBMI != -1) return 2;
        return 0;
    }

    private void updateStatusLabel(int score) {
        if (score >= 85) tvScoreStatus.setText("Excellent Health! ✨");
        else if (score >= 60) tvScoreStatus.setText("Good Progress! 💪");
        else if (score >= 40) tvScoreStatus.setText("Fair Wellness. 🌿");
        else tvScoreStatus.setText("Focus on health today! ❤️");
    }

    private String calculateDosha() {
        int vata = 0, pitta = 0, kapha = 0;
        if (cbFatigue.isChecked()) vata++;
        if (cbAnxiety.isChecked()) vata++;
        if (cbConstipation.isChecked()) vata++;
        if (cbSleepDist.isChecked()) vata++;
        if (cbHeadache.isChecked()) pitta++;
        if (cbReflux.isChecked()) pitta++;
        if (cbDigestion.isChecked()) pitta++;
        if (cbBloating.isChecked()) kapha++;
        if (cbJointPain.isChecked()) kapha++;
        if (cbWeightGain.isChecked()) kapha++;

        if (vata >= pitta && vata >= kapha) return "Vata";
        if (pitta >= vata && pitta >= kapha) return "Pitta";
        return "Kapha";
    }

    private void showBMIDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calculate BMI");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bmi, null);
        builder.setView(view);

        TextInputEditText etWeight = view.findViewById(R.id.etWeight);
        TextInputEditText etHeight = view.findViewById(R.id.etHeight);

        builder.setPositiveButton("Calculate", (dialog, which) -> {
            String wStr = etWeight.getText().toString().trim();
            String hStr = etHeight.getText().toString().trim();
            if (!wStr.isEmpty() && !hStr.isEmpty()) {
                try {
                    float weight = Float.parseFloat(wStr);
                    float heightCm = Float.parseFloat(hStr);
                    if (heightCm > 0) {
                        float heightM = heightCm / 100;
                        userBMI = weight / (heightM * heightM);
                        prefManager.saveBMI(userBMI);
                        updateBMIResultText();
                        updateScore();
                        FirebaseRealtimeHelper.saveUserAction(this, "BMI_CALCULATED", "BMI: " + userBMI);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Calculation error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateBMIResultText() {
        String category = getBMICategory(userBMI);
        tvBMIResult.setText(String.format(Locale.getDefault(), "BMI Status: %.1f (%s)", userBMI, category));
    }

    private String getBMICategory(float bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    private void showScoreBreakdown() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_score_breakdown, null);
        builder.setView(view);

        TextView tvWater = view.findViewById(R.id.tvWaterScore);
        TextView tvActivity = view.findViewById(R.id.tvActivityScore);
        TextView tvSleep = view.findViewById(R.id.tvSleepScore);
        TextView tvNutrition = view.findViewById(R.id.tvNutritionScore);
        TextView tvStress = view.findViewById(R.id.tvStressScore);
        TextView tvJunk = view.findViewById(R.id.tvJunkScore);
        TextView tvBmi = view.findViewById(R.id.tvBmiScore);

        LinearProgressIndicator pWater = view.findViewById(R.id.progressWater);
        LinearProgressIndicator pActivity = view.findViewById(R.id.progressActivity);
        LinearProgressIndicator pSleep = view.findViewById(R.id.progressSleep);
        LinearProgressIndicator pNutrition = view.findViewById(R.id.progressNutrition);
        LinearProgressIndicator pStress = view.findViewById(R.id.progressStress);
        LinearProgressIndicator pJunk = view.findViewById(R.id.progressJunk);
        LinearProgressIndicator pBmi = view.findViewById(R.id.progressBmi);

        int w = calculateWaterPoints();
        int a = calculateActivityPoints();
        int s = calculateSleepPoints();
        int n = calculateDietPoints();
        int st = calculateStressPoints();
        int j = calculateJunkPoints();
        int b = calculateBmiPoints();

        tvWater.setText(w + " / 20 pts"); pWater.setProgress(w);
        tvActivity.setText(a + " / 20 pts"); pActivity.setProgress(a);
        tvSleep.setText(s + " / 20 pts"); pSleep.setProgress(s);
        tvNutrition.setText(n + " / 15 pts"); pNutrition.setProgress(n);
        tvStress.setText(st + " / 10 pts"); pStress.setProgress(st);
        tvJunk.setText(j + " / 10 pts"); pJunk.setProgress(j);
        tvBmi.setText(b + " / 5 pts"); pBmi.setProgress(b);

        AlertDialog dialog = builder.create();
        view.findViewById(R.id.btnCloseBreakdown).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
