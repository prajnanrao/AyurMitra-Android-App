package com.example.ayurmitra.models;

import java.io.Serializable;
import java.util.List;

public class HealthScore implements Serializable {
    private int totalScore;
    private int waterScore;
    private int activityScore;
    private int sleepScore;
    private int nutritionScore;
    private int junkScore;
    private int stressScore;
    private int bmiScore;
    
    // Additional detailed fields from assessment
    private float water_intake;
    private int activity_min;
    private float sleep_hrs;
    private int diet_servings;
    private int junk_freq;
    private int stress_level;
    private float bmi;
    private String dominant_dosha;
    private List<String> symptoms_list;

    // Use Long for Realtime Database compatibility
    private Long timestamp;

    public HealthScore() {
        // Required for Firebase
    }

    public HealthScore(int totalScore, int waterScore, int activityScore, int sleepScore, 
                       int nutritionScore, int junkScore, int stressScore, int bmiScore) {
        this.totalScore = totalScore;
        this.waterScore = waterScore;
        this.activityScore = activityScore;
        this.sleepScore = sleepScore;
        this.nutritionScore = nutritionScore;
        this.junkScore = junkScore;
        this.stressScore = stressScore;
        this.bmiScore = bmiScore;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public int getWaterScore() { return waterScore; }
    public void setWaterScore(int waterScore) { this.waterScore = waterScore; }

    public int getActivityScore() { return activityScore; }
    public void setActivityScore(int activityScore) { this.activityScore = activityScore; }

    public int getSleepScore() { return sleepScore; }
    public void setSleepScore(int sleepScore) { this.sleepScore = sleepScore; }

    public int getNutritionScore() { return nutritionScore; }
    public void setNutritionScore(int nutritionScore) { this.nutritionScore = nutritionScore; }

    public int getJunkScore() { return junkScore; }
    public void setJunkScore(int junkScore) { this.junkScore = junkScore; }

    public int getStressScore() { return stressScore; }
    public void setStressScore(int stressScore) { this.stressScore = stressScore; }

    public int getBmiScore() { return bmiScore; }
    public void setBmiScore(int bmiScore) { this.bmiScore = bmiScore; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public float getWater_intake() { return water_intake; }
    public void setWater_intake(float water_intake) { this.water_intake = water_intake; }

    public int getActivity_min() { return activity_min; }
    public void setActivity_min(int activity_min) { this.activity_min = activity_min; }

    public float getSleep_hrs() { return sleep_hrs; }
    public void setSleep_hrs(float sleep_hrs) { this.sleep_hrs = sleep_hrs; }

    public int getDiet_servings() { return diet_servings; }
    public void setDiet_servings(int diet_servings) { this.diet_servings = diet_servings; }

    public int getJunk_freq() { return junk_freq; }
    public void setJunk_freq(int junk_freq) { this.junk_freq = junk_freq; }

    public int getStress_level() { return stress_level; }
    public void setStress_level(int stress_level) { this.stress_level = stress_level; }

    public float getBmi() { return bmi; }
    public void setBmi(float bmi) { this.bmi = bmi; }

    public String getDominant_dosha() { return dominant_dosha; }
    public void setDominant_dosha(String dominant_dosha) { this.dominant_dosha = dominant_dosha; }

    public List<String> getSymptoms_list() { return symptoms_list; }
    public void setSymptoms_list(List<String> symptoms_list) { this.symptoms_list = symptoms_list; }
}
