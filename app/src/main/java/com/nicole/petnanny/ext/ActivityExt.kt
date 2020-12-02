package com.nicole.petnanny.ext

import android.app.Activity
import app.appworks.school.publisher.factory.ViewModelFactory
import com.nicole.petnanny.PetNannyApplication


fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as PetNannyApplication).repository
    return ViewModelFactory(repository)
}

