package com.masterluck.giffyviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masterluck.giffyviewer.data.model.GifData

@Database(entities = [GifData::class], version = 1)
abstract class GiffyViewerDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDAO
}