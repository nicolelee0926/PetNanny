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

    //    第一行給xml綁資料用 第二行將argus值指定給myClientDetail
    val myClientDetail: LiveData<Order>
        get() = myClientDetailArgus

    var myClientDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsMyClient
    }

//  用來觀察NannyAcceptStatus欄位結果的liveData  (接受後 接受及拒絕的按鈕消失 跳出等待保姆付款)
    var liveAcceptStatusNanny = MutableLiveData<Boolean>()

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



//    update NannyAcceptStatus變true
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


}