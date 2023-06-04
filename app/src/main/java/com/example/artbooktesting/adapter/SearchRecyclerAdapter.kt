package com.example.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.databinding.SearchRecyclerRowBinding
import javax.inject.Inject

class SearchRecyclerAdapter @Inject constructor(val glide: RequestManager): RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>() {
    class SearchViewHolder(val binding: SearchRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((String) -> Unit) ? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchRecyclerRowBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val url = images[position]

        glide.load(url).into(holder.binding.searchRecyclerRowImageView)

        holder.binding.searchRecyclerRowImageView.setOnClickListener {
            onItemClickListener?.let {
                it(url)
            }
        }


    }

     fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

}