package com.nicole.petnanny.ext

import androidx.fragment.app.Fragment
import app.appworks.school.publisher.factory.ViewModelFactory
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.factory.EditPetViewModelFactory
import com.nicole.petnanny.factory.NannyListViewModelFactory
import com.nicole.petnanny.factory.NannyViewModelFactory
import com.nicole.petnanny.factory.OrderViewModelFactory
import com.nicole.petnanny.ui.profile.pet.edit.EditPetViewModel

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(arguments: String): NannyListViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return NannyListViewModelFactory(repository, arguments)
}

fun Fragment.getVmFactory(arguments: Nanny): NannyViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return NannyViewModelFactory(repository, arguments)
}

fun Fragment.getVmFactory(arguments: Order): OrderViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return OrderViewModelFactory(repository, arguments)
}

fun Fragment.getVmFactory(arguments: Pet): EditPetViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return EditPetViewModelFactory(repository, arguments)
}



