package com.nicole.petnanny.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.CurrentFragmentType

class MainActivityViewModel: ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()
}