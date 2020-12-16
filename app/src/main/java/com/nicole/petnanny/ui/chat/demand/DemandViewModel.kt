package com.nicole.petnanny.ui.chat.demand

import android.util.Log
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

class DemandViewModel(private val repository: PetNannyRepository): ViewModel() {

    var demandOrderChatRoomList = MutableLiveData<List<Order>>()


//    to demandAdapter viewHolder button set value 用
    var _navigationToDemandChatRoomDetail = MutableLiveData<Order>()
    val navigationToDemandChatRoomDetail: LiveData<Order>
        get() = _navigationToDemandChatRoomDetail

//    snapshot
    var liveDemandOrderChatRoomList = MutableLiveData<List<Order>>()

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
        getDemandOrderChatRoomListResult()
        getUserResult(UserManager.user.value?.userEmail)


    }

    fun getDemandOrderChatRoomListResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getDemandChatListResult()

            demandOrderChatRoomList.value = when (result) {
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

                //  因為登入後認證欄位還是null 所以要在這邊再存回UserManager一次 認證狀態才會被儲存 這時再執行snapshot時就有認證狀態了
                //  才不會因為還沒存入狀態前就snapshot了
                if (UserManager.user.value?.verification == null) {
                    getLiveDemandOrdersResult()
                }

                _refreshStatus.value = false
            }
        }
    }


    fun displayChatRoomDetailComplete() {
        _navigationToDemandChatRoomDetail.value = null
    }

    fun getLiveDemandOrdersResult() {
        liveDemandOrderChatRoomList = repository.getLiveDemandOrders()
    }

    fun getLiveDemandOrder() {
        demandOrderChatRoomList.value = liveDemandOrderChatRoomList.value
    }

}