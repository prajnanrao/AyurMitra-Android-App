package com.example.ayurmitra.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface HealthScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HealthScoreEntity score);

    @Query("SELECT * FROM health_scores WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    HealthScoreEntity getLatestScore(String userId);

    @Query("SELECT * FROM health_scores WHERE userId = :userId ORDER BY timestamp DESC")
    List<HealthScoreEntity> getAllScores(String userId);
    
    @Query("DELETE FROM health_scores WHERE userId = :userId")
    void deleteByUserId(String userId);
}
