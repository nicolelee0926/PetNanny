package com.nicole.petnanny.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.CurrentFragmentType
import com.nicole.petnanny.data.source.PetNannyRepository

class MainViewModel(private val repository: PetNannyRepository): ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    val addPetFlag = MutableLiveData<Boolean>()
    fun changePetStatusTrue(){
        addPetFlag.value = true
    }
    fun changePetStatusFalse(){
        addPetFlag.value = false
    }

    val addServiceFlag = MutableLiveData<Boolean>()
    fun changeServiceStatusTrue(){
        addServiceFlag.value = true
    }
    fun changeServiceStatusFalse(){
        addServiceFlag.value = false
    }
}