package com.example.ainak.data.models;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    private String id;

    @SerializedName("owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private Integer farm;

    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private Integer ispublic;

    @SerializedName("isfriend")
    private Integer isfriend;

    @SerializedName("isfamily")
    private Integer isfamily;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public Integer getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public Integer getIsfriend() {
        return isfriend;
    }

    public Integer getIsfamily() {
        return isfamily;
    }

    public String getImageUrl() {
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg";
    }
}
