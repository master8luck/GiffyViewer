package com.masterluck.giffyviewer.ui.giflist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.repository.GiffyViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val repository: GiffyViewerRepository,
) : ViewModel() {

    private val mGifListLiveData = repository.getGifs()
    val gifListLiveData = mGifListLiveData

    fun removeGif(gifData: GifData) {
        repository.removeGif(gifData)
    }


}