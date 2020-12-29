package com.nicole.petnanny.ui.chat.work

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.databinding.ItemChatWorkBinding


class WorkAdapter(val viewModel: WorkViewModel) : ListAdapter<Order, WorkAdapter.WorkViewHolder>(WorkDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class WorkViewHolder  private constructor(private val binding: ItemChatWorkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Order, viewModel: WorkViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
               viewModel._navigationToWorkChatRoomDetail.value = item
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): WorkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChatWorkBinding.inflate(layoutInflater, parent, false)
                return WorkViewHolder(binding)
            }
        }
    }
}

class WorkDiffCallback : DiffUtil.ItemCallback<Order>()  {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}