package com.example.artbooktesting.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.example.artbooktesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.example.artbooktesting.R

@HiltAndroidTest
class FeedFragmentTest {

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtBookFragmentFactory

    @Before
    fun setup(){
        hiltrule.inject()
    }


    @Test
    fun testNavigationFromFeedFragmentToAddArtFragment(){

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<FeedFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.plusButton)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.addArtButton)).perform(click())


        Mockito.verify(navController).navigate(
            FeedFragmentDirections.actionFeedFragmentToAddArtFragment()
        )
    }

}