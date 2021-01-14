package com.nicole.petnanny.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.databinding.ItemChatReceiverBinding
import com.nicole.petnanny.databinding.ItemChatSenderBinding
import com.nicole.petnanny.ui.login.UserManager


class ChatRoomDetailAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback) {

    class SendViewHolder(private var binding: ItemChatSenderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    class ReceivedViewHolder(private var binding: ItemChatReceiverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.senderEmail == newItem.senderEmail
        }

        private const val ITEM_VIEW_TYPE_SEND = 1
        private const val ITEM_VIEW_TYPE_RECEIVED = 2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_SEND -> SendViewHolder(
                ItemChatSenderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_RECEIVED -> ReceivedViewHolder(
                ItemChatReceiverBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SendViewHolder -> {
                holder.bind((getItem(position)))
            }
            is ReceivedViewHolder -> {
                holder.bind((getItem(position)))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderEmail != UserManager.user.value?.userEmail) {
            ITEM_VIEW_TYPE_RECEIVED
        } else {
            ITEM_VIEW_TYPE_SEND
        }
    }

}