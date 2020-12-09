package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel
import com.nicole.petnanny.ui.home.senddemand.SendDemandViewModel


@Suppress("UNCHECKED_CAST")
class SendDemandViewModelFactory(
    private val repository: PetNannyRepository,
    private val argumentsNanny: Nanny,
    private val argumentsUser: User,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SendDemandViewModel::class.java) ->
                    SendDemandViewModel(repository, argumentsNanny, argumentsUser)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}