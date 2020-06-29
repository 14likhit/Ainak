package com.example.ainak.data.models;

import com.google.gson.annotations.SerializedName;

public class ImagesRequestBody {

    public static final String FLICKR_METHOD = "flickr.photos.search";
    public static final String FLICKR_API_KEY = "062a6c0c49e4de1d78497d13a7dbb360";
    public static final String FLICKR_REQUEST_FORMAT = "json";
    public static final Integer FLICKR_NO_JSON_CALLBACK = 1;
    public static final Integer FLICKR_IMAGES_PER_PAGE = 10;

    @SerializedName("method")
    private String method;

    @SerializedName("api_key")
    private String apiKey;

    @SerializedName("text")
    private String text;

    @SerializedName("format")
    private String format;

    @SerializedName("nojsoncallback")
    private Integer nojsoncallback;

    @SerializedName("per_page")
    private Integer perPage;

    @SerializedName("page")
    private Integer page;

    public ImagesRequestBody(String searchQuery, Integer page) {
        this.method = FLICKR_METHOD;
        this.apiKey = FLICKR_API_KEY;
        this.text = searchQuery;
        this.format = FLICKR_REQUEST_FORMAT;
        this.nojsoncallback = FLICKR_NO_JSON_CALLBACK;
        this.perPage = FLICKR_IMAGES_PER_PAGE;
        this.page = page;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getNojsoncallback() {
        return nojsoncallback;
    }

    public void setNojsoncallback(Integer nojsoncallback) {
        this.nojsoncallback = nojsoncallback;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
