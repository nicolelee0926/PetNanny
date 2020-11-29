package com.nicole.petnanny.ui.profile.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.petnanny.model.Nanny

class ServiceViewModel: ViewModel() {
    private val _serviceListData = MutableLiveData<List<Nanny>>()
    val serviceListData : LiveData<List<Nanny>>
        get() = _serviceListData

    val db = FirebaseFirestore.getInstance()

    init {
        getFirebaseData()
    }

    //    拿到firebase後存在liveData給adapter observe用
    fun getFirebaseData() {
        db.collection("Nanny").orderBy("created_time", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val serviceList= it.toObjects(Nanny::class.java)
                Log.d("getFirebaseData", " getFirebaseData: $serviceList")
                _serviceListData.value = serviceList
            }
    }
}