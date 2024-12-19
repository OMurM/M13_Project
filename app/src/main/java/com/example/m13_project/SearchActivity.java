package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private List<Product> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search Products");

        Button navigationHome = findViewById(R.id.navigation_home);
        Button navigationSearch = findViewById(R.id.navigation_cart);
        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        Button navigationSettings = findViewById(R.id.navigation_settings);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        productAdapter = new ProductAdapter(this, product -> {
            // Handle product click here (if necessary)
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        // Fetch all products using ViewModel (or API call)
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, products -> {
            allProducts = products;
            productAdapter.submitList(allProducts); // Display all products initially
        });

        // Set up SearchView listener
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Bottom navigation listeners
        navigationHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        navigationSearch.setOnClickListener(v -> {
            // No action needed, already in SearchActivity
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
    }

    private void filterProducts(String query) {
        if (allProducts != null && !query.isEmpty()) {
            // Filter products based on the search query
            List<Product> filteredProducts = filterList(allProducts, query);
            productAdapter.submitList(filteredProducts);
        } else {
            // Show all products if query is empty
            productAdapter.submitList(allProducts);
        }
    }

    private List<Product> filterList(List<Product> products, String query) {
        // Filter the list of products based on the query
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}
