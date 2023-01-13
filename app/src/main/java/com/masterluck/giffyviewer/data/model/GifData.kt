package com.masterluck.giffyviewer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifData (

    @PrimaryKey
    val id: String,

    val title: String,
    val originalUrl: String,
    val downsizedUrl: String,
    var isRemoved: Boolean = false,

    )
