package com.nicole.petnanny.ui.profile.service

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.nicole.petnanny.databinding.ItemProfileServiceBinding
import com.nicole.petnanny.data.Nanny

class ServiceAdapter(val viewModel: ServiceViewModel, val serviceListView: ConstraintLayout) : ListAdapter<Nanny, ServiceAdapter.ServiceViewHolder>(ServiceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel, serviceListView)
    }

    class ServiceViewHolder  private constructor(private val binding: ItemProfileServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nanny, viewModel: ServiceViewModel, serviceListView: ConstraintLayout) {
            binding.data = item
            binding.root.setOnClickListener {
                viewModel._navigationToEditSerciveDetail.value = item
            }
            binding.btnRemove.setOnClickListener {

                val timer = object : CountDownTimer(5000, 5000) {

                    override fun onTick(millisUntilFinished: Long) {
                        val snackBar = Snackbar.make(serviceListView, "確定要刪除嗎？", Snackbar.LENGTH_LONG)

                        val serviceList = viewModel.service.value
                        val deleteServiceList = serviceList?.filter {
                            it != item
                        }
                        viewModel._service.value = deleteServiceList

                        snackBar.setAction("回復") {
                            viewModel._service.value = serviceList
                            snackBar.dismiss()
                            this.cancel()
                        }
                        snackBar.show()
                    }

                    override fun onFinish() {
                        this.cancel()
                        item.ID?.let { it1 -> viewModel.deleteService(it1) }
                        viewModel.getServicesResult()
                    }
                }
                timer.start()
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ServiceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfileServiceBinding.inflate(layoutInflater, parent, false)
                return ServiceViewHolder(binding)
            }
        }
    }
}

class ServiceDiffCallback : DiffUtil.ItemCallback<Nanny>()  {
    override fun areItemsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Nanny, newItem: Nanny): Boolean {
        return oldItem == newItem
    }
}