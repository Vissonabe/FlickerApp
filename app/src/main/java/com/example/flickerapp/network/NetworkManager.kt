package com.example.flickerapp.network

import com.example.flickerapp.model.PhotoResult
import com.example.flickerapp.network.FlickerSearch
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkManager @Inject constructor (private val flickerService : FlickerSearch) {

    private val perPage = 20

    suspend fun searchForImagesAsync(query : String, page : Int): PhotoResult {
        return flickerService.searchPhoto(query, perPage, page)
    }
}