package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationCart = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        navigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        navigationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}