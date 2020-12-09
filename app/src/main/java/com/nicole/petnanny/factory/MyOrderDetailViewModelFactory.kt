package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel


@Suppress("UNCHECKED_CAST")
class MyOrderDetailViewModelFactory(
    private val repository: PetNannyRepository,
    private val argumentsMyOrder: Order
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MyOrderDetailViewModel::class.java) ->
                    MyOrderDetailViewModel(repository, argumentsMyOrder)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}