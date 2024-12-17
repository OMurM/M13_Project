package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private List<Product> allProducts = new ArrayList<>();
    private final List<Product> filteredProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationCart = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        productAdapter = new ProductAdapter(this, product -> {});

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        fetchProducts();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });

        navigationHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        navigationCart.setOnClickListener(v -> {});

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
    }

    private void fetchProducts() {
        ApiService apiService = ApiClient.getInstance().getApiService();
        apiService.getProducts().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProducts = response.body();
                    filteredProducts.clear();
                    filteredProducts.addAll(allProducts);
                    productAdapter.submitList(filteredProducts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e("SearchActivity", "API call failed", t);
            }
        });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        for (Product product : allProducts) {
            if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        productAdapter.submitList(filteredProducts);
    }
}
