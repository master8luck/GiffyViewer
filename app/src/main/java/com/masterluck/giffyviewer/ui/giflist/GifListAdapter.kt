package com.masterluck.giffyviewer.ui.giflist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masterluck.giffyviewer.R
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.databinding.ItemGifListBinding
import com.masterluck.giffyviewer.utils.Utils

class GifListAdapter(
    var gifList: List<GifData>,
    private var onRemoveClicked: (gifData: GifData) -> Unit,
    private var onGifClicked: (position: Int) -> Unit,
) : RecyclerView.Adapter<GifListAdapter.GifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifListViewHolder {
        val binding = ItemGifListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifListViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(gifList[position].downsizedUrl)
            .placeholder(Utils.gifPlaceholder)
            .into(holder.binding.ivGif)

        holder.binding.ivClose.setOnClickListener {
            onRemoveClicked(gifList[position])
        }

        holder.binding.ivGif.setOnClickListener {
            onGifClicked(position)
        }

    }

    override fun getItemCount(): Int = gifList.size

    fun setupGifs(list: List<GifData>) {
        gifList = list
        notifyDataSetChanged()
    }

    class GifListViewHolder(val binding: ItemGifListBinding) : RecyclerView.ViewHolder(binding.root)

}