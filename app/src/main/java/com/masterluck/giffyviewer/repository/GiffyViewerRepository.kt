package com.masterluck.giffyviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masterluck.giffyviewer.data.database.GifDAO
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.domain.GifAPI
import com.masterluck.giffyviewer.domain.mapper.GifDtoToGifDAOMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiffyViewerRepository @Inject constructor(
    private val gifAPI: GifAPI,
    private val gifDAO: GifDAO,
) {

    //TODO refactor code to post <List<GifData>> instead of returning LiveData<List<GifData>>
    private val mIsLoadingState = MutableLiveData<Boolean>()
    val isLoadingState = mIsLoadingState as LiveData<Boolean>

    fun getGifs(query: String = "", offset: Int = 0): LiveData<List<GifData>> {
        mIsLoadingState.postValue(true)
        if (query.isNullOrEmpty()) {
            getTrendingGifs(offset)
        } else {
            searchGifs(query, offset)
        }

        return gifDAO.getGifs(query, offset)
    }

    private fun searchGifs(query: String, offset: Int) {
        gifAPI.searchGifs(query, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                mIsLoadingState.postValue(false)
                val gifDataList = mutableListOf<GifData>()
                for (gifDataDto in it.data) {
                    gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
                }
                gifDAO.insertGifs(gifDataList)

            }, {
                mIsLoadingState.postValue(false)
            })
    }

    private fun getTrendingGifs(offset: Int) {
        gifAPI.getTrendingGifs(offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                mIsLoadingState.postValue(false)
                val gifDataList = mutableListOf<GifData>()
                for (gifDataDto in it.data) {
                    gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
                }
                gifDAO.insertGifs(gifDataList)

            }, {
                mIsLoadingState.postValue(false)
            })
    }

    fun removeGif(gifData: GifData) {
        gifData.isRemoved = true
        gifDAO.update(gifData)
    }

    fun getGif(id: String): Single<GifData> {
        return gifDAO.getGif(id)
    }

}