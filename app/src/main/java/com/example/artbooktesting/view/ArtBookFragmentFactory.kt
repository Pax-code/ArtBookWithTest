package com.example.artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.adapter.FeedRecyclerAdapter
import com.example.artbooktesting.adapter.SearchRecyclerAdapter
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import javax.inject.Inject

class ArtBookFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val feedRecyclerAdapter: FeedRecyclerAdapter,
    private val searchRecyclerAdapter: SearchRecyclerAdapter,
    private val artBookViewModel: ArtBookViewModel
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            FeedFragment::class.java.name -> FeedFragment(feedRecyclerAdapter,artBookViewModel)
            SearchFragment::class.java.name -> SearchFragment(searchRecyclerAdapter,artBookViewModel)
            AddArtFragment::class.java.name -> AddArtFragment(glide,artBookViewModel)
            else -> super.instantiate(classLoader, className)
        }

    }

}