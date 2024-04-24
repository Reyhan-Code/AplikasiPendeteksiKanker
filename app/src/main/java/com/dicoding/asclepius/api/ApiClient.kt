package com.dicoding.asclepius.api

import com.dicoding.asclepius.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val loggingInterceptor = HttpLoggingInterceptor()

    val interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.NEWS_API_KEY)
                .build()
        )
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.NEWS_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val getApiService: ApiService = retrofit.create(ApiService::class.java)

    const val API_KEY = BuildConfig.NEWS_API_KEY
}