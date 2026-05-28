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

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button signupBtn;
    private TextView tvLoginLink;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        prefManager = new PreferenceManager(this);
        initViews();
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.signupBtn);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        signupBtn.setOnClickListener(v -> handleSignup());
        tvLoginLink.setOnClickListener(v -> {
            FirebaseRealtimeHelper.saveUserAction(this, "NAVIGATE_TO_LOGIN", "User clicked login link on signup screen");
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void handleSignup() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String pass = etPassword.getText().toString().trim();

        if (!validateInputs(name, email, pass)) {
            return;
        }

        showLoading(true);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    showLoading(false);
                    if (task.isSuccessful()) {
                        prefManager.clearAll();
                        prefManager.setLoggedIn(true);

                        // ✅ Log Signup to Firebase
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        FirebaseRealtimeHelper.saveGenericInput(this, "user_profile", userData);
                        FirebaseRealtimeHelper.saveUserAction(this, "SIGNUP_SUCCESS", "User signed up: " + email);

                        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Signup Failed";
                        Toast.makeText(SignupActivity.this, error, Toast.LENGTH_LONG).show();
                        FirebaseRealtimeHelper.saveUserAction(this, "SIGNUP_FAILURE", "Email: " + email + ", Error: " + error);
                    }
                });
    }

    private boolean validateInputs(String name, String email, String pass) {
        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }
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
        signupBtn.setEnabled(!isLoading);
    }
}
