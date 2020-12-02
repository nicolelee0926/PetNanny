package com.nicole.petnanny.ui.profile.pet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Pet

class PetViewModel:ViewModel() {

    private val _getFakePetListData = MutableLiveData<List<Pet>>()
    val getFakePetListData : LiveData<List<Pet>>
        get() = _getFakePetListData

    init {
//        getFirebaseData()
        getFakePetData()
    }

    private fun getFakePetData() {
        val petData = mutableListOf(
            Pet(petName = "Oreo",
                petAge = 7,
                gender = "母",
                petFigure = "小型",
                petVariety = "貴賓",
                petIntroduction = "身體很健康，有定期打疫苗")
        )
        _getFakePetListData.value = petData
    }
}