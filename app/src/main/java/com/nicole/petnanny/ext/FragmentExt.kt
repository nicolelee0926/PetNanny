package com.nicole.petnanny.ext

import androidx.fragment.app.Fragment
import app.appworks.school.publisher.factory.ViewModelFactory
import com.nicole.petnanny.PetNannyApplication


fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PetNannyApplication).repository
    return ViewModelFactory(repository)
}

