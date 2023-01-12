package com.masterluck.giffyviewer.repository

import com.masterluck.giffyviewer.domain.dto.ResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface GifAPI {

    @GET("v1/gifs/trending")
    fun getGifList() : Single<ResponseDto>

}