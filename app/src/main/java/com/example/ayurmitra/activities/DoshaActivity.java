package com.example.ayurmitra.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayurmitra.R;
import com.example.ayurmitra.adapters.DoshaAdapter;
import com.example.ayurmitra.models.Dosha;
import com.example.ayurmitra.utils.DataProvider;
import java.util.List;

public class DoshaActivity extends AppCompatActivity {

    private RecyclerView rvDoshas;
    private DoshaAdapter adapter;
    private List<Dosha> doshaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosha);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ayurvedic Doshas");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvDoshas = findViewById(R.id.rvDoshas);
        rvDoshas.setLayoutManager(new LinearLayoutManager(this));

        doshaList = DataProvider.getDoshas();

        adapter = new DoshaAdapter(this, doshaList);
        rvDoshas.setAdapter(adapter);
    }
}
