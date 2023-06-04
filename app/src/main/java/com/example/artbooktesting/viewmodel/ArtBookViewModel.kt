package com.example.artbooktesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artbooktesting.databases.api.ImageResponse
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.repo.ArtBookRepositoryInterface
import com.example.artbooktesting.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtBookViewModel @Inject constructor(

private val repository: ArtBookRepositoryInterface

): ViewModel() {

    //FeedFragment
    val artlist = repository.getArt()

    //SearchFragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val slectedImageUrl : LiveData<String>
        get() = selectedImage

    //AddArtFragment
    private var  insertArtMessage = MutableLiveData<Resource<ArtModelRoom>>()
    val insertArtMsg: LiveData<Resource<ArtModelRoom>>
        get() = insertArtMessage


    fun resetInsertArtMsg(){
        insertArtMessage = MutableLiveData<Resource<ArtModelRoom>>()
    }

    fun setSelectedImage(url: String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: ArtModelRoom) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt(art: ArtModelRoom) = viewModelScope.launch{
        repository.insertArt(art)
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

    fun makeArt(artName: String, artistName: String, artDate: String){
        if (artName.isEmpty() || artistName.isEmpty() || artDate.isEmpty()){
            insertArtMessage.postValue(Resource.error("Enter values", null))
            return
        }

        val art = ArtModelRoom(artName,artistName,artDate,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMessage.postValue(Resource.success(art))
    }
}