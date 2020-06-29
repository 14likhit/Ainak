package com.example.ainak.network;

import com.example.ainak.data.models.ImagesRequestBody
import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET

/**
 * Service to implement all Api endpoints
 */
interface ApiService {

    @GET("services/rest/")
    fun getImages(@Body imagesRequestBody: ImagesRequestBody): Single<JsonElement>

}