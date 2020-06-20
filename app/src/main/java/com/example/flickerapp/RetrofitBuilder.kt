package com.example.flickerapp

import com.example.flickerapp.network.FlickerSearch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level

object RetrofitBuilder {

    val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/services/rest/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val flickerService = retrofit.create(FlickerSearch::class.java)
}