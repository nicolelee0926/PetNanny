package com.nicole.petnanny.ui.chat.demand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.databinding.ItemChatDemandBinding


class DemandAdapter(val viewModel: DemandViewModel) : ListAdapter<Order, DemandAdapter.DemandViewHolder>(DemandDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemandViewHolder {
        return DemandViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DemandViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,viewModel)
    }

    class DemandViewHolder  private constructor(private val binding: ItemChatDemandBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Order, viewModel: DemandViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToDemandChatRoomDetail.value = item
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): DemandViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChatDemandBinding.inflate(layoutInflater, parent, false)
                return DemandViewHolder(binding)
            }
        }
    }
}

class DemandDiffCallback : DiffUtil.ItemCallback<Order>()  {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}


