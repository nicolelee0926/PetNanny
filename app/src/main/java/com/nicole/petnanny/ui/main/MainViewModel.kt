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

    val addUserFlag = MutableLiveData<Boolean>()
    fun changeUserStatusTrue(){
        addUserFlag.value = true
    }
    fun changeUserStatusFalse(){
        addUserFlag.value = false
    }

    val addNannyExamineFlag = MutableLiveData<Boolean>()
    fun changeNannyExamineStatusTrue(){
        addNannyExamineFlag.value = true
    }
    fun changeNannyExamineStatusFalse(){
        addNannyExamineFlag.value = false
    }

    val editPetFlag = MutableLiveData<Boolean>()
    fun changeEditPetStatusTrue(){
        editPetFlag.value = true
    }
    fun changeEditPetStatusFalse(){
        editPetFlag.value = false
    }

    val editServiceFlag = MutableLiveData<Boolean>()
    fun changeEditServiceStatusTrue(){
        editServiceFlag.value = true
    }
    fun changeEditServiceStatusFalse(){
        editServiceFlag.value = false
    }
}