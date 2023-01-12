package com.masterluck.giffyviewer.ui.giflist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterluck.giffyviewer.R
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.databinding.ItemGifListBinding

class GifListAdapter(private var gifList: List<GifData>) : RecyclerView.Adapter<GifListAdapter.GifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        val binding = ItemGifListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(gifList[position].downsized)
            .override(480)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.ivGif)

    }

    override fun getItemCount(): Int = gifList.size

    fun setupGifs(list: List<GifData>) {
        gifList = list
        notifyDataSetChanged()
    }

    class GifListViewHolder(val binding: ItemGifListBinding) : RecyclerView.ViewHolder(binding.root)

}