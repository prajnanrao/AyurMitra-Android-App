package com.example.ayurmitra.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurmitra.R;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MeditationActivity extends AppCompatActivity {

    private TextInputLayout tilTime;
    private TextInputEditText etTime;
    private Button btnStart, btnPause, btnReset;
    private LinearLayout layoutTimerControls;
    private TextView tvCountdown;
    private ImageView ivMeditationIllustration;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 0;
    private int initialMinutes = 0;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initViews();
        setupToolbar();

        ivMeditationIllustration.setImageResource(R.drawable.medi_alaram);

        btnStart.setOnClickListener(v -> startInitialTimer());

        btnPause.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
                FirebaseRealtimeHelper.saveUserAction(this, "MEDITATION_PAUSED", "Minutes left: " + (timeLeftInMillis / 60000));
            } else {
                resumeTimer();
                FirebaseRealtimeHelper.saveUserAction(this, "MEDITATION_RESUMED", "Minutes remaining: " + (timeLeftInMillis / 60000));
            }
        });

        btnReset.setOnClickListener(v -> {
            FirebaseRealtimeHelper.saveUserAction(this, "MEDITATION_RESET", "Session cancelled");
            resetTimer();
        });
    }

    private void initViews() {
        tilTime = findViewById(R.id.tilTime);
        etTime = findViewById(R.id.etTime);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);
        layoutTimerControls = findViewById(R.id.layoutTimerControls);
        tvCountdown = findViewById(R.id.tvCountdown);
        ivMeditationIllustration = findViewById(R.id.ivMeditationIllustration);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Mindful Meditation");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void startInitialTimer() {
        String input = etTime.getText().toString().trim();
        if (input.isEmpty()) {
            tilTime.setError("Enter duration");
            return;
        }

        try {
            initialMinutes = Integer.parseInt(input);
            if (initialMinutes <= 0) {
                tilTime.setError("Enter valid duration");
                return;
            }
            timeLeftInMillis = (long) initialMinutes * 60000;
            
            // ✅ Log session start to Firebase
            FirebaseRealtimeHelper.saveUserAction(this, "MEDITATION_STARTED", "Duration: " + initialMinutes + " mins");
            
            startCountdown();
        } catch (Exception e) {
            tilTime.setError("Invalid input");
        }
    }

    private void startCountdown() {
        tilTime.setVisibility(View.GONE);
        tvCountdown.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.GONE);
        layoutTimerControls.setVisibility(View.VISIBLE);
        btnPause.setText("Pause");

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                onTimerFinished();
            }
        }.start();

        isTimerRunning = true;
    }

    private void pauseTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        isTimerRunning = false;
        btnPause.setText("Resume");
        btnStart.setVisibility(View.GONE);
        layoutTimerControls.setVisibility(View.VISIBLE);
    }

    private void resumeTimer() {
        startCountdown();
    }

    private void resetTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timeLeftInMillis = 0;
        updateCountDownText();
        isTimerRunning = false;

        btnStart.setVisibility(View.VISIBLE);
        layoutTimerControls.setVisibility(View.GONE);
        tvCountdown.setVisibility(View.GONE);
        tilTime.setVisibility(View.VISIBLE);
        btnStart.setText("Start Session");
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        tvCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    private void onTimerFinished() {
        playAlarm();
        vibrate();
        
        // ✅ Log completed meditation session to Firebase
        FirebaseRealtimeHelper.logMeditationSession(this, initialMinutes, "Mindful Meditation");
        FirebaseRealtimeHelper.saveUserAction(this, "MEDITATION_COMPLETED", "Duration: " + initialMinutes + " mins");

        showCompletionDialog();
    }

    private void playAlarm() {
        try {
            stopAlarm();
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null && v.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(1000);
            }
        }
    }

    private void showCompletionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Session Completed 🌿")
                .setMessage("You completed your meditation successfully!")
                .setPositiveButton("Finish", (dialog, which) -> {
                    stopAlarm();
                    resetTimer();
                })
                .setCancelable(false)
                .show();
    }

    private void stopAlarm() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
        stopAlarm();
    }
}
