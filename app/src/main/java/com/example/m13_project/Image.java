package com.example.m13_project;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String imageUrl;

    // Default constructor
    public Image() {
    }

    // Constructor for Parcelable
    protected Image(Parcel in) {
        imageUrl = in.readString();
    }

    // Write the object to the parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl != null ? imageUrl : "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelable creator
    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    // Getter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for imageUrl
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
