package com.example.ayurmitra.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.ayurmitra.api.ApiCallback;
import com.example.ayurmitra.api.HealthScoreApi;
import com.example.ayurmitra.models.HealthScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HealthScoreRepository {
    private final HealthScoreApi api;
    private final HealthScoreDao dao;
    private final ExecutorService executor;
    private final Handler mainHandler;

    public HealthScoreRepository(Context context, HealthScoreApi api) {
        this.api = api;
        this.dao = AppDatabase.getDatabase(context).healthScoreDao();
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void saveScore(String userId, HealthScore score, ApiCallback<Void> callback) {
        executor.execute(() -> {
            // 1. Save to Room (Local) with rounded timestamp to prevent duplicates in same minute
            HealthScoreEntity entity = toEntity(userId, score);
            dao.insert(entity);
            
            // 2. Sync to Firebase (Remote)
            // Ensure the remote score has the same normalized timestamp
            score.setTimestamp(entity.timestamp);
            mainHandler.post(() -> api.saveHealthScore(userId, score, callback));
        });
    }

    public void getLatestScore(String userId, ApiCallback<HealthScore> callback) {
        executor.execute(() -> {
            HealthScoreEntity entity = dao.getLatestScore(userId);
            if (entity != null) {
                HealthScore localScore = fromEntity(entity);
                mainHandler.post(() -> callback.onSuccess(localScore));
            }

            mainHandler.post(() -> api.getLatestHealthScore(userId, new ApiCallback<HealthScore>() {
                @Override
                public void onSuccess(HealthScore result) {
                    if (result != null) {
                        executor.execute(() -> {
                            dao.insert(toEntity(userId, result));
                            if (entity == null || entity.timestamp != result.getTimestamp()) {
                                mainHandler.post(() -> callback.onSuccess(result));
                            }
                        });
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    if (entity == null) {
                        mainHandler.post(() -> callback.onError(errorMessage));
                    }
                }
            }));
        });
    }

    public void getScoreHistory(String userId, ApiCallback<List<HealthScore>> callback) {
        executor.execute(() -> {
            // 1. Return Local data immediately
            List<HealthScoreEntity> entities = dao.getAllScores(userId);
            List<HealthScore> localScores = new ArrayList<>();
            for (HealthScoreEntity entity : entities) {
                localScores.add(fromEntity(entity));
            }
            mainHandler.post(() -> callback.onSuccess(localScores));
            
            // 2. Sync from Firebase in background
            mainHandler.post(() -> api.getHealthScoreHistory(userId, new ApiCallback<List<HealthScore>>() {
                @Override
                public void onSuccess(List<HealthScore> result) {
                    if (result != null && !result.isEmpty()) {
                        executor.execute(() -> {
                            // Update local cache with remote data (Room handles deduplication via Unique Index)
                            for (HealthScore hs : result) {
                                dao.insert(toEntity(userId, hs));
                            }
                            
                            // Re-fetch deduplicated list from DB
                            List<HealthScoreEntity> updatedEntities = dao.getAllScores(userId);
                            List<HealthScore> deduplicatedScores = new ArrayList<>();
                            for (HealthScoreEntity entity : updatedEntities) {
                                deduplicatedScores.add(fromEntity(entity));
                            }
                            
                            // Refresh UI if the list has changed after normalization/deduplication
                            mainHandler.post(() -> callback.onSuccess(deduplicatedScores));
                        });
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    if (localScores.isEmpty()) {
                        mainHandler.post(() -> callback.onError(errorMessage));
                    }
                }
            }));
        });
    }

    private HealthScoreEntity toEntity(String userId, HealthScore score) {
        HealthScoreEntity entity = new HealthScoreEntity();
        entity.userId = userId;
        entity.totalScore = score.getTotalScore();
        entity.waterScore = score.getWaterScore();
        entity.activityScore = score.getActivityScore();
        entity.sleepScore = score.getSleepScore();
        entity.nutritionScore = score.getNutritionScore();
        entity.junkScore = score.getJunkScore();
        entity.stressScore = score.getStressScore();
        entity.bmiScore = score.getBmiScore();
        
        // Detailed fields
        entity.water_intake = score.getWater_intake();
        entity.activity_min = score.getActivity_min();
        entity.sleep_hrs = score.getSleep_hrs();
        entity.diet_servings = score.getDiet_servings();
        entity.junk_freq = score.getJunk_freq();
        entity.stress_level = score.getStress_level();
        entity.bmi = score.getBmi();
        entity.dominant_dosha = score.getDominant_dosha();
        
        if (score.getSymptoms_list() != null) {
            entity.symptoms = TextUtils.join(",", score.getSymptoms_list());
        }
        
        long ts = score.getTimestamp() != null ? score.getTimestamp() : System.currentTimeMillis();
        // Normalize timestamp to the start of the minute (60,000 ms)
        entity.timestamp = (ts / 60000) * 60000;

        return entity;
    }

    private HealthScore fromEntity(HealthScoreEntity entity) {
        HealthScore score = new HealthScore(
                entity.totalScore,
                entity.waterScore,
                entity.activityScore,
                entity.sleepScore,
                entity.nutritionScore,
                entity.junkScore,
                entity.stressScore,
                entity.bmiScore
        );
        score.setTimestamp(entity.timestamp);
        score.setWater_intake(entity.water_intake);
        score.setActivity_min(entity.activity_min);
        score.setSleep_hrs(entity.sleep_hrs);
        score.setDiet_servings(entity.diet_servings);
        score.setJunk_freq(entity.junk_freq);
        score.setStress_level(entity.stress_level);
        score.setBmi(entity.bmi);
        score.setDominant_dosha(entity.dominant_dosha);
        
        if (!TextUtils.isEmpty(entity.symptoms)) {
            score.setSymptoms_list(Arrays.asList(entity.symptoms.split(",")));
        }

        return score;
    }
}
