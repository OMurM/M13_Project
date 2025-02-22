package com.example.m13_project;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("/images/on_offer")
    Call<List<Product>> getProducts();

    @GET("/images/all_products")
    Call<List<Product>> getAllProducts();

    @GET("/user/info")
    Call<UserResponse> getUserInfo(@Query("email") String email);

    @PUT("/users/{email}")
    Call<UserResponse> updateUser(@Path("email") String email, @Body UserRequest userRequest);
}