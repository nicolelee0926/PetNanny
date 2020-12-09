package com.nicole.petnanny.ui.order.nannyorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.databinding.ItemOrderMyClientBinding



class MyClientAdapter(val viewModel: MyClientViewModel) : ListAdapter<Order, MyClientAdapter.MyClientViewHolder>(MyClientDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClientViewHolder {
        return MyClientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyClientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class MyClientViewHolder  private constructor(private val binding: ItemOrderMyClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Order, viewModel: MyClientViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel.navigationToMyClientDetail.value = true
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyClientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOrderMyClientBinding.inflate(layoutInflater, parent, false)
                return MyClientViewHolder(binding)
            }
        }
    }
}

class MyClientDiffCallback : DiffUtil.ItemCallback<Order>()  {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}