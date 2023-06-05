package com.example.artbooktesting.roomdb

import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.artbooktesting.databases.room.ArtDatabase
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.databases.room.DAO
import com.example.artbooktesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class DAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDB")
    lateinit var database: ArtDatabase
    private lateinit var  dao: DAO


    @Before
    fun setup(){

        hiltRule.inject()

        dao = database.dao()

    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTest() = runBlocking{
        val exampleArt = ArtModelRoom("artName","artistName","1900","test",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)

    }

    @Test
    fun deleteArtTest() = runBlocking{
        val deleteArt = ArtModelRoom("artName","artistName","1900","test",2)
        dao.insertArt(deleteArt)

        dao.deleteArt(deleteArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(deleteArt)
    }
}