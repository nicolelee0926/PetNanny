package com.nicole.petnanny.ui.home.senddemand

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SendDemandViewModel(private val repository: PetNannyRepository, private val argumentsNanny: Nanny, private val argumentsUser: User): ViewModel() {

    val setDemandData = MutableLiveData<Order>()

    //    第一行給xml綁資料用 第二行將argus值指定給nannyDetail
    val nannyData: LiveData<Nanny>
        get() = nannyDataArgus

    val userData: LiveData<User>
        get() = userDataArgus

//  接收 nanny detail 的資料
    var userDataArgus = MutableLiveData<User>().apply {
        value = argumentsUser
    }
    var nannyDataArgus = MutableLiveData<Nanny>().apply {
        value = argumentsNanny
    }

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

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)




    fun addDemand(demand: Order) {
        Log.d("addPet", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addDemand(demand)) {
                is Result.Success-> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = PetNannyApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }



    fun sendDemand() {
        setDemandData.value = Order(
            petID= orderPet.value.toString(),
//            orderStartTime= orderStartTime.value.toString(),
//            orderEndTime = orderEndTime.value.toString(),
            address = orderServiceAddress.value.toString(),
            note = orderNote.value.toString(),
            userEmail = UserManager.user.value?.userEmail,
            nannyServiceDetail = nannyDataArgus.value,
            userInfo = userDataArgus.value
        )
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}