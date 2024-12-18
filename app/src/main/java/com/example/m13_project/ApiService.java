package com.example.m13_project;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiService {
    @GET("/images/on_offer")
    Call<List<Product>> getProducts();
}
