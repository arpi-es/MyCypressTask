package com.example.mycypresstask.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycypresstask.databinding.AlbumsRecyclerItemBinding
import com.example.mycypresstask.model.AlbumsItem

class AlbumsAdapter : ListAdapter<AlbumsItem, AlbumsAdapter.AlbumsAdapterViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapterViewHolder {
        return AlbumsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumsAdapterViewHolder, position: Int) {
//        val item = getItem(position)

        var pos  = if (getActualItemCount() == 0) {position} else (position % getActualItemCount()) /* To scroll infinitely */
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
        if ( getActualItemCount() == 0 ) return  0
        return Integer.MAX_VALUE/2
    }

    class AlbumsAdapterViewHolder private constructor(val binding: AlbumsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlbumsItem) {
            binding.item = item
//            val adapter = PhotosAdapter()
//            binding.rvChild.layoutManager = LinearLayoutManager(binding.context)
//            binding.rvChild.adapter = adapter
//            lstChildRecyclerView.
//            lstChildRecyclerView

        }

        companion object {
            fun from(parent: ViewGroup): AlbumsAdapterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlbumsRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return AlbumsAdapterViewHolder(binding)
            }
        }

    }

//    override fun getItem(position: Int): AlbumsItem {
//        val a : Int = position % Int.MAX_VALUE
//        return  getItem((position % Int.MAX_VALUE)
//    }

//    override fun getItemCount(): Int {
//        return Int.MAX_VALUE
//    }


    private class DiffCallback : DiffUtil.ItemCallback<AlbumsItem>() {
        override fun areItemsTheSame(oldItem: AlbumsItem, newItem: AlbumsItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AlbumsItem, newItem: AlbumsItem): Boolean {
            return oldItem == newItem
        }
    }

}