package com.example.m13_project;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRepository {

    private static final String TAG = "ProductRepository";
    private static final String BASE_URL = "http://10.0.2.2:5000/";
    private static final String PLACEHOLDER_IMAGE_URL = "https://via.placeholder.com/250";

    private final MutableLiveData<List<Product>> allProducts = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> onOfferProducts = new MutableLiveData<>();
    private final ApiService apiService;

    public ProductRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Product>> getAllProducts() {
        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();

                    // Assign placeholder image for products missing an image
                    for (Product product : products) {
                        if (product.getImage() == null || product.getImage().getImageUrl() == null) {
                            Log.w(TAG, "Product ID " + product.getProductId() + " is missing an image. Setting placeholder.");
                            Image placeholderImage = new Image();
                            placeholderImage.setImageUrl(PLACEHOLDER_IMAGE_URL);
                            product.setImage(placeholderImage);
                        }
                    }

                    allProducts.setValue(products);
                    Log.d(TAG, "Fetched all products: " + products.size());
                } else {
                    Log.e(TAG, "Failed to fetch all products: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching all products: " + t.getMessage(), t);
            }
        });

        return allProducts;
    }

    public LiveData<List<Product>> getOnOfferProducts() {
        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();

                    // Filter products to include only those on offer
                    List<Product> onOfferList = products.stream()
                            .filter(Product::isOffer)
                            .collect(Collectors.toList());

                    onOfferProducts.setValue(onOfferList);
                    Log.d(TAG, "Fetched on-offer products: " + onOfferList.size());
                } else {
                    Log.e(TAG, "Failed to fetch on-offer products: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching on-offer products: " + t.getMessage(), t);
            }
        });

        return onOfferProducts;
    }
}
