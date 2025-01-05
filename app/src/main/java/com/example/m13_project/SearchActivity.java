package com.example.m13_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private final List<Product> allProducts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search Products");

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationSearch = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        productAdapter = new ProductAdapter(this, allProducts, product -> {
            // Handle product click
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        // Fetch all products using ViewModel (or API call)
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, products -> {
            if (products != null) {
                allProducts.clear();
                allProducts.addAll(products);
                productAdapter.notifyDataSetChanged();
            }
        });

        // Bottom navigation listeners
        navigationHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSearch.setOnClickListener(v -> {
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

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private List<Product> filterList(List<Product> products, String query) {
        return products.stream()
                .filter(product -> product.getDescription().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}