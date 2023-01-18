package com.masterluck.giffyviewer.ui.gif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.masterluck.giffyviewer.databinding.FragmentGifBinding
import com.masterluck.giffyviewer.ui.giflist.PageLoadingOrder
import com.masterluck.giffyviewer.ui.viewmodel.GifsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFragment : Fragment() {

    private lateinit var binding: FragmentGifBinding
    private val viewModel: GifsViewModel by activityViewModels()

    private var lastUserDragged = false
    private var firstUserDragged = false
    val adapter = GifAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vp.adapter = adapter
        binding.vp.setPageTransformer(MarginPageTransformer(200))
        setupBorderScroll(binding.vp)

        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList -> adapter.setupGifs(gifList) }

    }

    override fun onResume() {
        super.onResume()
        binding.vp.setCurrentItem(viewModel.selectedGifPosition, false)
    }

    /**
     * ViewPager can't detect when it is dragging on edge directly, so we use positionOffset == 0F(no offset on dragging == edge)
     * and lastUserDragged or firstUserDragged to trigger only when user drags it towards the edge
     */
    private fun setupBorderScroll(vp: ViewPager2) {

        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                lastUserDragged =
                    binding.vp.currentItem == adapter.itemCount - 1 && state == ViewPager2.SCROLL_STATE_DRAGGING
                firstUserDragged =
                    binding.vp.currentItem == 0 && state == ViewPager2.SCROLL_STATE_DRAGGING
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == adapter.itemCount - 1 && lastUserDragged && positionOffset == 0F) {
                    showOtherPage(PageLoadingOrder.NEXT)
                } else if (position == 0 && firstUserDragged && positionOffset == 0F) {
                    showOtherPage(PageLoadingOrder.PREVIOUS)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.selectedGifPosition = position
            }
        })

    }

    private fun showOtherPage(order: PageLoadingOrder) {

        if (order == PageLoadingOrder.PREVIOUS && viewModel.offset == 0)
            return

        viewModel.gifListLiveData.removeObservers(viewLifecycleOwner)
        viewModel.showOtherPage(order)
        viewModel.gifListLiveData.observe(viewLifecycleOwner) { gifList -> adapter.setupGifs(gifList) }

        if (order == PageLoadingOrder.NEXT)
            viewModel.selectedGifPosition = 0
        if (order == PageLoadingOrder.PREVIOUS)
            viewModel.selectedGifPosition = adapter.itemCount - 1
        firstUserDragged = false
        lastUserDragged = false

        binding.vp.setCurrentItem(viewModel.selectedGifPosition, false)

    }

}