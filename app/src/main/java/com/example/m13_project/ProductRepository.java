package com.example.m13_project;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ProductRepository {

    private final MutableLiveData<List<Product>> onOfferProducts = new MutableLiveData<>();
    private final ApiService apiService;

    private static final String TAG = "ProductRepository";

    public ProductRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")  // Replace with actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<Product>> getOnOfferProducts() {

        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();

                    for (Product product : products) {
                        Log.d(TAG, "Product ID: " + product.getProductId() + ", Image URL: " + product.getImageUrl());
                    }

                    onOfferProducts.setValue(products);
                } else {
                    Log.e(TAG, "API response failed: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
            }
        });

        return onOfferProducts;
    }
}
