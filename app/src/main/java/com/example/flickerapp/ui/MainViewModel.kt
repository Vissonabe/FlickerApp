package com.example.flickerapp.ui

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.flickerapp.R
import com.example.flickerapp.model.PhotoResult
import com.example.flickerapp.network.NetworkManager
import com.example.flickerapp.network.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MainViewModel @ViewModelInject constructor(val networkManager : NetworkManager) : ViewModel() {

    var currentQuery = ""
    var currentPage = 1
    var isLoading = false
    var isLastPage = false

    fun searchImage(query: String)  = liveData<Result<PhotoResult>>(Dispatchers.IO) {
        currentPage = 1
        currentQuery = query
        makeApiRequest(this, false)
    }

    fun searchImage()  = liveData<Result<PhotoResult>>(Dispatchers.IO) {
        currentPage++
        makeApiRequest(this, true)
    }

    private suspend fun makeApiRequest(liveDataScope: LiveDataScope<Result<PhotoResult>>, isPagination : Boolean) {
        setLoadingValue(true)
        if(!isPagination) {
            liveDataScope.emit(Result.Loading("Loading ..."))
        }
        try {
            if(!isPagination) {
                liveDataScope.emit(
                    Result.Success(
                        data = networkManager.searchForImagesAsync(
                            currentQuery,
                            currentPage
                        )
                    )
                )
            } else {
                liveDataScope.emit(
                    Result.PaginationSuccess(
                        data = networkManager.searchForImagesAsync(
                            currentQuery,
                            currentPage
                        )
                    )
                )
            }
        } catch (exception: Exception) {
            liveDataScope.emit(Result.Error(message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setLoadingValue(value : Boolean) {
        isLoading = value
    }

    fun updateIsLastPage(pages : Int) {
        isLastPage = currentPage >= pages
    }
}