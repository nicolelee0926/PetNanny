package com.nicole.petnanny.ext

import androidx.fragment.app.Fragment
import app.appworks.school.publisher.factory.ViewModelFactory
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.factory.NannyDetailViewModelFactory
import com.nicole.petnanny.factory.NannyListViewModelFactory
import com.nicole.petnanny.ui.home.nannylist.NannyListViewModel


fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(arguments: String): NannyListViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return NannyListViewModelFactory(repository, arguments)
}

fun Fragment.getVmFactory(arguments: Nanny): NannyDetailViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return NannyDetailViewModelFactory(repository, arguments)
}
