package com.example.ainak.network;

import android.content.Context
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Class for retrofit instance creation.
 */
class ApiClient private constructor() {

    companion object {
        private var retrofit: Retrofit? = null

        private var apiService: ApiService? = null

        private const val base_url = "https://api.flickr.com/"

        private const val REQUEST_TIMEOUT = 60

        private var instance: ApiClient? = null

        operator fun invoke(): ApiClient {
            initialiseRetrofitInstance()
            return ApiClient()
        }

        fun initialize(context: Context?) {
            if (instance == null) {
                instance = invoke()
            }
        }

        /**
         * Method to instantiate retrofit instance.
         * @param context->Application Context
         */
        private fun initialiseRetrofitInstance() {
            if (retrofit == null) {
                val httpClient = OkHttpClient().newBuilder()
                    .connectTimeout(
                        REQUEST_TIMEOUT.toLong(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        REQUEST_TIMEOUT.toLong(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        REQUEST_TIMEOUT.toLong(),
                        TimeUnit.SECONDS
                    )

                httpClient.addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }

                retrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()

                apiService = retrofit!!.create(ApiService::class.java)
            }
        }

        /**
         * Method to get retrofit instance
         * @return->Retrofit instance
         */
        fun getRetrofitInstance(): Retrofit? {
            checkNotNull(retrofit) { "ApiClient not initialized, use initialize()" }
            return retrofit
        }

        fun getInstance(): ApiClient? {
            checkNotNull(instance) { "ApiClient not initialized, use initialize()" }
            return instance
        }
    }

    fun getAinakApi(): ApiService? {
        return ApiClient.apiService
    }

}