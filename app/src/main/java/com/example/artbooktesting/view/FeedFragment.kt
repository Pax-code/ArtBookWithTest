package com.example.artbooktesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbooktesting.R
import com.example.artbooktesting.adapter.FeedRecyclerAdapter
import com.example.artbooktesting.databinding.FragmentFeedBinding
import com.example.artbooktesting.viewmodel.ArtBookViewModel
import javax.inject.Inject

class FeedFragment @Inject constructor(
    private val feedRecyclerAdapter: FeedRecyclerAdapter,
    private val viewModel: ArtBookViewModel
) : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_animation)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_animation)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_animation)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_animation)}
    private var clicked = false



    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
           val layoutPosition = viewHolder.layoutPosition
            val selectedArt = feedRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        binding.recyclerView.adapter = feedRecyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerView)

        binding.plusButton.setOnClickListener(){

            setVisibility(clicked)
            setAnimation(clicked)
            setClickable(clicked)
            clicked = !clicked
        }

        binding.addArtButton.setOnClickListener(){
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToAddArtFragment())
        }

        binding.editButton.setOnClickListener(){
            Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()

        }


    }

    private fun subscribeToObservers(){
        viewModel.artlist.observe(viewLifecycleOwner, Observer {
          feedRecyclerAdapter.arts = it
        })
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.editButton.startAnimation(fromBottom)
            binding.addArtButton.startAnimation(fromBottom)
            binding.plusButton.startAnimation(rotateOpen)

        }else{
            binding.editButton.startAnimation(toBottom)
            binding.addArtButton.startAnimation(toBottom)
            binding.plusButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {

        if (!clicked){
            binding.editButton.visibility = View.VISIBLE
            binding.addArtButton.visibility = View.VISIBLE
        }else{
            binding.editButton.visibility = View.GONE
            binding.addArtButton.visibility = View.GONE
        }

    }

    private fun setClickable(clicked: Boolean){
        if (!clicked){
            binding.editButton.isClickable = true
            binding.addArtButton.isClickable = true
        }else{
            binding.editButton.isClickable = false
            binding.addArtButton.isClickable = false
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}