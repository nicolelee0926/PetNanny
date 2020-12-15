package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.chat.demand.add.DemandDetailViewModel
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel
import com.nicole.petnanny.ui.home.senddemand.SendDemandViewModel
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel

@Suppress("UNCHECKED_CAST")
class OrderViewModelFactory(
    private val repository: PetNannyRepository,
    private val arguments: Order
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MyOrderDetailViewModel::class.java) ->
                    MyOrderDetailViewModel(repository, arguments)

                isAssignableFrom(MyClientDetailViewModel::class.java) ->
                    MyClientDetailViewModel(repository, arguments)

                isAssignableFrom(DemandDetailViewModel::class.java) ->
                    DemandDetailViewModel(repository, arguments)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
