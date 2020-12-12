package com.nicole.petnanny.ui.order.parentorder.detail

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

class MyOrderDetailViewModel(
    private val repository: PetNannyRepository,
    private val argumentsMyOrder: Order
) : ViewModel() {

    //        第一行給xml綁資料用 第二行將argus值指定給myOrderDetail
    val myOrderDetail: LiveData<Order>
        get() = myOrderDetailArgus

    var myOrderDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsMyOrder
    }

    //    存nannyAcceptStatus用
    var liveAcceptStatusNanny = MutableLiveData<Boolean>()

//    用來觀察ParentCheckoutStatus欄位結果的liveData  (完成付款後 按鈕消失 出現付款完成的字)
    var liveCheckoutStatusParent = MutableLiveData<Boolean>()


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
        getMyOrderNannyAcceptStatus()
    }

    //    get nannyAcceptStatus
    fun getMyOrderNannyAcceptStatus() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMyOrderNannyAcceptStatus(myOrderDetail.value?.orderID!!)

            liveAcceptStatusNanny.value = when (result) {
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

    //    update ParentCheckoutCompleteStatus變true
    fun updateParentCheckoutCompleteStatus() {
        Log.d("updateNannyAcceptStatus", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateParentCheckoutCompleteStatus(myOrderDetail.value?.orderID!!, this@MyOrderDetailViewModel)) {
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


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
