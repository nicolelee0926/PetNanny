package com.nicole.petnanny.ui.profile.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.databinding.ItemProfileServiceBinding
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.ui.profile.pet.PetViewModel

class ServiceAdapter(val viewModel: ServiceViewModel) : ListAdapter<Nanny, ServiceAdapter.ServiceViewHolder>(ServiceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class ServiceViewHolder  private constructor(private val binding: ItemProfileServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nanny, viewModel: ServiceViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToEditSerciveDetail.value = item
            }
            binding.btnRemove.setOnClickListener {
                item.ID?.let { it1 -> viewModel.deleteService(it1) }
                viewModel.getServicesResult()
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ServiceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfileServiceBinding.inflate(layoutInflater, parent, false)
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