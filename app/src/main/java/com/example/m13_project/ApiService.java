package com.example.m13_project;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("images/on_offer")
    Call<List<Product>> getProducts();

    @GET("images/on_offer")
    Call<List<Product>> getProductsOnOffer(@Query("page") int page, @Query("size") int size);

    @GET("images/all_products")
    Call<List<Product>> getAllProducts();

}