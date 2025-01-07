package com.example.m13_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private TextView textViewName, textViewLastName, textViewEmail, textViewPhone;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textViewName = findViewById(R.id.textViewName);
        textViewLastName = findViewById(R.id.textViewLastName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("USER_EMAIL", null);
        if (userEmail != null) {
            fetchUserInfo(userEmail);
        } else {
            Log.e(TAG, "User email is not available");
        }

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationSearch = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        navigationHome.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SearchActivity.class);
            startActivity(intent);
            finish();
        });

        navigationFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, FavoriteActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSettings.setOnClickListener(v -> {
            // Already in Settings
        });

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchUserInfo(String email) {
        apiService.getUserInfo(email).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    textViewName.setText("Name: " + user.getFirstName());
                    textViewLastName.setText("Last Name: " + user.getLastName());
                    textViewEmail.setText("Email: " + user.getEmail());
                    textViewPhone.setText("Phone: " + user.getPhone());
                } else {
                    Log.e(TAG, "API response failed: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
            }
        });
    }
}