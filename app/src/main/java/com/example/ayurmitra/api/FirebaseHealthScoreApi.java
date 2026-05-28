package com.example.ayurmitra.api;

import android.util.Log;

import com.example.ayurmitra.models.HealthScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of HealthScoreApi using Firebase Realtime Database.
 * Migrated from Firestore to ensure all data is in the same regional instance.
 */
public class FirebaseHealthScoreApi implements HealthScoreApi {
    private static final String TAG = "FirebaseHealthScoreApi";
    private static final String DATABASE_URL = "https://ayurmitra-bda7b-default-rtdb.asia-southeast1.firebasedatabase.app/";
    
    private final DatabaseReference rootRef;

    public FirebaseHealthScoreApi() {
        this.rootRef = FirebaseDatabase.getInstance(DATABASE_URL).getReference();
    }

    @Override
    public void saveHealthScore(String userId, HealthScore healthScore, ApiCallback<Void> callback) {
        if (userId == null) {
            callback.onError("User ID is null");
            return;
        }

        DatabaseReference userScoresRef = rootRef.child("users").child(userId).child("health_scores");
        
        // Use normalized timestamp as key to prevent duplicates in history
        // This ensures that "SEE IN HISTORY SHOULD NOT REPEAT MORE THAN ONCE"
        long ts = healthScore.getTimestamp() != null ? healthScore.getTimestamp() : System.currentTimeMillis();
        String scoreId = String.valueOf(ts);
        
        userScoresRef.child(scoreId).setValue(healthScore)
                .addOnSuccessListener(aVoid -> callback.onSuccess(null))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error saving health score", e);
                    callback.onError(e.getMessage());
                });
    }

    @Override
    public void getLatestHealthScore(String userId, ApiCallback<HealthScore> callback) {
        if (userId == null) {
            callback.onError("User ID is null");
            return;
        }

        Query latestScoreQuery = rootRef.child("users").child(userId).child("health_scores")
                .orderByKey()
                .limitToLast(1);

        latestScoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HealthScore latest = null;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        latest = child.getValue(HealthScore.class);
                    }
                    callback.onSuccess(latest);
                } else {
                    callback.onSuccess(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error fetching latest health score", databaseError.toException());
                callback.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getHealthScoreHistory(String userId, ApiCallback<List<HealthScore>> callback) {
        if (userId == null) {
            callback.onError("User ID is null");
            return;
        }

        DatabaseReference userScoresRef = rootRef.child("users").child(userId).child("health_scores");
        userScoresRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HealthScore> scores = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    HealthScore score = child.getValue(HealthScore.class);
                    if (score != null) {
                        scores.add(score);
                    }
                }
                // Reverse to show latest first (keys are timestamps)
                Collections.reverse(scores);
                callback.onSuccess(scores);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error fetching health score history", databaseError.toException());
                callback.onError(databaseError.getMessage());
            }
        });
    }
}
