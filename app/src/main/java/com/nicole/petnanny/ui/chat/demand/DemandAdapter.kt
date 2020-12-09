package com.nicole.petnanny.ui.chat.demand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.databinding.ItemChatDemandBinding


class DemandAdapter(val viewModel: DemandViewModel) : ListAdapter<Message, DemandAdapter.DemandViewHolder>(DemandDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemandViewHolder {
        return DemandViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DemandViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,viewModel)
    }

    class DemandViewHolder  private constructor(private val binding: ItemChatDemandBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Message, viewModel: DemandViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel.navigationToChatRoomDetail.value = true
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

class DemandDiffCallback : DiffUtil.ItemCallback<Message>()  {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}


