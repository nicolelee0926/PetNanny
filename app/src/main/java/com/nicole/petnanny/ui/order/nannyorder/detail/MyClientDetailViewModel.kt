package com.nicole.petnanny.ui.order.nannyorder.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyClientDetailViewModel(private val repository: PetNannyRepository, private val argumentsMyClient: Order): ViewModel() {

    //  第一行給xml綁資料用 第二行將argus值指定給myClientDetail
    val myClientDetail: LiveData<Order>
        get() = myClientDetailArgus

    var myClientDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsMyClient
    }

    //  用來觀察NannyAcceptStatus欄位結果的liveData  (接受後 接受及拒絕的按鈕消失 跳出等待保姆付款)
    var liveAcceptStatusNanny = MutableLiveData<Boolean>()

    // 用來監聽存parentCheckoutCompleteStatus用
    var liveCheckoutCompleteStatusParent = MutableLiveData<Boolean>()

    //  用來觀察NannyCompleteServiceStatus欄位結果的liveData  (完成服務後 跳出完成服務的按鈕)
    var liveNannyCompleteServiceStatus = MutableLiveData<Boolean>()

    // 用來監聽存parentCheckServiceCompleteStatus用
    var liveCheckServiceCompleteStatus = MutableLiveData<Boolean>()

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

    init {
        getParentCheckServiceCompleteStatus()
    }

    //  update NannyAcceptStatus變true
    fun updateNannyAcceptStatus() {
        Log.d("updateNannyAcceptStatus", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateNannyAcceptStatus(myClientDetail.value?.orderID!!, this@MyClientDetailViewModel)) {
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

    //  監聽parentCheckoutCompleteStatus
    fun getMyClientParentCheckoutCompleteStatus() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMyClientParentCheckoutCompleteStatus(myClientDetail.value?.orderID!!)

            liveCheckoutCompleteStatusParent.value = when (result) {
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

    //  update NannyCompleteServiceStatus變true
    fun updateNannyCompleteServiceStatus() {
        Log.d("updateNannyCompleteServiceStatus", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateNannyCompleteServiceStatus(myClientDetail.value?.orderID!!, this@MyClientDetailViewModel)) {
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

    //  監聽parentCheckServiceCompleteStatus
    fun getParentCheckServiceCompleteStatus() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getParentCheckServiceCompleteStatus(myClientDetail.value?.orderID!!)

            liveCheckServiceCompleteStatus.value = when (result) {
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
