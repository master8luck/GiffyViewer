package com.masterluck.giffyviewer.ui.giflist

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.masterluck.giffyviewer.Consts
import com.masterluck.giffyviewer.databinding.FragmentGifListBinding
import com.masterluck.giffyviewer.viewmodel.GifListViewModel
import kotlin.math.floor


class GifListFragment : Fragment() {

    private lateinit var binding: FragmentGifListBinding
    private val viewModel: GifListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GifListAdapter(listOf())
        binding.rvGifList.adapter = adapter


        // Warning hardcoded gif item max width
        // Adapted for different screen sizes
        val display = requireActivity().windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        val columns = floor(dpWidth / Consts.maxGifItemWidth).toInt()
        binding.rvGifList.layoutManager = GridLayoutManager(context, columns)

        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList ->
            adapter.setupGifs(gifList)
        }

    }

}