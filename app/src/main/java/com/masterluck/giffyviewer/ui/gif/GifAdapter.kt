package com.masterluck.giffyviewer.ui.gif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.masterluck.giffyviewer.R
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.databinding.ItemGifBinding

class GifAdapter(var gifList: List<GifData>) :
    RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(gifList[position].originalUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.ivGif)
    }

    override fun getItemCount(): Int = gifList.size

    fun setupGifs(list: List<GifData>) {
        gifList = list
        notifyDataSetChanged()
    }

    class GifViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

}