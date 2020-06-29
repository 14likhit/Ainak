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
        val imagesRequestBodyMap: MutableMap<String, String> = HashMap<String, String>()
        imagesRequestBodyMap["method"] = imagesRequestBody.method
        imagesRequestBodyMap["api_key"] = imagesRequestBody.apiKey
        imagesRequestBodyMap["text"] = imagesRequestBody.text
        imagesRequestBodyMap["format"] = imagesRequestBody.format
        imagesRequestBodyMap["nojsoncallback"] = imagesRequestBody.nojsoncallback.toString()
        imagesRequestBodyMap["per_page"] = imagesRequestBody.perPage.toString()
        imagesRequestBodyMap["page"] = imagesRequestBody.page.toString()
        return service.getImages(imagesRequestBodyMap)
    }

}