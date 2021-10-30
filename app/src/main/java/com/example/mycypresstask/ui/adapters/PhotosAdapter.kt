package com.example.mycypresstask.ui.adapters


import android.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycypresstask.databinding.PhotosRecyclerItemBinding
import com.example.mycypresstask.model.PhotosItem


class PhotosAdapter :
    ListAdapter<PhotosItem, PhotosAdapter.PhotosAdapterViewHolder>(PhotosAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapterViewHolder {

        return PhotosAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotosAdapterViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
        val pos = if (getActualItemCount() == 0) position else (position % getActualItemCount()) /* To scroll infinitely */
        val item = getItem(pos)
        holder.bind(item)
    }

    /*
    The getItemCount() is used by the recyclerview to determinate how many items there are in the list,
    and if it returns always MAX_VALUE, the list is pretty much infinite.
    */
    private fun getActualItemCount(): Int {
        return getCurrentList().size
    }

    override fun getItemCount(): Int {
        if (getActualItemCount() == 0) return 0
        return Integer.MAX_VALUE
    }

    class PhotosAdapterViewHolder private constructor(val binding: PhotosRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotosItem) {
            binding.item = item
        }

        companion object {
            fun from(parent: ViewGroup): PhotosAdapterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotosRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return PhotosAdapterViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<PhotosItem>() {
        override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem == newItem
        }
    }
}

