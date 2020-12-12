package com.nicole.petnanny.ui.order.parentorder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyOrderViewModel(private val repository: PetNannyRepository) : ViewModel() {

    private val _myOrderList = MutableLiveData<List<Order>>()
    val myOrderList: LiveData<List<Order>>
        get() = _myOrderList

    // to myOrderAdapter viewHolder button set value 用
    val _navigationToMyOrderDetail = MutableLiveData<Order>()
    val navigationToMyOrderDetail: LiveData<Order>
        get() = _navigationToMyOrderDetail

//    //  監聽NannyAcceptStatus欄位結果的liveData (跳出等待您的付款 及 付款完成按鈕)
//    var liveAcceptStatusParent = MutableLiveData<List<Order>>()

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
        getMyOrderDataResult()
        _navigationToMyOrderDetail.value = null
        getUserResult(UserManager.user.value?.userEmail)
    }

    fun getMyOrderDataResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMyOrderDataResult()

            _myOrderList.value = when (result) {
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

    //    get user data to save userManager for load myClient order (這時get下來是用存是否認證的欄位資料, 存在userManager, 方便給my client去query)
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




    fun displayMyOrderDetailsComplete() {
        _navigationToMyOrderDetail.value = null
    }


}