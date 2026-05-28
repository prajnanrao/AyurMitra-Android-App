package com.example.ayurmitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurmitra.R;
import com.example.ayurmitra.adapters.ExpertsAdapter;
import com.example.ayurmitra.models.Expert;
import com.example.ayurmitra.utils.DataProvider;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ExpertsActivity extends AppCompatActivity {

    private RecyclerView rvExperts;
    private ExpertsAdapter adapter;
    private List<Expert> fullExpertList;
    private List<Expert> displayedList;
    
    private TextInputEditText etSearch;
    private TextInputLayout tilSearch;
    private LinearLayout layoutNoResults;
    private MaterialButton btnSearchWeb;
    private TextView tvNoResultsMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);

        fullExpertList = DataProvider.getExperts();
        displayedList = new ArrayList<>(fullExpertList);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupSearch();
    }

    private void initViews() {
        rvExperts = findViewById(R.id.rvExperts);
        etSearch = findViewById(R.id.etSearchExpert);
        tilSearch = findViewById(R.id.tilSearchExpert);
        layoutNoResults = findViewById(R.id.layoutNoResults);
        btnSearchWeb = findViewById(R.id.btnSearchWeb);
        tvNoResultsMsg = findViewById(R.id.tvNoResultsMsg);

        btnSearchWeb.setOnClickListener(v -> performWebSearch());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Consult Ayurveda Experts");
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        rvExperts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpertsAdapter(this, displayedList);
        rvExperts.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterExperts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performWebSearch();
                return true;
            }
            return false;
        });

        tilSearch.setEndIconOnClickListener(v -> performWebSearch());
    }

    private void filterExperts(String query) {
        String cleanQuery = query.trim().toLowerCase();
        displayedList.clear();

        if (cleanQuery.isEmpty()) {
            displayedList.addAll(fullExpertList);
        } else {
            for (Expert expert : fullExpertList) {
                if (expert.getName().toLowerCase().contains(cleanQuery) || 
                    expert.getSpecialization().toLowerCase().contains(cleanQuery)) {
                    displayedList.add(expert);
                }
            }
            // ✅ Log Search to Firebase
            FirebaseRealtimeHelper.saveGenericInput(this, "expert_searches", cleanQuery);
        }

        updateUI(cleanQuery);
        adapter.notifyDataSetChanged();
    }

    private void updateUI(String query) {
        if (displayedList.isEmpty()) {
            rvExperts.setVisibility(View.GONE);
            layoutNoResults.setVisibility(View.VISIBLE);
            if (!query.isEmpty()) {
                tvNoResultsMsg.setText("'" + query + "' not found in our expert directory.");
                btnSearchWeb.setText("Search Google for '" + query + "'");
            }
        } else {
            rvExperts.setVisibility(View.VISIBLE);
            layoutNoResults.setVisibility(View.GONE);
        }
    }

    private void performWebSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            query = "Best Ayurvedic Doctors and Experts";
        }
        
        // ✅ Log Web Search action
        FirebaseRealtimeHelper.saveUserAction(this, "EXPERT_WEB_SEARCH", query);

        String webQuery = query + " ayurveda expert consultant doctor";
        String url = "https://www.google.com/search?q=" + webQuery.replace(" ", "+");
        
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "Web Result: " + query);
        startActivity(intent);
    }
}
