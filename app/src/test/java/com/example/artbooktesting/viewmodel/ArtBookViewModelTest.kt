package com.example.artbooktesting.viewmodel

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbooktesting.getOrAwaitValueTest
import com.example.artbooktesting.repo.FakeArtBookRepository
import com.example.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtBookViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtBookViewModel

    @Before
    fun setup(){
       viewModel = ArtBookViewModel(FakeArtBookRepository())
    }


    @Test
    fun `insert art without artDate returns error`(){
        viewModel.makeArt("artName","artistName","")
        val value = viewModel.insertArtMsg.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artName returns error`(){
        viewModel.makeArt("","artistName","1900")
        val value = viewModel.insertArtMsg.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("artName","","1900")
        val value = viewModel.insertArtMsg.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}