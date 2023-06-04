package com.example.artbooktesting.databases.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DAO {


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertArt(artModel: ArtModelRoom)

    @Delete
    suspend fun deleteArt(artModel: ArtModelRoom)

    @Query("SELECT * FROM ArtBookRoomTable")
    fun observeArts(): LiveData<List<ArtModelRoom>>


}