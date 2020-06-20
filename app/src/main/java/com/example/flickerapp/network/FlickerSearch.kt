package com.example.flickerapp.network

import com.example.flickerapp.model.PhotoResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerSearch {

    @GET("?method=flickr.photos.search&api_key=062a6c0c49e4de1d78497d13a7dbb360&format=json&nojsoncallback=1")
    suspend fun searchPhoto(@Query("text") text : String,
                    @Query("per_page") perPage : Int,
                    @Query("page") page : Int) : PhotoResult
}