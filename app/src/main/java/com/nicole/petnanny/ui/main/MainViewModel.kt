package com.nicole.petnanny.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.CurrentFragmentType
import com.nicole.petnanny.data.source.PetNannyRepository

class MainViewModel(private val repository: PetNannyRepository): ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()
}