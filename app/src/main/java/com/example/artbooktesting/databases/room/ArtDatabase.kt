package com.example.artbooktesting.databases.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtModelRoom::class], version = 1)
abstract class ArtDatabase: RoomDatabase() {

    abstract fun dao(): DAO

}