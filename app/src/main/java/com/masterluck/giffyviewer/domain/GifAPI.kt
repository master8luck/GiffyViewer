package com.masterluck.giffyviewer.domain

import com.masterluck.giffyviewer.domain.dto.ResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GifAPI {

    @GET("v1/gifs/trending")
    fun getTrendingGifs(@Query("offset") offset: Int): Single<ResponseDto>

    @GET("v1/gifs/search")
    fun searchGifs(@Query("q") query: String, @Query("offset") offset: Int): Single<ResponseDto>

}