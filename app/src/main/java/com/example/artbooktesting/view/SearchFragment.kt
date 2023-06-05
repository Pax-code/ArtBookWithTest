package com.example.artbooktesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.R
import com.example.artbooktesting.adapter.SearchRecyclerAdapter
import com.example.artbooktesting.databinding.FragmentSearchBinding
import com.example.artbooktesting.util.Status
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment @Inject constructor(
    val searchRecyclerAdapter: SearchRecyclerAdapter,
    var viewModel: ArtBookViewModel
): Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() =  _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(100)
                it?.let {
                    if (it.toString().isNotEmpty())  {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        binding.imageRecyclerView.adapter = searchRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        searchRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    searchRecyclerAdapter.images = urls ?: listOf()
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}