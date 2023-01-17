package com.masterluck.giffyviewer.repository

import androidx.lifecycle.LiveData
import com.masterluck.giffyviewer.data.database.GifDAO
import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.domain.GifAPI
import com.masterluck.giffyviewer.mapper.GifDtoToGifDAOMapper
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

    fun getGifs(query: String = "", offset: Int = 0): LiveData<List<GifData>> {
        if (query.isNullOrEmpty()) {
            gifAPI.getTrendingGifs(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val gifDataList = mutableListOf<GifData>()
                    for (gifDataDto in it.data) {
                        gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
                    }

                    gifDAO.insertGifs(gifDataList)
                }, {

                })
        } else {
            gifAPI.searchGifs(query, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val gifDataList = mutableListOf<GifData>()
                    for (gifDataDto in it.data) {
                        gifDataList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
                    }

                    gifDAO.insertGifs(gifDataList)
                }, {

                })
        }

        return gifDAO.getGifs(query, offset)
    }

    fun removeGif(gifData: GifData) {
        gifData.isRemoved = true
        gifDAO.update(gifData)
    }

    fun getGif(id: String): Single<GifData> {
        return gifDAO.getGif(id)
    }

}