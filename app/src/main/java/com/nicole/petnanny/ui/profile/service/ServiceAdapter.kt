package com.nicole.petnanny.ui.profile.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.databinding.ItemProfileNannyBinding
import com.nicole.petnanny.model.Nanny

class ServiceAdapter : ListAdapter<Nanny, ServiceAdapter.ServiceViewHolder>(ServiceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ServiceViewHolder  private constructor(private val binding: ItemProfileNannyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nanny) {
            binding.data = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ServiceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfileNannyBinding.inflate(layoutInflater, parent, false)
                return ServiceViewHolder(binding)
            }
        }
    }
}

class ServiceDiffCallback : DiffUtil.ItemCallback<Nanny>()  {
    override fun areItemsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem == newItem
    }
}