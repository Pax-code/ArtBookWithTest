package com.example.artbooktesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.databinding.FragmentAddArtBinding
import com.example.artbooktesting.util.Status
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import javax.inject.Inject

class AddArtFragment @Inject constructor(

    val glide: RequestManager,
    var viewModel: ArtBookViewModel
) : Fragment() {
    private var _binding: FragmentAddArtBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddArtBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        binding.addArtImage.setOnClickListener{
            findNavController().navigate(AddArtFragmentDirections.actionAddArtFragmentToSearchFragment())

        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener{
            viewModel.makeArt(binding.addArtText.text.toString().trim(),
                binding.addArtArtistText.text.toString().trim(),
                binding.addArtDateText.text.toString().trim())
        }
    }

    private fun subscribeToObservers(){
        viewModel.slectedImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(binding.addArtImage)
        })

        viewModel.insertArtMsg.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {}
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}