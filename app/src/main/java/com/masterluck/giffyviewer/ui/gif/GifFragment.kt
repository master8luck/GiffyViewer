package com.masterluck.giffyviewer.ui.gif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.masterluck.giffyviewer.databinding.FragmentGifBinding

class GifFragment  : Fragment() {

    private lateinit var binding: FragmentGifBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

}