package com.example.ayurmitra.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FirebaseRealtimeHelper {

    private static final String TAG = "FirebaseHelper";
    private static final String DATABASE_URL = "https://ayurmitra-bda7b-default-rtdb.asia-southeast1.firebasedatabase.app/";

    /**
     * Sanitizes email to be used as a Firebase key by replacing illegal characters with "_".
     */
    public static String getSafeEmail(String email) {
        if (email == null) return "unknown";
        return email.replace(".", "_")
                    .replace("#", "_")
                    .replace("$", "_")
                    .replace("[", "_")
                    .replace("]", "_")
                    .replace("/", "_");
    }

    /**
     * Returns a unique identifier for the user (sanitized email or guest ID).
     */
    public static String getUserIdentifier(Context context) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && auth.getCurrentUser().getEmail() != null) {
            return getSafeEmail(auth.getCurrentUser().getEmail());
        }
        // Fallback to Android ID for guests/session persistence
        return "guest_" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static DatabaseReference getBaseRef(Context context) {
        String identifier = getUserIdentifier(context);
        DatabaseReference ref = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users").child(identifier);
        
        // Store real email as a field for clarity in console
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && auth.getCurrentUser().getEmail() != null) {
            ref.child("email").setValue(auth.getCurrentUser().getEmail());
        }
        
        return ref;
    }

    /**
     * Updates only the current state of the health assessment. 
     * Called frequently as user interacts with sliders/boxes.
     */
    public static void updateCurrentState(Context context, Set<String> symptoms, float water, int activity, float sleep,
                                          int diet, int junk, int stress, float bmi, int score, 
                                          String dosha) {
        DatabaseReference ref = getBaseRef(context);

        Map<String, Object> data = new HashMap<>();
        data.put("symptoms_list", new java.util.ArrayList<>(symptoms));
        data.put("water_intake", water);
        data.put("activity_min", activity);
        data.put("sleep_hrs", sleep);
        data.put("diet_servings", diet);
        data.put("junk_freq", junk);
        data.put("stress_level", stress);
        data.put("bmi", bmi);
        data.put("totalScore", score);
        data.put("dominant_dosha", dosha);
        data.put("timestamp", System.currentTimeMillis());

        ref.child("symptoms").child("current_state").setValue(data);
    }

    /**
     * Commits the assessment to history. 
     * Note: health_scores are now handled by HealthScoreRepository to avoid duplication.
     */
    public static void commitToHistory(Context context, Set<String> symptoms, float water, int activity, float sleep,
                                          int diet, int junk, int stress, float bmi, int score, 
                                          String dosha) {
        DatabaseReference ref = getBaseRef(context);

        Map<String, Object> data = new HashMap<>();
        data.put("symptoms_list", new java.util.ArrayList<>(symptoms));
        data.put("water_intake", water);
        data.put("activity_min", activity);
        data.put("sleep_hrs", sleep);
        data.put("diet_servings", diet);
        data.put("junk_freq", junk);
        data.put("stress_level", stress);
        data.put("bmi", bmi);
        data.put("totalScore", score);
        data.put("dominant_dosha", dosha);
        data.put("timestamp", System.currentTimeMillis());

        // Add to symptoms history only (detailed node)
        ref.child("symptoms").child("history").push().setValue(data)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Symptoms history updated"));
    }

    public static void saveBooking(Context context, String expertName, String date, String time, String notes) {
        DatabaseReference ref = getBaseRef(context);

        Map<String, Object> booking = new HashMap<>();
        booking.put("expert_name", expertName);
        booking.put("date", date);
        booking.put("time", time);
        booking.put("notes", notes);
        booking.put("timestamp", System.currentTimeMillis());

        ref.child("bookings").push().setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Booking saved");
                    if (context != null) Toast.makeText(context, "Booking Saved to Cloud", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Booking failed: " + e.getMessage());
                    if (context != null) Toast.makeText(context, "Booking Sync Failed", Toast.LENGTH_SHORT).show();
                });
        
        ref.child("inputs").child("draft_booking").removeValue();
    }

    public static void updateDraftBooking(Context context, String expertName, String date, String time, String notes) {
        DatabaseReference ref = getBaseRef(context);
        Map<String, Object> draft = new HashMap<>();
        draft.put("expert_name", expertName);
        draft.put("date", date);
        draft.put("time", time);
        draft.put("notes", notes);
        ref.child("inputs").child("draft_booking").setValue(draft);
    }

    public static void logMeditationSession(Context context, int durationMinutes, String sessionTitle) {
        DatabaseReference ref = getBaseRef(context);
        Map<String, Object> session = new HashMap<>();
        session.put("title", sessionTitle);
        session.put("duration_min", durationMinutes);
        session.put("timestamp", System.currentTimeMillis());

        ref.child("meditation_history").push().setValue(session)
                .addOnSuccessListener(aVoid -> {
                    if (context != null) Toast.makeText(context, "Meditation Logged", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    if (context != null) Toast.makeText(context, "Meditation Log Failed", Toast.LENGTH_SHORT).show();
                });
    }

    public static void saveReport(Context context, String reportType, Map<String, Object> data) {
        DatabaseReference ref = getBaseRef(context);
        data.put("timestamp", System.currentTimeMillis());
        ref.child("reports").child(reportType).push().setValue(data);
    }

    public static void saveGenericInput(Context context, String key, Object value) {
        DatabaseReference ref = getBaseRef(context);
        ref.child("inputs").child(key).setValue(value);
    }

    public static void saveUserAction(Context context, String action, String details) {
        DatabaseReference ref = getBaseRef(context);
        Map<String, Object> actionData = new HashMap<>();
        actionData.put("action", action);
        actionData.put("details", details);
        actionData.put("timestamp", System.currentTimeMillis());
        ref.child("user_actions").push().setValue(actionData);
    }
}
