package com.example.m13_project;

import com.google.gson.annotations.SerializedName;
import android.util.Log;
import java.util.Objects;

public class Product {

    private int productId;
    private String name;
    private String description;
    private String price;
    private int stock;
    private int categoryId;
    private int imageId;
    private boolean offer;

    @SerializedName("image")
    private Image image;

    // Getter methods
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getImageId() {
        return imageId;
    }

    public boolean isOffer() {
        return offer;
    }

    public Image getImage() {
        return image;
    }

    // Return the image URL (null safe)
    public String getImageUrl() {
        if (image != null) {
            Log.d("Product", "Image object is not null: " + image.getUrl());
            if (image.getUrl() != null) {
                Log.d("Product", "Image URL: " + image.getUrl());
                return image.getUrl();
            } else {
                Log.d("Product", "Image URL is null");
            }
        } else {
            Log.d("Product", "Image object is null");
        }
        return image != null ? image.getUrl() : null;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId == product.productId &&
                categoryId == product.categoryId &&
                imageId == product.imageId &&
                offer == product.offer &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                price.equals(product.price) &&
                image.equals(product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, stock, categoryId, imageId, offer, image);
    }

    // Image inner class
    public static class Image {
        private int imageId;
        private String type;
        private String description;
        private String filename;
        private String url;

        public int getImageId() {
            return imageId;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getFilename() {
            return filename;
        }

        public String getUrl() {
            return url;
        }
    }
}
