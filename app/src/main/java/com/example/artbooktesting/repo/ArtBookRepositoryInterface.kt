package com.example.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.example.artbooktesting.databases.api.ImageResponse
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.util.Resource

interface ArtBookRepositoryInterface {


    suspend fun  insertArt(art: ArtModelRoom)

    suspend fun deleteArt(art: ArtModelRoom)

    fun getArt(): LiveData<List<ArtModelRoom>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}