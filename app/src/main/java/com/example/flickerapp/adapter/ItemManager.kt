package com.example.flickerapp.adapter

import com.example.flickerapp.model.Loading
import com.example.flickerapp.model.Photo

class ItemManager {
    private val photosList = mutableListOf<ItemType>()

    fun changeDataSet(items : List<Photo>) {
        photosList.clear()
        photosList.addAll(items)
        addLoading()
    }

    fun addItemsToDataSet(items : List<Photo>) {
        removeLoadingItem()
        photosList.addAll(items)
        addLoading()
    }

    private fun addLoading() {
        photosList.add(Loading())
    }

    private fun removeLoadingItem() {
        photosList.removeAt(photosList.size-1)
    }

    fun getSize() : Int = photosList.size

    fun getItemAtPosition(position : Int): ItemType = photosList[position]
}