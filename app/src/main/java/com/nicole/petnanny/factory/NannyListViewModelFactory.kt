package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel

@Suppress("UNCHECKED_CAST")
class NannyListViewModelFactory(
    private val repository: PetNannyRepository,
    private val arguments: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(NannyListViewModel::class.java) ->
                    NannyListViewModel(repository, arguments)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}