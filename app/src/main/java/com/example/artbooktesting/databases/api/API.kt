package com.example.artbooktesting.databases.api

import com.example.artbooktesting.util.Util.api_key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/api/")
    suspend fun imageSearch(

        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = api_key

    ): Response<ImageResponse>

}