package com.nicole.petnanny.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel
import com.nicole.petnanny.ui.profile.pet.edit.EditPetViewModel


@Suppress("UNCHECKED_CAST")
class EditPetViewModelFactory(
    private val repository: PetNannyRepository,
    private val arguments: Pet
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(EditPetViewModel::class.java) ->
                    EditPetViewModel(repository, arguments)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}