package com.example.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.databases.room.ArtModelRoom
import com.example.artbooktesting.databinding.RecyclerRowBinding
import javax.inject.Inject

class FeedRecyclerAdapter @Inject constructor(
    val glide: RequestManager
): RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder>() {

    class FeedViewHolder( val binding: RecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root)


         private val diffUtil = object : DiffUtil.ItemCallback<ArtModelRoom>(){
            override fun areItemsTheSame(oldItem: ArtModelRoom, newItem: ArtModelRoom): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArtModelRoom, newItem: ArtModelRoom): Boolean {
                return oldItem == newItem

            }
        }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var arts : List<ArtModelRoom>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val art = arts[position]

         glide.load(art.imageUrl).into(holder.binding.imageView)

        holder.binding.artDateText.text = art.artDate
        holder.binding.artNameText.text = art.artName
        holder.binding.artistText.text = art.artistName

    }
}