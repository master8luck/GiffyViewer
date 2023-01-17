package com.masterluck.giffyviewer.ui.giflist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.Utils
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.repository.GiffyViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val repository: GiffyViewerRepository,
) : ViewModel() {

    var gifListLiveData = repository.getGifs()
        private set

    var offset = 0
        private set

    private var lastQuery = ""

    fun removeGif(gifData: GifData) {
        repository.removeGif(gifData)
    }

    fun showOtherPage(isNext: PageLoadingOrder, query: String) {
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