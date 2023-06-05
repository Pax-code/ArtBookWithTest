package com.example.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import com.example.artbooktesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.example.artbooktesting.R
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.getOrAwaitValue
import com.example.artbooktesting.repo.FakeArtBookRepository
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import com.google.common.truth.Truth.assertThat


@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtBookFragmentFactory


    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun imageOnClickFromAddArtFragmentToSearchFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddArtFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.addArtImage)).perform(click())

        Mockito.verify(navController).navigate(AddArtFragmentDirections.actionAddArtFragmentToSearchFragment())


    }

    @Test
    fun onBackPressed(){

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddArtFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)

        }


        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }

    @Test
    fun saveButtonAndTextsTest() {
        val testViewModel = ArtBookViewModel(FakeArtBookRepository())
        launchFragmentInHiltContainer<AddArtFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.addArtText)).perform(replaceText("Scream"))
        Espresso.onView(ViewMatchers.withId(R.id.addArtArtistText)).perform(replaceText("Edvard Munch"))
        Espresso.onView(ViewMatchers.withId(R.id.addArtDateText)).perform(replaceText("1495"))
        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artlist.getOrAwaitValue()).contains(
            ArtModelRoom("Scream", "Edvard Munch","1495","")
        )
    }

}