package com.masterluck.giffyviewer.ui.viewmodel

import com.masterluck.giffyviewer.data.model.GifData

sealed class GifsResponseState(
    val gifs: List<GifData>,
) {

    class CacheResponse(
        gifs: List<GifData>,
        val errorMessage: String? = null,
    ) : GifsResponseState(gifs)

    class WebResponse(gifs: List<GifData>) : GifsResponseState(gifs)

    object LoadingResponse : GifsResponseState(emptyList())

}