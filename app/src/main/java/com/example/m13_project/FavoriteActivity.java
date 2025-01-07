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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteActivity";
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String FAVORITES_KEY = "favorite_products";
    private ProductAdapter productAdapter;
    private final List<Product> favoriteProducts = new ArrayList<>();
    private TextView textViewNoFavorites;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("Favorite Page");

        textViewNoFavorites = findViewById(R.id.textViewNoFavorites);

        try {
            // Initialize RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
            productAdapter = new ProductAdapter(this, favoriteProducts, product -> {
                if (!product.isLiked()) {
                    favoriteProducts.remove(product);
                    saveFavorites();
                    productAdapter.notifyDataSetChanged();
                    updateNoFavoritesMessage();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(productAdapter);

            // Load favorite products from SharedPreferences
            loadFavorites();
            updateNoFavoritesMessage();

            // Bottom navigation listeners
            Button navigationHome = findViewById(R.id.navigation_home);
            Button navigationSearch = findViewById(R.id.navigation_cart);
            Button navigationFavorite = findViewById(R.id.navigation_favorite);
            Button navigationSettings = findViewById(R.id.navigation_settings);

            navigationHome.setOnClickListener(v -> {
                Intent intent = new Intent(FavoriteActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            });

            navigationSearch.setOnClickListener(v -> {
                Intent intent = new Intent(FavoriteActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            });

            navigationFavorite.setOnClickListener(v -> {
            });

            navigationSettings.setOnClickListener(v -> {
                Intent intent = new Intent(FavoriteActivity.this, SettingsActivity.class);
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

    private void updateNoFavoritesMessage() {
        if (favoriteProducts.isEmpty()) {
            textViewNoFavorites.setVisibility(TextView.VISIBLE);
        } else {
            textViewNoFavorites.setVisibility(TextView.GONE);
        }
    }
}