package com.nicole.petnanny.ui.order.nannyorder

import android.view.LayoutInflater
import android.view.View
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
                viewModel._navigationToMyClientDetail.value = item
            }

            if (item.userCheckedStatus == true) {
                binding.tvClientStatus.text = "此筆訂單完成"
                binding.ivOrderChecked.visibility = View.VISIBLE
                binding.ivOrderUndone.visibility = View.GONE
            } else if (item.nannyCompletedStatus == true) {
                binding.tvClientStatus.text = "等待家長最後確認"
            } else if (item.userCheckoutStatus == true) {
                binding.tvClientStatus.text = "服務即將開始"
            } else if (item.nannyAcceptStatus == true) {
                binding.tvClientStatus.text = "等待家長的付款"
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