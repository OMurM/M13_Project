package com.example.m13_project;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String productId;
    private Image image;
    private String description;
    private String name;
    private double price;
    private boolean offer;
    private int stock;
    private boolean isLiked;

    // Constructor for Parcel
    protected Product(Parcel in) {
        productId = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        description = in.readString();
        name = in.readString();
        price = in.readDouble();
        offer = in.readByte() != 0; // 0 = false, 1 = true
        stock = in.readInt();
        isLiked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId != null ? productId : "");
        dest.writeParcelable(image, flags);
        dest.writeString(description != null ? description : "");
        dest.writeString(name != null ? name : "");
        dest.writeDouble(price);
        dest.writeByte((byte) (offer ? 1 : 0));
        dest.writeInt(stock);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    // New Method: Get Image URL
    public String getImageUrl() {
        return image != null ? image.getUrl() : null;
    }
}
