package com.example.artbooktesting.databases.api

import com.google.gson.annotations.SerializedName

data class ImageResult(


    val comments: Int,
    val downloads: Int,
    val favorites: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pakgeURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: String,
    val tags: String,
    val type: String,
    val user: String,

    @SerializedName("user_id")
    val userId: Int,
    val userImageURL: String,
    val views: Int,
    val webformatURL: String,
    val webformatHeight: Int,
    val webformatWidth: Int,


)
