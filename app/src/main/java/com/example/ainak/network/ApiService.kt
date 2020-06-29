package com.example.ainak.network;

import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Service to implement all Api endpoints
 */
interface ApiService {

    @GET("services/rest/")
    fun getImages(@QueryMap imagesRequestBody: Map<String, String>): Single<JsonElement>

}