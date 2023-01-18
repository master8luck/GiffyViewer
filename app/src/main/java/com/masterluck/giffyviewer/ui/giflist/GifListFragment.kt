package com.masterluck.giffyviewer.ui.giflist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.masterluck.giffyviewer.utils.Extensions.hideKeyboard
import com.masterluck.giffyviewer.utils.Utils
import com.masterluck.giffyviewer.databinding.FragmentGifListBinding
import com.masterluck.giffyviewer.ui.viewmodel.GifsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor

@AndroidEntryPoint
class GifListFragment : Fragment() {

    private lateinit var binding: FragmentGifListBinding
    private val viewModel: GifsViewModel by activityViewModels()

    private lateinit var adapter: GifListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            adapter =
                GifListAdapter(listOf(), viewModel::removeGif, this@GifListFragment::onGifClicked)
            rvGifList.adapter = adapter
            rvGifList.layoutManager = GridLayoutManager(context, countColumnsNumber())

            ivBack.setOnClickListener {
                showOtherPage(
                    PageLoadingOrder.PREVIOUS,
                    tilEt.text.toString()
                )
            }
            ivForward.setOnClickListener {
                showOtherPage(
                    PageLoadingOrder.NEXT,
                    tilEt.text.toString()
                )
            }
            til.setEndIconOnClickListener {
                showOtherPage(
                    PageLoadingOrder.NEW,
                    tilEt.text.toString()
                )
            }

            // Hiding keyboard when search field not in focus
            root.setOnClickListener { context?.hideKeyboard(it) }
            rvGifList.setOnTouchListener { view, _ ->
                context?.hideKeyboard(view)
                false
            }

        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList ->
            adapter.setupGifs(gifList)
        }
        binding.ivBack.isVisible = viewModel.offset > 0
        binding.rvGifList.scrollToPosition(viewModel.selectedGifPosition)
    }

    private fun showOtherPage(order: PageLoadingOrder, query: String) {
        context?.hideKeyboard(binding.tilEt)
        viewModel.gifListLiveData.removeObservers(viewLifecycleOwner)
        viewModel.showOtherPage(order, query)
        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList -> adapter.setupGifs(gifList) }
        binding.ivBack.isVisible = viewModel.offset > 0
    }


    private fun onGifClicked(position: Int) {
        viewModel.selectedGifPosition = position
        findNavController().navigate(
            GifListFragmentDirections.actionGifListFragmentToGifFragment()
        )
    }

    /**
    Warning hardcoded gif item max width
    Adapted for different screen sizes
     */
    private fun countColumnsNumber(): Int {
        val display = requireActivity().windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        return floor(dpWidth / Utils.maxGifItemWidth).toInt()
    }

}