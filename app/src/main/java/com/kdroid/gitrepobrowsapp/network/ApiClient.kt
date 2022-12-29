package com.kdroid.gitrepobrowsapp.network

import com.kdroid.gitrepobrowsapp.api.ApiService
import com.moczul.ok2curl.CurlInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object ApiClient {
    var request: ApiService? =null
        private set

    fun init() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        // setup timeout vale offset
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(CurlInterceptor(Timber::d)).connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

        request = retrofit.create(ApiService::class.java)
    }

}