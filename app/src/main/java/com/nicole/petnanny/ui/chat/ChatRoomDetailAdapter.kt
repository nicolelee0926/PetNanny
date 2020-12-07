package com.nicole.petnanny.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.databinding.ItemChatReceiverBinding
import com.nicole.petnanny.databinding.ItemChatSenderBinding


class ChatRoomDetailAdapter : ListAdapter<ChatRoomDetailAdapter.CRText, RecyclerView.ViewHolder>(DiffCallback) {

    class SendViewHolder(private var binding: ItemChatSenderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userText: Message) {
            binding.userText = userText
            binding.executePendingBindings()
        }
    }

    class ReceivedViewHolder(private var binding: ItemChatReceiverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userText: Message) {
            binding.userText = userText
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CRText>() {
        override fun areItemsTheSame(oldItem: CRText, newItem: CRText): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CRText, newItem: CRText): Boolean {
            return oldItem.email == newItem.email
        }

        private const val ITEM_VIEW_TYPE_SEND = 123
        private const val ITEM_VIEW_TYPE_RECEIVED = 456


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
                holder.bind((getItem(position) as CRText.Send).userText)
            }
            is ReceivedViewHolder -> {
                holder.bind((getItem(position) as CRText.Received).userText)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CRText.Send -> ITEM_VIEW_TYPE_SEND
            is CRText.Received -> ITEM_VIEW_TYPE_RECEIVED
        }
    }


    sealed class CRText {
        abstract val email: String

        data class Send(val userText: Message) : CRText() {
            override val email: String
                get() = userText.sender?.userEmail.toString()
        }

        data class Received(val userText: Message) : CRText() {
            override val email: String
                get() = userText.receiver?.userEmail.toString()
        }
    }


}