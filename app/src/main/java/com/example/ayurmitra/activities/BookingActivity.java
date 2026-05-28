package com.example.ayurmitra.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ayurmitra.R;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private String expertName;
    private String expertUrl;
    private String selectedDate = "";
    private String selectedTime = "";
    private MaterialButton btnDate, btnTime;
    private TextInputEditText etNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        expertName = getIntent().getStringExtra("expert_name");
        expertUrl = getIntent().getStringExtra("expert_url");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvBookingWith = findViewById(R.id.tvBookingWith);
        tvBookingWith.setText("Booking with " + (expertName != null ? expertName : "Expert"));

        btnDate = findViewById(R.id.btnDatePicker);
        btnTime = findViewById(R.id.btnTimePicker);
        etNotes = findViewById(R.id.etNotes);
        MaterialButton btnConfirm = findViewById(R.id.btnConfirmBooking);

        btnDate.setOnClickListener(v -> showDatePicker());
        btnTime.setOnClickListener(v -> showTimePicker());

        // ✅ AUTO SYNC NOTES AS USER TYPES
        etNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                syncDraftBooking();
            }
        });

        btnConfirm.setOnClickListener(v -> {
            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ FINAL SAVE ON CONFIRM
            FirebaseRealtimeHelper.saveBooking(this, expertName, selectedDate, selectedTime, etNotes.getText().toString());
            FirebaseRealtimeHelper.saveUserAction(this, "BOOKING_CONFIRMED", "Expert: " + expertName + ", Date: " + selectedDate);

            if (expertUrl != null && !expertUrl.isEmpty()) {
                Toast.makeText(this, "Redirecting to " + expertName + "'s booking portal...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", expertUrl);
                intent.putExtra("title", "Confirm with " + expertName);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        });
    }

    private void syncDraftBooking() {
        // Sync draft state to Firebase - Added 'this' context
        FirebaseRealtimeHelper.updateDraftBooking(this, expertName, selectedDate, selectedTime, etNotes.getText().toString());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            btnDate.setText(selectedDate);
            syncDraftBooking(); 
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            btnTime.setText(selectedTime);
            syncDraftBooking();
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
}
