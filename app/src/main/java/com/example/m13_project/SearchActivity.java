package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    // RecyclerView and adapter
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set the title
        setTitle("Search Products");

        // Bottom navigation buttons
        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationSearch = findViewById(R.id.navigation_cart); // Reuse this button for search
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewProducts);
        productAdapter = new ProductAdapter(this, product -> {});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        // Navigation button listeners
        navigationHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSearch.setOnClickListener(v -> {
            // This is already the search view, so nothing to do here
        });

        navigationFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, FavoriteActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSettings.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

        // Here you could eventually fetch and display products
    }
}
