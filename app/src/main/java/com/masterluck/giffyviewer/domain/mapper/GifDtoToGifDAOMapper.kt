package com.masterluck.giffyviewer.domain.mapper

import com.masterluck.giffyviewer.data.model.GifData
import com.masterluck.giffyviewer.domain.dto.GifDataDto

object GifDtoToGifDAOMapper {
    fun mapGifDTOToGifDAO(gifDto: GifDataDto): GifData {
        return GifData(
            gifDto.id,
            gifDto.title,
            gifDto.images.original.url,
            gifDto.images.downsized?.url ?: gifDto.images.original.url,
        )
    }
}