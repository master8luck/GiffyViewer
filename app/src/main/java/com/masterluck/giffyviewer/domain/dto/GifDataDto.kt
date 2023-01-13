package com.masterluck.giffyviewer.domain.dto

data class GifDataDto(
    val id: String,
    val title: String,
    val images: ImageDataVariantsDto,
)
