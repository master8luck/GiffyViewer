package com.masterluck.giffyviewer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
        repository.getGifs()
    }

    val responseState = repository.responseState

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
            repository.getGifs(query, offset)
        } else {
            offset = 0
            repository.getGifs(query, offset)
        }
        lastQuery = query
    }


}