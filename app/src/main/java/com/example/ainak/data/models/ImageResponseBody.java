package com.example.ainak.data.models;

import com.google.gson.annotations.SerializedName;

public class ImageResponseBody {

    @SerializedName("photos")
    private Photos photos;

    @SerializedName("stat")
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}
