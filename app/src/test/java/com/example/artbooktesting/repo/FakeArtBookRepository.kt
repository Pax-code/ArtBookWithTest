package com.example.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.artbooktesting.databases.api.ImageResponse
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.util.Resource

class FakeArtBookRepository: ArtBookRepositoryInterface {

    private val arts = mutableListOf<ArtModelRoom>()
    private val artsLiveData = MutableLiveData<List<ArtModelRoom>>(arts)

    override suspend fun insertArt(art: ArtModelRoom) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: ArtModelRoom) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<ArtModelRoom>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }

}