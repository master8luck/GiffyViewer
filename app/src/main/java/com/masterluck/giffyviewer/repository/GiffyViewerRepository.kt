package com.masterluck.giffyviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masterluck.giffyviewer.data.database.GifDAO
import com.masterluck.giffyviewer.data.database.GiffyViewerDatabase
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.domain.GifAPI
import com.masterluck.giffyviewer.domain.dto.ResponseDto
import com.masterluck.giffyviewer.domain.mapper.GifDtoToGifDAOMapper
import com.masterluck.giffyviewer.ui.viewmodel.GifsResponseState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiffyViewerRepository @Inject constructor(
    private val gifAPI: GifAPI,
    private val gifDAO: GifDAO,
    private val database: GiffyViewerDatabase,
) {

    private val mResponseState = MutableLiveData<GifsResponseState>()
    val responseState = mResponseState as LiveData<GifsResponseState>

    fun getGifs(query: String = "", offset: Int = 0) {
        mResponseState.postValue(GifsResponseState.LoadingResponse)
        if (query.isEmpty()) {
            getTrendingGifs(offset)
        } else {
            searchGifs(query, offset)
        }
    }

    private fun searchGifs(query: String, offset: Int) {
        gifAPI.searchGifs(query, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                postWebGifs(it)
            }, {
                postCachedGifs(query, offset, it)
            })
    }

    private fun getTrendingGifs(offset: Int) {
        gifAPI.getTrendingGifs(offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                postWebGifs(it)
            }, {
                postCachedGifs(offset = offset, error = it)
            })
    }

    private fun postWebGifs(it: ResponseDto) {
        val gifDataList = mutableListOf<GifData>()
        for (gifDataDto in it.data) {
            gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
        }
        mResponseState.postValue(GifsResponseState.WebResponse(gifDataList.toList()))

        Single.fromCallable { gifDAO.insertGifs(gifDataList) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun postCachedGifs(query: String = "", offset: Int, error: Throwable) {

        Single.fromCallable { gifDAO.getGifs(query, offset) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { gifs ->
                mResponseState.postValue(GifsResponseState.CacheResponse(gifs, error.message))
            }

    }

    //TODO fix this don't work
    fun removeGif(gifData: GifData) {
        gifData.isRemoved = true
        Single.fromCallable { gifDAO.update(gifData) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun clearDB() {
        database.clearAllTables()
    }

}