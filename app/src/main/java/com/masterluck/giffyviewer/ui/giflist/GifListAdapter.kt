package com.masterluck.giffyviewer.ui.giflist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterluck.giffyviewer.data.dao.GifDAO
import com.masterluck.giffyviewer.databinding.ItemGifListBinding

class GifListAdapter(private var gifList: List<GifDAO>) : RecyclerView.Adapter<GifListAdapter.GifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        val binding = ItemGifListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(gifList[position].downsized)
            .override(480)
            .into(holder.binding.ivGif)

    }

    override fun getItemCount(): Int = gifList.size

    fun setupGifs(list: List<GifDAO>) {
        gifList = list
        notifyDataSetChanged()
    }

    class GifListViewHolder(val binding: ItemGifListBinding) : RecyclerView.ViewHolder(binding.root)

}