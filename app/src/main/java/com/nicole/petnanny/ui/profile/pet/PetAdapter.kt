package com.nicole.petnanny.ui.profile.pet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicole.petnanny.databinding.ItemProfilePetBinding
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.ui.profile.pet.edit.EditPetViewModel

class PetAdapter(val viewModel: PetViewModel) : ListAdapter<Pet, PetAdapter.PetViewHolder>(PetDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    class PetViewHolder  private constructor(private val binding: ItemProfilePetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Pet, viewModel: PetViewModel) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToEditPetDetail.value = item
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): PetViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfilePetBinding.inflate(layoutInflater, parent, false)
                return PetViewHolder(binding)
            }
        }
    }
}

class PetDiffCallback : DiffUtil.ItemCallback<Pet>()  {
    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem == newItem
    }
}