package com.nicole.petnanny.ui.chat.work

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.databinding.ItemChatWorkBinding


class WorkAdapter(val viewModel: WorkViewModel) : ListAdapter<Message, WorkAdapter.WorkViewHolder>(WorkDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class WorkViewHolder  private constructor(private val binding: ItemChatWorkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Message, viewModel: WorkViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
               viewModel.navigationToChatRoomDetail.value = true
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

class WorkDiffCallback : DiffUtil.ItemCallback<Message>()  {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}