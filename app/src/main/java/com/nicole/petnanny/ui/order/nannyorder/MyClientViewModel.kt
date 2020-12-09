package com.nicole.petnanny.ui.order.nannyorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MyClientViewModel(private val repository: PetNannyRepository): ViewModel() {

    private val _myClientList = MutableLiveData<List<Order>>()
    val myClientList: LiveData<List<Order>>
        get() = _myClientList

    // to myClientListAdapter viewHolder button set value 用
    var navigationToMyClientDetail = MutableLiveData<Boolean>()

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    //    to nanny detail page
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        navigationToMyClientDetail.value = null
    }

    fun getFakeMyClientData() {
//        val myClientList = mutableListOf(
//            Order(
//
//            )
//        )
//        _myClientList.value = myClientList
    }



    fun displayMyClientDetailsComplete () {
        navigationToMyClientDetail.value = null
    }

}