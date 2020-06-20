package com.example.flickerapp

object DependencyProvider {
    //can be replaced with Dagger
    val retrofit = RetrofitBuilder.flickerService
    val networkManager = NetworkManager(retrofit)
}