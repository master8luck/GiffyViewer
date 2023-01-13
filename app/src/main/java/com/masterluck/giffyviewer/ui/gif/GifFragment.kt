package com.masterluck.giffyviewer.ui.gif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.masterluck.giffyviewer.R
import com.masterluck.giffyviewer.databinding.FragmentGifBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFragment  : Fragment() {

    private lateinit var binding: FragmentGifBinding
    private val viewModel: GifViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gifLiveData.observe(viewLifecycleOwner) { gifData ->
            Glide.with(requireContext())
                .load(gifData.originalUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivGif)
        }
    }

}