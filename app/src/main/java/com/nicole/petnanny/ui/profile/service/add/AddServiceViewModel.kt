package com.nicole.petnanny.ui.profile.service.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Nanny

class AddServiceViewModel: ViewModel() {

    private val _sendServiceData = MutableLiveData<Nanny>()
    val sendServiceData: LiveData<Nanny>
        get() = _sendServiceData

//    fun setService() {
//            val serviceData = Nanny(
//                serviceName = "專業寵物保姆，值得您的信賴，呵護極致呵護極致",
//                serviceType = "到府訓練",
//                nannyIntroduction = "身體很健康，有定期打疫苗"
//            )
//        _sendServiceData.value = serviceData
//    }


}