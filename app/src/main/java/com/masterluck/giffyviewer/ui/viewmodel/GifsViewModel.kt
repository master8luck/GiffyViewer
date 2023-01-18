package com.masterluck.giffyviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.utils.Utils
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.repository.GiffyViewerRepository
import com.masterluck.giffyviewer.ui.giflist.PageLoadingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class GifsViewModel @Inject constructor(
    private val repository: GiffyViewerRepository,
) : ViewModel() {

    var gifListLiveData = repository.getGifs()
        private set

    var offset = 0
        private set

    private var lastQuery = ""
    var selectedGifPosition = 0

    fun removeGif(gifData: GifData) {
        repository.removeGif(gifData)
    }

    fun showOtherPage(isNext: PageLoadingOrder, query: String = lastQuery) {
        if (lastQuery == query) {
            when (isNext) {
                PageLoadingOrder.PREVIOUS -> offset -= Utils.pageSize
                PageLoadingOrder.NEW -> offset = 0
                PageLoadingOrder.NEXT -> offset += Utils.pageSize
            }
            gifListLiveData = repository.getGifs(query, offset)
        } else {
            offset = 0
            gifListLiveData = repository.getGifs(query, offset)
        }
        lastQuery = query
    }


}