package com.nicole.petnanny.ui.profile.pet

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nicole.petnanny.databinding.ItemProfilePetBinding
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.ui.profile.pet.edit.EditPetViewModel
import kotlin.concurrent.timer

class PetAdapter(val viewModel: PetViewModel, val petListView: ConstraintLayout) : ListAdapter<Pet, PetAdapter.PetViewHolder>(PetDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel, petListView)
    }

    class PetViewHolder  private constructor(private val binding: ItemProfilePetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Pet, viewModel: PetViewModel, petListView: ConstraintLayout) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToEditPetDetail.value = item
            }
            binding.btnRemove.setOnClickListener {

                val timer = object : CountDownTimer(8000, 8000) {

                    override fun onTick(millisUntilFinished: Long) {
                        val snackBar = Snackbar.make(petListView, "確定要刪除嗎？", Snackbar.LENGTH_LONG)

                        val petList = viewModel.pet.value
                        val deletePetList = petList?.filter {
                            it != item
                        }
                        viewModel._pet.value = deletePetList

                        snackBar.setAction("回復") {
                            viewModel._pet.value = petList
                            snackBar.dismiss()
                            this.cancel()
                        }
                        snackBar.show()
                    }

                    override fun onFinish() {
                        this.cancel()
                        item.petID?.let { it1 -> viewModel.deletePet(it1) }
                        viewModel.getPetsResult()
                    }
                }
                timer.start()
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