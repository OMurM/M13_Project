package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationCart = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);

        navigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on Search page, no action needed
            }
        });

        navigationFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}