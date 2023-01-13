package com.masterluck.giffyviewer.ui.gif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.repository.GiffyViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class GifViewModel @Inject constructor(
    private val repository: GiffyViewerRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    init {
        repository.getGif(state.get<String>("id")!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { gifData ->
                mGifLiveData.postValue(gifData)
            }
    }

    private val mGifLiveData = MutableLiveData<GifData>()
    val gifLiveData = mGifLiveData as LiveData<GifData>


}