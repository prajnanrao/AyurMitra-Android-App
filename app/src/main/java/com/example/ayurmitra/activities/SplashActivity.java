package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ayurmitra.R;
import com.example.ayurmitra.utils.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView ivLogo = findViewById(R.id.ivLogo);
        TextView tvAppName = findViewById(R.id.tvAppName);

        if (ivLogo != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            ivLogo.startAnimation(fadeIn);
            if (tvAppName != null) tvAppName.startAnimation(fadeIn);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            PreferenceManager prefManager = new PreferenceManager(this);

            // CASE 1: App closed WITHOUT logout (Session Persistence)
            if (user != null && prefManager.isLoggedIn()) {
                // Restore same user data and go to Home
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            } else {
                // CASE 2: User logged out OR fresh start needed
                // Force a clean state
                if (user != null) {
                    mAuth.signOut();
                }
                prefManager.clearAll();
                prefManager.setLoggedIn(false);

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 2500);
    }
}
