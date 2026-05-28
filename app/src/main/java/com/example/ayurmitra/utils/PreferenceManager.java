package com.example.ayurmitra.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import java.util.HashSet;
import java.util.Set;

public class PreferenceManager {
    private static final String PREF_NAME = "AyurMitraPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    
    // Health Assessment Data Keys
    private static final String KEY_SELECTED_SYMPTOMS = "selectedSymptoms";
    private static final String KEY_WATER_INTAKE = "waterIntake";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_SLEEP = "sleep";
    private static final String KEY_DIET = "diet";
    private static final String KEY_JUNK = "junk";
    private static final String KEY_STRESS = "stress";
    private static final String KEY_BMI = "bmi";
    private static final String KEY_LAST_SCORE = "lastScore";
    private static final String KEY_DOMINANT_DOSHA = "dominantDosha";
    private static final String KEY_TIMESTAMP = "lastUpdated";

    private SharedPreferences sharedPreferences;
    private Context context;

    public PreferenceManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getUserIdentifier() {
        return FirebaseRealtimeHelper.getUserIdentifier(context);
    }

    private String getKey(String baseKey) {
        return baseKey + "_" + getUserIdentifier();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void saveHealthAssessment(Set<String> symptoms, float water, int activity, float sleep,
                                   int diet, int junk, int stress, float bmi, int score, 
                                   String dosha) {
        sharedPreferences.edit()
                .putStringSet(getKey(KEY_SELECTED_SYMPTOMS), symptoms)
                .putFloat(getKey(KEY_WATER_INTAKE), water)
                .putInt(getKey(KEY_ACTIVITY), activity)
                .putFloat(getKey(KEY_SLEEP), sleep)
                .putInt(getKey(KEY_DIET), diet)
                .putInt(getKey(KEY_JUNK), junk)
                .putInt(getKey(KEY_STRESS), stress)
                .putFloat(getKey(KEY_BMI), bmi)
                .putInt(getKey(KEY_LAST_SCORE), score)
                .putString(getKey(KEY_DOMINANT_DOSHA), dosha)
                .putLong(getKey(KEY_TIMESTAMP), System.currentTimeMillis())
                .apply();
    }

    public void saveUserHealthData(int score, String dosha) {
        sharedPreferences.edit()
                .putInt(getKey(KEY_LAST_SCORE), score)
                .putString(getKey(KEY_DOMINANT_DOSHA), dosha)
                .putLong(getKey(KEY_TIMESTAMP), System.currentTimeMillis())
                .apply();
    }

    public void saveWaterIntake(float water) {
        sharedPreferences.edit().putFloat(getKey(KEY_WATER_INTAKE), water).apply();
    }

    public void saveActivity(int activity) {
        sharedPreferences.edit().putInt(getKey(KEY_ACTIVITY), activity).apply();
    }

    public void saveSleep(float sleep) {
        sharedPreferences.edit().putFloat(getKey(KEY_SLEEP), sleep).apply();
    }

    public void saveStress(int stress) {
        sharedPreferences.edit().putInt(getKey(KEY_STRESS), stress).apply();
    }

    public void saveLastScore(int score) {
        sharedPreferences.edit().putInt(getKey(KEY_LAST_SCORE), score).apply();
    }

    public void saveSelectedSymptoms(Set<String> symptoms) {
        sharedPreferences.edit().putStringSet(getKey(KEY_SELECTED_SYMPTOMS), symptoms).apply();
    }

    public void saveDiet(int diet) {
        sharedPreferences.edit().putInt(getKey(KEY_DIET), diet).apply();
    }

    public void saveJunk(int junk) {
        sharedPreferences.edit().putInt(getKey(KEY_JUNK), junk).apply();
    }

    public void saveBMI(float bmi) {
        sharedPreferences.edit().putFloat(getKey(KEY_BMI), bmi).apply();
    }

    public Set<String> getSelectedSymptoms() {
        return sharedPreferences.getStringSet(getKey(KEY_SELECTED_SYMPTOMS), new HashSet<>());
    }
    public float getWaterIntake() { return sharedPreferences.getFloat(getKey(KEY_WATER_INTAKE), 0.0f); }
    public int getActivity() { return sharedPreferences.getInt(getKey(KEY_ACTIVITY), 0); }
    public float getSleep() { return sharedPreferences.getFloat(getKey(KEY_SLEEP), 0.0f); }
    public int getDiet() { return sharedPreferences.getInt(getKey(KEY_DIET), 0); }
    public int getJunk() { return sharedPreferences.getInt(getKey(KEY_JUNK), 0); }
    public int getStress() { return sharedPreferences.getInt(getKey(KEY_STRESS), 1); }
    public float getBMI() { return sharedPreferences.getFloat(getKey(KEY_BMI), -1.0f); }
    public int getLastScore() { return sharedPreferences.getInt(getKey(KEY_LAST_SCORE), -1); }
    public String getDominantDosha() { return sharedPreferences.getString(getKey(KEY_DOMINANT_DOSHA), null); }
    public long getLastUpdated() { return sharedPreferences.getLong(getKey(KEY_TIMESTAMP), 0); }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public void clearHealthData() {
        String uid = getUserIdentifier();
        sharedPreferences.edit()
                .remove(KEY_SELECTED_SYMPTOMS + "_" + uid)
                .remove(KEY_WATER_INTAKE + "_" + uid)
                .remove(KEY_ACTIVITY + "_" + uid)
                .remove(KEY_SLEEP + "_" + uid)
                .remove(KEY_DIET + "_" + uid)
                .remove(KEY_JUNK + "_" + uid)
                .remove(KEY_STRESS + "_" + uid)
                .remove(KEY_BMI + "_" + uid)
                .remove(KEY_LAST_SCORE + "_" + uid)
                .remove(KEY_DOMINANT_DOSHA + "_" + uid)
                .remove(KEY_TIMESTAMP + "_" + uid)
                .apply();
    }
}
