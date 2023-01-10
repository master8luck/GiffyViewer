package com.masterluck.giffyviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masterluck.giffyviewer.data.dao.GifDAO
import com.masterluck.giffyviewer.data.mapper.GifDtoToGifDAOMapper
import com.masterluck.giffyviewer.repository.GifAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GifListViewModel : ViewModel() {

    private val mGifListLiveData = MutableLiveData<List<GifDAO>>()
    val gifListLiveData = mGifListLiveData as LiveData<List<GifDAO>>

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("api_key", "6CAcUkXwYgR2HvA04WZF1dCA6hZ6O67r")
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }
                    .build()
            )
            .build()

        retrofit.create(GifAPI::class.java).getGifList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                val gifDAOList = mutableListOf<GifDAO>()
                for (gifDataDto in it.data) {
                    gifDAOList.add(GifDtoToGifDAOMapper.mapGifDTOToGifDAO(gifDataDto))
                }
                mGifListLiveData.postValue(gifDAOList)
            }, {

            })
    }

}