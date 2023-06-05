package com.example.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.artbooktesting.launchFragmentInHiltContainer
import com.example.artbooktesting.repo.FakeArtBookRepository
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.example.artbooktesting.R
import com.example.artbooktesting.adapter.SearchRecyclerAdapter
import com.example.artbooktesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat


@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtBookFragmentFactory


    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImageFromApiWithUrl(){
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtBookViewModel(FakeArtBookRepository())


        launchFragmentInHiltContainer<SearchFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            searchRecyclerAdapter.images = listOf("testTest.com")
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SearchRecyclerAdapter.SearchViewHolder>(0, click())
        )

        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.slectedImageUrl.getOrAwaitValue()).isEqualTo("testTest.com")

    }

}