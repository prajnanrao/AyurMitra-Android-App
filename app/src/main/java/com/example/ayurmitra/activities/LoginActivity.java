package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ayurmitra.R;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.example.ayurmitra.utils.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginBtn;
    private TextView tvSignupLink;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.loginBtn);
        tvSignupLink = findViewById(R.id.tvSignupLink);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        loginBtn.setOnClickListener(v -> handleLogin());
        tvSignupLink.setOnClickListener(v -> {
            FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE_TO_SIGNUP", "User clicked signup link on login screen");
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim().toLowerCase();
        String pass = etPassword.getText().toString().trim();

        if (!validateInputs(email, pass)) {
            return;
        }

        showLoading(true);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    showLoading(false);
                    if (task.isSuccessful()) {
                        new PreferenceManager(this).setLoggedIn(true);
                        // ✅ Log Login Success
                        FirebaseRealtimeHelper.saveUserAction(this, "LOGIN_SUCCESS", "User: " + email);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Login Failed";
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                        // ✅ Log Login Failure
                        FirebaseRealtimeHelper.saveUserAction(this, "LOGIN_FAILURE", "Email: " + email + ", Error: " + error);
                    }
                });
    }

    private boolean validateInputs(String email, String pass) {
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        if (pass.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void showLoading(boolean isLoading) {
        if (progressBar != null) progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        loginBtn.setEnabled(!isLoading);
    }
}
