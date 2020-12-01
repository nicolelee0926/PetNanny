package com.nicole.petnanny.ui.profile.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.model.Nanny

class ServiceViewModel: ViewModel() {
//    private val _serviceListData = MutableLiveData<List<Nanny>>()
//    val serviceListData : LiveData<List<Nanny>>
//        get() = _serviceListData
//
//    val db = FirebaseFirestore.getInstance()

    private val _getFakeServiceListData = MutableLiveData<List<Nanny>>()
    val getFakeServiceListData : LiveData<List<Nanny>>
        get() = _getFakeServiceListData

    init {
//        getFirebaseData()
        getFakeServiceData()
    }

    //    拿到firebase後存在liveData給adapter observe用
//    fun getFirebaseData() {
//        db.collection("Nanny").orderBy("created_time", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener {
//                val serviceList= it.toObjects(Nanny::class.java)
//                Log.d("getFirebaseData", " getFirebaseData: $serviceList")
//                _serviceListData.value = serviceList
//            }
//    }

    private fun getFakeServiceData() {
        val serviceData = mutableListOf(
            Nanny(serviceName = "專業寵物保姆，值得您的信賴，呵護極致呵護極致",
                serviceType = "到府訓練",
                nannyIntroduction = "身體很健康，有定期打疫苗")
        )
        _getFakeServiceListData.value = serviceData
    }
}