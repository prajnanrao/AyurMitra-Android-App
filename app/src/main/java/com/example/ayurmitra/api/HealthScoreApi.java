package com.example.ayurmitra.api;

import com.example.ayurmitra.models.HealthScore;
import java.util.List;

public interface HealthScoreApi {
    void saveHealthScore(String userId, HealthScore healthScore, ApiCallback<Void> callback);
    void getLatestHealthScore(String userId, ApiCallback<HealthScore> callback);
    void getHealthScoreHistory(String userId, ApiCallback<List<HealthScore>> callback);
}
