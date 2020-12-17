package com.nicole.petnanny.ui.chat.work

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: PetNannyRepository): ViewModel() {

    var workOrderChatRoomList = MutableLiveData<List<Order>>()

    //    to workAdapter viewHolder button set value 用
    var _navigationToWorkChatRoomDetail = MutableLiveData<Order>()
    val navigationToWorkChatRoomDetail: LiveData<Order>
        get() = _navigationToWorkChatRoomDetail

    //    snapshot
    var liveWorkOrderChatRoomList = MutableLiveData<List<Order>>()

    //    setFirstMessage
    var getFirstMessage = MutableLiveData<Message>()

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
        if(UserManager.user.value?.verification == true) {
//          要傳入自己的email(因為要query自己是保姆的訂單)
            UserManager.user.value?.userEmail?.let {
                getWorkOrderChatRoomListResult(it)
                getLiveWorkOrdersResult()
            }
        }
    }

    //    get workOrderChatRoom時 去query自己的userEmail(存再Order欄位裡的nanny email)
    fun getWorkOrderChatRoomListResult(nannyEmail : String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getWorkChatListResult(nannyEmail)

            workOrderChatRoomList.value = when (result) {
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


    fun displayChatRoomDetailComplete() {
        _navigationToWorkChatRoomDetail.value = null
    }

    fun getLiveWorkOrdersResult() {
        liveWorkOrderChatRoomList = repository.getLiveWorkOrders()
    }

    fun getLiveWorkOrder() {
        workOrderChatRoomList.value = liveWorkOrderChatRoomList.value
    }
}