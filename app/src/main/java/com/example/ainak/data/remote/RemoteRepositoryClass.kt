package com.example.ainak.data.remote

import com.example.ainak.data.models.ImagesRequestBody
import com.example.ainak.network.ApiService
import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Single

/**
 * Repository Class for making network calls
 */
class RemoteRepositoryClass(private val service: ApiService) : RemoteRepository {

    companion object {
        private const val TAG = "RemoteRepositoryClass"

        fun getInstance(apiService: ApiService): RemoteRepositoryClass? {
            if (mInstance == null) {
                mInstance =
                    RemoteRepositoryClass(apiService)
            }
            return mInstance
        }

        private var mInstance: RemoteRepositoryClass? = null
    }

    override fun getImages(imagesRequestBody: ImagesRequestBody): Single<JsonElement> {
        return service.getImages(imagesRequestBody)
    }

}