package com.masterluck.giffyviewer.data.dao

data class GifDAO (
    val id: String,
    val original: String,
    val downsized: String,
    var isRemoved: Boolean = false,
)
