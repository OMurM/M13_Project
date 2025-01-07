package com.example.m13_project;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String creationDate;
    private String description;
    private String filename;
    private int imageId;
    private String type;
    private String url;

    // Default constructor
    public Image() {
    }

    // Constructor for Parcel
    protected Image(Parcel in) {
        creationDate = in.readString();
        description = in.readString();
        filename = in.readString();
        imageId = in.readInt();
        type = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(creationDate);
        dest.writeString(description);
        dest.writeString(filename);
        dest.writeInt(imageId);
        dest.writeString(type);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    // Getters and Setters
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getImageUrl() {
        return url;
    }

    public void setImageUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
