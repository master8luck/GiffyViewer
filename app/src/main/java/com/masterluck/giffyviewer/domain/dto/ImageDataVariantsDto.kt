package com.masterluck.giffyviewer.domain.dto

data class ImageDataVariantsDto(
    val original: ImageDataDto,
    val downsized: ImageDataDto?,
)
