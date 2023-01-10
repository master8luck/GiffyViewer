package com.masterluck.giffyviewer.data.mapper

import com.masterluck.giffyviewer.data.dao.GifDAO
import com.masterluck.giffyviewer.data.dto.GifDataDto

object GifDtoToGifDAOMapper {
    fun mapGifDTOToGifDAO(gifDto: GifDataDto): GifDAO {
        return GifDAO(
            gifDto.id,
            gifDto.images.original.url,
            gifDto.images.downsized.url,
        )
    }
}