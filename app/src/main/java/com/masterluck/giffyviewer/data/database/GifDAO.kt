package com.masterluck.giffyviewer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masterluck.giffyviewer.data.model.GifData

@Dao
interface GifDAO {

    @Query("SELECT * FROM GifData")
    fun getGifs(): LiveData<List<GifData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGifs(gifs: List<GifData>)

}