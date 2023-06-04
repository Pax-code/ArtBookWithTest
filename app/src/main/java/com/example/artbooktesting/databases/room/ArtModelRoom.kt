package com.example.artbooktesting.databases.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArtBookRoomTable")
data class ArtModelRoom(

    var artName: String,
    var artistName: String,
    var artDate: String,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


)

