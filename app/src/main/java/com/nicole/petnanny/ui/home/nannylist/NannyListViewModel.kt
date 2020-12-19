package com.nicole.petnanny.ui.home.nannylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NannyListViewModel(private val repository: PetNannyRepository, private val arguments: String): ViewModel() {

//    首頁選到類別後傳進來的字串 與 list頁面點dialog類別後要傳出去的字串共用livaData (外面選了後 裡面可以再選一次)
    var serviceType = MutableLiveData<String>().apply {
        value = arguments
    }

    private val _nannyList = MutableLiveData<List<Nanny>>()
    val nannyList: LiveData<List<Nanny>>
        get() = _nannyList

    // to nannyListAdapter viewHolder button set value 用
    val _navigationToNannyDetail = MutableLiveData<Nanny>()
    val navigationToNannyDetail: LiveData<Nanny>
        get() = _navigationToNannyDetail

//    搜尋用 選擇寵物型態
    var selectedPetType  = MutableLiveData<String>().apply { value = "" }

//    搜尋用 選擇地區
    var selectedLocation  = MutableLiveData<String>().apply { value = "" }


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

    // Handle leave detail
    private val _leaveDetail = MutableLiveData<Boolean>()

    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _navigationToNannyDetail.value = null
    }

    fun getThreeSelectedList(serviceType: String, petType: String, location: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getThreeSelectedList(serviceType, petType, location)

            _nannyList.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data

                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = PetNannyApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }

            _refreshStatus.value = false
        }
    }

    fun displayNannyDetailsComplete () {
        _navigationToNannyDetail.value = null
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

}