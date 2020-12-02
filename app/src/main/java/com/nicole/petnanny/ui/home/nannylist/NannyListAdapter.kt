package com.nicole.petnanny.ui.home.nannylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.databinding.ItemHomeNannyListBinding
import com.nicole.petnanny.data.Nanny

class NannyListAdapter(val viewModel: NannyListViewModel) : ListAdapter<Nanny, NannyListAdapter.NannyListViewHolder>(NannyListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NannyListViewHolder {
        return NannyListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NannyListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class NannyListViewHolder  private constructor(private val binding: ItemHomeNannyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nanny, viewModel: NannyListViewModel) {
            binding.data = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                viewModel.navigationToNannyDetail.value = true
            }
        }

        companion object{
            fun from(parent: ViewGroup): NannyListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeNannyListBinding.inflate(layoutInflater, parent, false)
                return NannyListViewHolder(binding)
            }
        }
    }
}

class NannyListDiffCallback : DiffUtil.ItemCallback<Nanny>()  {
    override fun areItemsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem == newItem
    }
}