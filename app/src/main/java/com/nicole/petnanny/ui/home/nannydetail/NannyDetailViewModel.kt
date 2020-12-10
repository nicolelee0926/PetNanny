package com.nicole.petnanny.ui.home.nannydetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NannyDetailViewModel(
    private val repository: PetNannyRepository,
    private val arguments: Nanny
) : ViewModel() {

    //    第一行給xml綁資料用 第二行將argus值指定給nannyDetail
    val nannyDetail: LiveData<Nanny>
        get() = nannyDetailArgus

//    //get userName用
//    private var _user = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _user

    var nannyDetailArgus = MutableLiveData<Nanny>().apply {
        value = arguments
    }

    //    給按聯繫保姆按鈕後到demand頁面用的live data
    private val _navigateToDemandNannyData = MutableLiveData<Nanny>()
    val navigateToDemandNannyData: LiveData<Nanny>
        get() = _navigateToDemandNannyData

//    private val _navigateToDemandUserData = MutableLiveData<User>()
//    val navigateToDemandUserData: LiveData<User>
//        get() = _navigateToDemandUserData

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
//        UserManager.user.value?.userEmail?.let {
////            getUserResult(it)
//        }

//        一開始先賦值給要帶過去的data
        _navigateToDemandNannyData.value = nannyDetailArgus.value
        Log.d("&&&&&&&&&", "${_navigateToDemandNannyData.value}")
    }

//    //get userName用
//    fun getUserResult(userEmail: String) {
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getUser(userEmail)
//            Log.d("@@@@", "@@${result} ")
//
//            _user.value = when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    Log.d("@@@@", "@@${result.data} ")
//                    result.data
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = PetNannyApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//            _refreshStatus.value = false
//        }
//    }

}