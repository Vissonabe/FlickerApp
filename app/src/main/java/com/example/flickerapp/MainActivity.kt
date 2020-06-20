package com.example.flickerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickerapp.adapter.RecyclerViewAdapter
import com.example.flickerapp.databinding.ActivityMainBinding
import com.example.flickerapp.model.Photo
import com.example.flickerapp.model.PhotoResult
import com.example.flickerapp.network.Result
import com.example.flickerapp.paging.RecyclerViewPaginator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var uiService: ActivityUIService

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bindViewModel()
        uiService = ActivityUIService(baseContext, this, binding, viewModel)
    }

    private fun bindViewModel() {
        val networkManager = DependencyProvider.networkManager
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory { MainViewModel(networkManager) }
        ).get(MainViewModel::class.java)
    }
}