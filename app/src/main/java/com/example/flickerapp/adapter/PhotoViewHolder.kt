package com.example.flickerapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickerapp.R
import com.example.flickerapp.databinding.ItemRecyclerViewBinding
import com.example.flickerapp.model.Photo

class PhotoViewHolder(private val v : View) : RecyclerView.ViewHolder(v) {

    private fun constructImageUrl(farm : Int, server : String, id :String, secret : String) : String {
        return "https://farm$farm.staticflickr.com/$server/${id}_${secret}_m.jpg"
    }

    private val binding : ItemRecyclerViewBinding = ItemRecyclerViewBinding.bind(v)

    fun bindData(photo : Photo) {
        Glide.with(v)
            .load(constructImageUrl(photo.farm, photo.server, photo.id, photo.secret))
            .placeholder(R.drawable.image_placeholder)
            .centerCrop()
            .into(binding.imageView)

        binding.textView.text = photo.title
    }
}