package com.nicole.petnanny.ui.home.nannydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.source.PetNannyRepository

class NannyDetailViewModel(private val repository: PetNannyRepository, private val arguments: Nanny): ViewModel() {

//    第一行給xml綁資料用 第二行將argus值指定給nannyDetail
    val nannyDetail: LiveData<Nanny>
        get() = nannyDetailArgus

    var nannyDetailArgus = MutableLiveData<Nanny>().apply {
        value = arguments
    }
}