package com.masterluck.giffyviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.data.database.GiffyViewerDatabase
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.domain.mapper.GifDtoToGifDAOMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiffyViewerRepository @Inject constructor(
    private val gifAPI: GifAPI,
    private val database: GiffyViewerDatabase,
) {



    fun getGifs(): LiveData<List<GifData>> {
        gifAPI.getGifList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe ({
            val gifDataList = mutableListOf<GifData>()
            for (gifDataDto in it.data) {
                gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
            }

            database.gifDao().insertGifs(gifDataList)
        }, {

        })

        return database.gifDao().getGifs()
    }

}