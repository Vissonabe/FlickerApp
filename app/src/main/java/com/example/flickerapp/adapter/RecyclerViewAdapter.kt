package com.example.flickerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.flickerapp.R
import com.example.flickerapp.model.Loading
import com.example.flickerapp.model.Photo

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_LOADING = 0
        private const val TYPE_ITEM = 1
    }

    private val itemManager = ItemManager()

    fun changeDataSet(items : List<Photo>) {
        itemManager.changeDataSet(items)
        notifyDataSetChanged()
    }

    fun addItemsToDataSet(items : List<Photo>) {
        itemManager.addItemsToDataSet(items)
        notifyDataSetChanged()
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                println("xxx TYPE_LOADING")
                val inflatedView = parent.inflate(R.layout.item_recycler_loading, false)
                 LoadingViewHolder(inflatedView)
            }
            TYPE_ITEM -> {
                val inflatedView = parent.inflate(R.layout.item_recycler_view, false)
                PhotoViewHolder(inflatedView)
            }
            else -> {
                val inflatedView = parent.inflate(R.layout.item_recycler_loading, false)
                LoadingViewHolder(inflatedView)
            }
        }
    }

    override fun getItemCount(): Int = itemManager.getSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemManager.getItemAtPosition(position)
        if(holder is PhotoViewHolder && item is Photo){
            holder.bindData(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemManager.getItemAtPosition(position) is Loading) TYPE_LOADING else TYPE_ITEM
    }
}