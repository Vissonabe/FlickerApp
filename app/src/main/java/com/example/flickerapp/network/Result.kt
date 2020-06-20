package com.example.flickerapp.network

sealed class Result<out T : Any> {
    data class Loading(val message: String) : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class PaginationSuccess<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}