package com.masterluck.giffyviewer.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masterluck.giffyviewer.data.model.GifData
import io.reactivex.rxjava3.core.Single

@Dao
interface GifDAO {

    @Query("SELECT * FROM GifData WHERE isRemoved=0 AND title LIKE '%' || :query || '%' LIMIT 20 OFFSET :offset")
    fun getGifs(query: String = "", offset: Int): List<GifData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGifs(gifs: List<GifData>)

    @Update
    fun update(gifData: GifData)

}