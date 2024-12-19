package com.example.m13_project;

import java.util.Objects;

public class Product {
    private int productId;
    private String description;
    private String imageUrl;

    public int getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                description.equals(product.description) &&
                imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, description, imageUrl);
    }
}