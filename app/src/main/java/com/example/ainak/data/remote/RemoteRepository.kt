package com.example.ainak.data.remote

import com.example.ainak.data.models.ImagesRequestBody
import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Single

/**
 * Repository Service for making network calls
 */
interface RemoteRepository {

    fun getImages(imagesRequestBody: ImagesRequestBody): Single<JsonElement>

}