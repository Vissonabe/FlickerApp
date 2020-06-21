package com.example.flickerapp.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickerapp.adapter.RecyclerViewAdapter
import com.example.flickerapp.databinding.ActivityMainBinding
import com.example.flickerapp.model.PhotoResult
import com.example.flickerapp.network.Result
import com.example.flickerapp.paging.RecyclerViewPaginator
import com.example.flickerapp.searchDebounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ActivityUIService(val context : Context, val lifecycleOwner: LifecycleOwner, val binding : ActivityMainBinding, val viewModel : MainViewModel): CoroutineScope {

    lateinit var recyclerAdapter: RecyclerViewAdapter

    init {
        bindRecyclerView()
        bindEditText()
    }

    private fun bindEditText() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText

                launch(context = Dispatchers.Main) {
                    delay(searchDebounce)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    requestApiWithQuery(searchFor)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        })
    }

    private fun bindRecyclerView() {
        recyclerAdapter = RecyclerViewAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = recyclerAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerViewPaginator(layoutManager) {
            override val isLoading: Boolean
                get() = viewModel.isLoading
            override val isLastPage: Boolean
                get() = viewModel.isLastPage

            override fun loadMoreItems() {
                requestApiWithPage()
            }
        })
    }

    fun requestApiWithPage(){
        viewModel.searchImage().observe(lifecycleOwner,
            Observer { t : Result<PhotoResult> ->
                handleResult(t)
            })
    }

    fun requestApiWithQuery(query : String){
        if(query.isNotBlank()) {
            viewModel.searchImage(query)
                .observe(lifecycleOwner, Observer { t: Result<PhotoResult> ->
                    handleResult(t)
                })
        }
    }

    private fun handleResult(result : Result<PhotoResult>) {
        viewModel.setLoadingValue(false)
        binding.progressHorizontal.visibility = View.GONE
        when(result) {
            is Result.Loading -> {
//                binding.loadingView.text = result.message
//                binding.loadingView.visibility = View.VISIBLE
                binding.progressHorizontal.visibility = View.VISIBLE
            }
            is Result.Error -> {
                binding.loadingView.text = result.message
                binding.loadingView.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding.loadingView.visibility = View.GONE
                recyclerAdapter.changeDataSet(result.data.photos.photo)
            }
            is Result.PaginationSuccess -> {
                binding.loadingView.visibility = View.GONE
                recyclerAdapter.addItemsToDataSet(result.data.photos.photo)
                viewModel.updateIsLastPage(result.data.photos.pages)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}