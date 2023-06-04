package com.example.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.example.artbooktesting.databases.api.API
import com.example.artbooktesting.databases.api.ImageResponse
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.databases.room.DAO
import com.example.artbooktesting.util.Resource
import javax.inject.Inject

class ArtBookRepository @Inject constructor(

    private val  dao: DAO,
    private val api: API

   ): ArtBookRepositoryInterface {
    override suspend fun insertArt(art: ArtModelRoom) {
        dao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtModelRoom) {
        dao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<ArtModelRoom>> {
        return dao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = api.imageSearch(imageString)

            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error!", null)
            }else{
                Resource.error("Error", null)
            }

        }catch (e: Exception){
            Resource.error("No data!",null)
        }
    }
}