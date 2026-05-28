package com.example.ayurmitra.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "health_scores",
        primaryKeys = {"userId", "timestamp"})
public class HealthScoreEntity {

    @NonNull
    public String userId;
    
    public long timestamp;

    public int totalScore;
    public int waterScore;
    public int activityScore;
    public int sleepScore;
    public int nutritionScore;
    public int junkScore;
    public int stressScore;
    public int bmiScore;
    
    // Detailed fields
    public float water_intake;
    public int activity_min;
    public float sleep_hrs;
    public int diet_servings;
    public int junk_freq;
    public int stress_level;
    public float bmi;
    public String dominant_dosha;
    public String symptoms; // Comma separated list
}
