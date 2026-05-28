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
import com.example.ayurmitra.adapters.HerbAdapter;
import com.example.ayurmitra.models.Herb;
import com.example.ayurmitra.utils.DataProvider;
import com.example.ayurmitra.utils.FirebaseRealtimeHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class HerbActivity extends AppCompatActivity {

    private RecyclerView rvHerbs;
    private HerbAdapter adapter;
    private List<Herb> fullHerbList;
    private List<Herb> displayedList;
    
    private TextInputEditText etSearch;
    private TextInputLayout tilSearch;
    private LinearLayout layoutNoResults;
    private MaterialButton btnSearchWeb;
    private TextView tvNoResultsMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herb);

        fullHerbList = DataProvider.getHerbs();
        displayedList = new ArrayList<>(fullHerbList);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupSearch();
    }

    private void initViews() {
        rvHerbs = findViewById(R.id.rvHerbs);
        etSearch = findViewById(R.id.etSearchHerb);
        tilSearch = findViewById(R.id.tilSearchHerb);
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
            getSupportActionBar().setTitle("Natural Remedies");
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        rvHerbs.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HerbAdapter(this, displayedList);
        rvHerbs.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterHerbs(s.toString());
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

    private void filterHerbs(String query) {
        String cleanQuery = query.trim().toLowerCase();
        displayedList.clear();

        if (cleanQuery.isEmpty()) {
            displayedList.addAll(fullHerbList);
        } else {
            for (Herb herb : fullHerbList) {
                if (herb.getName().toLowerCase().contains(cleanQuery) || 
                    herb.getDescription().toLowerCase().contains(cleanQuery)) {
                    displayedList.add(herb);
                }
            }
            // ✅ Log Search to Firebase
            FirebaseRealtimeHelper.saveGenericInput(this, "herb_searches", cleanQuery);
        }

        updateUI(cleanQuery);
        adapter.notifyDataSetChanged();
    }

    private void updateUI(String query) {
        if (displayedList.isEmpty()) {
            rvHerbs.setVisibility(View.GONE);
            layoutNoResults.setVisibility(View.VISIBLE);
            if (!query.isEmpty()) {
                tvNoResultsMsg.setText("'" + query + "' not found in our remedies guide.");
                btnSearchWeb.setText("Search Google for '" + query + "'");
            }
        } else {
            rvHerbs.setVisibility(View.VISIBLE);
            layoutNoResults.setVisibility(View.GONE);
        }
    }

    private void performWebSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            query = "Ayurvedic herbs and remedies";
        }
        
        // ✅ Log Web Search action
        FirebaseRealtimeHelper.saveUserAction(this, "HERB_WEB_SEARCH", query);

        String webQuery = query + " ayurvedic herb benefits usage precautions";
        String url = "https://www.google.com/search?q=" + webQuery.replace(" ", "+");
        
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "Web Result: " + query);
        startActivity(intent);
    }
}
