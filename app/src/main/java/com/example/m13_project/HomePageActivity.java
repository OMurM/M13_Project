package com.example.m13_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private static final String TAG = "HomePageActivity";
    private ProductAdapter productAdapter;
    private final List<Product> allProducts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home Page");

        try {
            // UI Elements
            TextView textViewWelcome = findViewById(R.id.textViewWelcome);
            Button buttonLogout = findViewById(R.id.buttonLogout);
            Button navigationHome = findViewById(R.id.navigation_home);
            Button navigationSearch = findViewById(R.id.navigation_cart);
            Button navigationFavorite = findViewById(R.id.navigation_favorite);
            Button navigationSettings = findViewById(R.id.navigation_settings);

            // Initialize RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            productAdapter = new ProductAdapter(this, allProducts, product -> {
                // Add logic for product click if needed
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(productAdapter);

            // Fetch all products using ViewModel
            ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
            productViewModel.getAllProducts().observe(this, products -> {
                if (products != null) {
                    allProducts.clear();
                    allProducts.addAll(products);
                    productAdapter.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "No products available.");
                }
            });

            // Bottom navigation listeners
            navigationHome.setOnClickListener(v -> {
                // Home navigation - Already on this screen
            });

            navigationSearch.setOnClickListener(v -> {
                Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            });

            navigationFavorite.setOnClickListener(v -> {
                Intent intent = new Intent(HomePageActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            });

            navigationSettings.setOnClickListener(v -> {
                Intent intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            });

            // Logout button listener
            buttonLogout.setOnClickListener(v -> {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: ", e);
        }
    }
}
