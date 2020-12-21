package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel
import com.nicole.petnanny.ui.home.senddemand.SendDemandViewModel
import com.nicole.petnanny.ui.profile.service.edit.EditServiceViewModel

@Suppress("UNCHECKED_CAST")
class NannyViewModelFactory(
    private val repository: PetNannyRepository,
    private val arguments: Nanny
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(NannyDetailViewModel::class.java) ->
                    NannyDetailViewModel(repository, arguments)

                isAssignableFrom(SendDemandViewModel::class.java) ->
                    SendDemandViewModel(repository, arguments)

                isAssignableFrom(EditServiceViewModel::class.java) ->
                    EditServiceViewModel(repository, arguments)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
