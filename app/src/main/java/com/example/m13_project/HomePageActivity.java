package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("HomePageActivity", "onCreate called");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(this, this::showProductDetailsDialog);
        recyclerView.setAdapter(productAdapter);

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getOnOfferProducts().observe(this, products -> {
            if (products != null) {
                Log.d("HomePageActivity", "Received products: " + products.size());
                productAdapter.submitList(products);
            } else {
                Log.d("HomePageActivity", "No products received");
            }
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            Log.d("HomePageActivity", "Logout button clicked");
            Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Button navigationHome = findViewById(R.id.navigation_home);
        navigationHome.setOnClickListener(v -> {
            Log.d("HomePageActivity", "Navigation to Home clicked");
        });

        Button navigationCart = findViewById(R.id.navigation_cart);
        navigationCart.setOnClickListener(v -> {
            Log.d("HomePageActivity", "Navigation to Cart clicked");
            Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        Button navigationFavorite = findViewById(R.id.navigation_favorite);
        navigationFavorite.setOnClickListener(v -> {
            Log.d("HomePageActivity", "Navigation to Favorite clicked");
            Intent intent = new Intent(HomePageActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        Button navigationSettings = findViewById(R.id.navigation_settings);
        navigationSettings.setOnClickListener(v -> {
            Log.d("HomePageActivity", "Navigation to Settings clicked");
            Intent intent = new Intent(HomePageActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void showProductDetailsDialog(Product product) {
        Log.d("HomePageActivity", "Product clicked: " + product.getDescription());

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product_details, null);

        ImageView imageViewProduct = dialogView.findViewById(R.id.imageViewProductDetail);
        TextView textViewProductDescription = dialogView.findViewById(R.id.textViewProductDescription);

        textViewProductDescription.setText(product.getDescription());
        Picasso.get().load(product.getImageUrl()).into(imageViewProduct);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }
}
