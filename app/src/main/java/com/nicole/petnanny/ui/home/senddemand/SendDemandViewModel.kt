package com.nicole.petnanny.ui.home.senddemand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SendDemandViewModel(private val repository: PetNannyRepository): ViewModel() {

    val setDemandData = MutableLiveData<Order>()

    var orderPet  = MutableLiveData<String>().apply { value = "" }
//    var orderStartTime = MutableLiveData<String>().apply { value = "" }
//    var orderEndTime = MutableLiveData<String>().apply { value = "" }
    var orderServiceAddress = MutableLiveData<String>().apply { value = "" }
    var orderNote = MutableLiveData<String>().apply { value = "" }


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun sendDemand() {
        setDemandData.value = Order(
            petID= orderPet.value.toString(),
//            orderStartTime= orderStartTime.value.toString(),
//            orderEndTime = orderEndTime.value.toString(),
            address = orderServiceAddress.value.toString(),
            note = orderNote.value.toString(),
            userEmail = UserManager.user.value?.userEmail
        )
    }



}