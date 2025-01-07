package com.example.m13_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePageActivity extends AppCompatActivity {

    private static final String TAG = "HomePageActivity";
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String FAVORITES_KEY = "favorite_products";
    private ProductAdapter productAdapter;
    private final List<Product> allProducts = new ArrayList<>();
    private final Set<Product> favoriteProducts = new HashSet<>();

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
                if (favoriteProducts.contains(product)) {
                    favoriteProducts.remove(product);
                    product.setLiked(false);
                } else {
                    favoriteProducts.add(product);
                    product.setLiked(true);
                }
                saveFavorites();
                productAdapter.notifyDataSetChanged();
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

            // Load favorite products from SharedPreferences
            loadFavorites();

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
                intent.putParcelableArrayListExtra("favoriteProducts", new ArrayList<>(favoriteProducts));
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

    private void saveFavorites() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new ArrayList<>(favoriteProducts));
        editor.putString(FAVORITES_KEY, json);
        editor.apply();
    }

    private void loadFavorites() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FAVORITES_KEY, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        List<Product> favorites = gson.fromJson(json, type);
        if (favorites != null) {
            favoriteProducts.addAll(favorites);
        }
    }
}