package com.nicole.petnanny.ui.profile.pet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.ui.login.UserManager

class PetViewModel(private val repository: PetNannyRepository):ViewModel() {

    private var _pet = MutableLiveData<List<Pet>>()
    val pet: LiveData<List<Pet>>
        get() = _pet

    // to petAdapter viewHolder button set value 用
    var _navigationToEditPetDetail = MutableLiveData<Pet>()
    val navigationToEditPetDetail: LiveData<Pet>
        get() = _navigationToEditPetDetail

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
//        if (PetNannyApplication.instance.isLiveDataDesign()) {
//            getLivePetsResult()
//        }
//        else{
            getPetsResult()
//        }
        getUserResult(UserManager.user.value?.userEmail)
    }


    fun getPetsResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getPets()

            _pet.value = when (result) {
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

        //    get user result save user verification to userManager
    fun getUserResult(userEmail: String?) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            userEmail?.let {
                val result = repository.getUser(it)
                Log.d("@@@@", "@@${result} ")

                UserManager.user.value = when (result) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        Log.d("@@@@", "@@${result.data} ")
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
                        _error.value =
                            PetNannyApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
                _refreshStatus.value = false
            }
        }
    }

    fun displayEditPetDetailsComplete () {
        _navigationToEditPetDetail.value = null
    }
}
