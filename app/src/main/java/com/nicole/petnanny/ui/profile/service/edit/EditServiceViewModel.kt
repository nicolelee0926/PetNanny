package com.nicole.petnanny.ui.profile.service.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditServiceViewModel(private val repository: PetNannyRepository, private val arguments: Nanny): ViewModel() {
    //    第一行給xml綁資料用 第二行將argus值指定給serviceDetail
    val serviceDetail: LiveData<Nanny>
        get() = serviceDetailArgus

    var serviceDetailArgus = MutableLiveData<Nanny>().apply {
        value = arguments
    }

    //    edit service data 修該過後的整包service
    val setEditServiceData = MutableLiveData<Nanny>()

    var editSelectedServiceType  = MutableLiveData<String>().apply { value = "" }
    var editServicePrice = MutableLiveData<String>().apply { value = "" }
    var editServiceName = MutableLiveData<String>().apply { value = "" }
    var editServiceIntroduction = MutableLiveData<String>().apply { value = "" }
    var editSelectedServiceLocation = MutableLiveData<String>().apply { value = "" }
    var editSelectedAcceptPet = MutableLiveData<String>().apply { value = "" }

    //  修改成功flag
    private val _modifyDataFinished = MutableLiveData<Boolean>()
    val modifyDataFinished: LiveData<Boolean>
        get() = _modifyDataFinished

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _modifyDataFinished.value = null
    }

    fun updateService(service: Nanny) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateService(service)) {
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
        _modifyDataFinished.value = true
    }

    fun setEditService() {
//        把之前帶過來的data先存起來
        val previousServiceData = serviceDetail.value
//        再把新修改過的存到剛剛的liveData
        previousServiceData?.price = editServicePrice.value
        previousServiceData?.serviceName = editServiceName.value
        previousServiceData?.nannyIntroduction = editServiceIntroduction.value
        previousServiceData?.serviceType = editSelectedServiceType.value
        previousServiceData?.serviceArea = editSelectedServiceLocation.value
        previousServiceData?.acceptPetType = editSelectedAcceptPet.value

        setEditServiceData.value = previousServiceData
    }

    fun modifyDataFinished() {
        _modifyDataFinished.value = null
    }
}