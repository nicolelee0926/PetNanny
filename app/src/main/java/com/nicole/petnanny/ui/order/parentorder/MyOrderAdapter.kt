package com.nicole.petnanny.ui.order.parentorder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.databinding.ItemOrderMyOrderBinding


class MyOrderAdapter(val viewModel: MyOrderViewModel) : ListAdapter<Order, MyOrderAdapter.MyOrderViewHolder>(MyOrderDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        return MyOrderViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class MyOrderViewHolder  private constructor(private val binding: ItemOrderMyOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Order, viewModel: MyOrderViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToMyOrderDetail.value = item
            }

            if (item.userCheckedStatus == true) {
                binding.tvMyOrderStatus.setText("此筆訂單完成")
                binding.ivClientChecked.visibility = View.VISIBLE
                binding.ivClientUndone.visibility = View.GONE
            } else if (item.nannyCompletedStatus == true) {
                binding.tvMyOrderStatus.setText("等待您的確認")
            } else if (item.userCheckoutStatus == true) {
                binding.tvMyOrderStatus.setText("等待服務開始")
            } else if (item.nannyAcceptStatus == true) {
                binding.tvMyOrderStatus.setText("等待您的付款")
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyOrderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOrderMyOrderBinding.inflate(layoutInflater, parent, false)
                return MyOrderViewHolder(binding)
            }
        }
    }
}

class MyOrderDiffCallback : DiffUtil.ItemCallback<Order>()  {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}