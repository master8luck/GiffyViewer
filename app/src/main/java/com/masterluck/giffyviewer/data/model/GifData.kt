package com.masterluck.giffyviewer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifData (

    @PrimaryKey
    val id: String,

    val original: String,
    val downsized: String,
    var isRemoved: Boolean = false,
)
