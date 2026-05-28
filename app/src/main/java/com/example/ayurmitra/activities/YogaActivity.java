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
import com.example.ayurmitra.adapters.YogaAdapter;
import com.example.ayurmitra.models.Yoga;
import com.example.ayurmitra.utils.DataProvider;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class YogaActivity extends AppCompatActivity {

    private RecyclerView rvYoga;
    private YogaAdapter adapter;
    private List<Yoga> fullYogaList;
    private List<Yoga> displayedList;
    private TextInputEditText etSearch;
    private TextInputLayout tilSearch;
    private LinearLayout layoutNoResults;
    private MaterialButton btnSearchWeb;
    private TextView tvNoResultsMsg;
    
    private String selectedCategory = "All";
    private String selectedDosha = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        fullYogaList = DataProvider.getYogaList();
        displayedList = new ArrayList<>(fullYogaList);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupFilters();
        setupSearch();
        
        applyFilters(); 
    }

    private void initViews() {
        rvYoga = findViewById(R.id.rvYoga);
        etSearch = findViewById(R.id.etSearchYoga);
        tilSearch = findViewById(R.id.tilSearchYoga);
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
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        rvYoga.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YogaAdapter(this, displayedList);
        rvYoga.setAdapter(adapter);
    }

    private void setupFilters() {
        ChipGroup chipGroupYoga = findViewById(R.id.chipGroupYoga);
        if (chipGroupYoga != null) {
            chipGroupYoga.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (checkedIds.isEmpty()) {
                    selectedCategory = "All";
                } else {
                    int checkedId = checkedIds.get(0);
                    if (checkedId == R.id.chipAll) selectedCategory = "All";
                    else if (checkedId == R.id.chipAsana) selectedCategory = "Asana";
                    else if (checkedId == R.id.chipPranayama) selectedCategory = "Pranayama";
                    else if (checkedId == R.id.chipMudra) selectedCategory = "Mudra";
                    else if (checkedId == R.id.chipOthers) selectedCategory = "Others";
                }
                // ✅ Log Filter Change
                FirebaseRealtimeHelper.saveUserAction(this, "YOGA_FILTER_CATEGORY", selectedCategory);
                applyFilters();
            });
        }

        ChipGroup chipGroupDosha = findViewById(R.id.chipGroupDosha);
        if (chipGroupDosha != null) {
            chipGroupDosha.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (checkedIds.isEmpty()) {
                    selectedDosha = "All";
                } else {
                    int checkedId = checkedIds.get(0);
                    if (checkedId == R.id.chipVata) selectedDosha = "Vata";
                    else if (checkedId == R.id.chipPitta) selectedDosha = "Pitta";
                    else if (checkedId == R.id.chipKapha) selectedDosha = "Kapha";
                }
                // ✅ Log Dosha Filter Change
                FirebaseRealtimeHelper.saveUserAction(this, "YOGA_FILTER_DOSHA", selectedDosha);
                applyFilters();
            });
        }
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
                if (!s.toString().trim().isEmpty()) {
                    FirebaseRealtimeHelper.saveGenericInput(YogaActivity.this, "yoga_searches", s.toString());
                }
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

    private void applyFilters() {
        String query = etSearch.getText().toString().trim().toLowerCase();
        displayedList.clear();
        
        for (Yoga yoga : fullYogaList) {
            boolean matchesQuery = query.isEmpty() || 
                    yoga.getName().toLowerCase().contains(query) || 
                    yoga.getCategory().toLowerCase().contains(query);
            
            boolean matchesCategory;
            if (selectedCategory.equals("All")) {
                matchesCategory = true;
            } else if (selectedCategory.equals("Others")) {
                matchesCategory = !yoga.getCategory().equalsIgnoreCase("Asana") && 
                                  !yoga.getCategory().equalsIgnoreCase("Pranayama") && 
                                  !yoga.getCategory().equalsIgnoreCase("Mudra");
            } else {
                matchesCategory = yoga.getCategory().equalsIgnoreCase(selectedCategory);
            }

            boolean matchesDosha;
            if (selectedDosha.equals("All")) {
                matchesDosha = true;
            } else {
                matchesDosha = yoga.getSuitableDosha().equalsIgnoreCase(selectedDosha) || 
                               yoga.getSuitableDosha().equalsIgnoreCase("All");
            }
            
            if (matchesQuery && matchesCategory && matchesDosha) {
                displayedList.add(yoga);
            }
        }
        
        updateEmptyState(query);
        adapter.notifyDataSetChanged();
    }

    private void updateEmptyState(String query) {
        if (displayedList.isEmpty()) {
            rvYoga.setVisibility(View.GONE);
            layoutNoResults.setVisibility(View.VISIBLE);
            if (!query.isEmpty()) {
                tvNoResultsMsg.setText("'" + query + "' not found in our local library.");
                btnSearchWeb.setText("Search Google for '" + query + "'");
            } else {
                tvNoResultsMsg.setText("No entries found matching filters.");
                btnSearchWeb.setText("Fetch Full Info from Google");
            }
        } else {
            rvYoga.setVisibility(View.VISIBLE);
            layoutNoResults.setVisibility(View.GONE);
        }
    }

    private void performWebSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            query = "Yoga exercises and benefits";
        }
        
        // ✅ Log Web Search
        FirebaseRealtimeHelper.saveUserAction(this, "YOGA_WEB_SEARCH", query);

        String webQuery = query + " yoga introduction steps benefits precautions";
        String url = "https://www.google.com/search?q=" + webQuery.replace(" ", "+");
        
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "Web Result: " + query);
        startActivity(intent);
    }
}
