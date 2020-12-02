package com.nicole.petnanny.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.databinding.ItemHomeNannyBinding
import com.nicole.petnanny.data.Nanny

class HomeAdapter : ListAdapter<Nanny, HomeAdapter.HomeViewHolder>(HomeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HomeViewHolder  private constructor(private val binding: ItemHomeNannyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nanny) {
            binding.data = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): HomeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeNannyBinding.inflate(layoutInflater, parent, false)
                return HomeViewHolder(binding)
            }
        }
    }
}

class HomeDiffCallback : DiffUtil.ItemCallback<Nanny>()  {
    override fun areItemsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem == newItem
    }
}