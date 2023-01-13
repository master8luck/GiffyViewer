package com.masterluck.giffyviewer.ui.giflist

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.masterluck.giffyviewer.Utils
import com.masterluck.giffyviewer.databinding.FragmentGifListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor

@AndroidEntryPoint
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

        val adapter = GifListAdapter(listOf(), viewModel::removeGif, this::onGifClicked)
        binding.rvGifList.adapter = adapter
        binding.rvGifList.layoutManager = GridLayoutManager(context, countColumnsNumber())

        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList ->
            adapter.setupGifs(gifList)
        }

    }

    private fun onGifClicked(id: String) {
        findNavController().navigate(
            GifListFragmentDirections.actionGifListFragmentToGifFragment(id)
        )
    }

    private fun countColumnsNumber(): Int {
        // Warning hardcoded gif item max width
        // Adapted for different screen sizes
        val display = requireActivity().windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        return floor(dpWidth / Utils.maxGifItemWidth).toInt()
    }

}