package com.example.m13_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<List<Product>> getOnOfferProducts() {
        return productRepository.getOnOfferProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
