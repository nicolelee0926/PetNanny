package com.nicole.petnanny.ui.chat.demand.add

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

class DemandDetailViewModel(private val repository: PetNannyRepository, private val argumentsDemand: Order): ViewModel() {

    //    第一行給xml綁資料用 第二行將argus值指定給DemandDetail
    val demandDetail: LiveData<Order>
        get() = demandDetailArgus

    var demandDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsDemand
    }

    //  snapshot 某筆訂單
    var livaDemandOrderChatRoom = MutableLiveData<Order>()

    //  get message
    private var _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    //  snapshot message
    var livemessages = MutableLiveData<List<Message>>()

    //  set Message
    var setMessage = MutableLiveData<Message>()

    //  EditText input
    val enterMessage = MutableLiveData<String>()

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
        getMessage()
        getLiveMessagesResult()
        getLiveOneDemandOrderResult()
    }

    fun getMessage() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMessage(demandDetail.value?.orderID)
            Log.d("xxxxxxx", result.toString())

            _messages.value = when (result) {
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

    fun addMessage(setMessage: Message) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addMessage(demandDetail.value?.orderID!!, setMessage)) {
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


    fun setMessage() {
        setMessage.value = Message(
            content = enterMessage.value.toString(),
            senderName = UserManager.user.value?.userName,
            messageTime = System.currentTimeMillis(),
            senderImage = UserManager.user.value?.photo!!,
            senderEmail = UserManager.user.value?.userEmail!!,
            id =""
        )
    }

    fun getLiveMessagesResult() {
        Log.d("rrrrrr", "${demandDetail.value?.orderID} ")
        livemessages = repository.getLiveMessages(demandDetail.value?.orderID)
    }

    fun getLiveMessage() {
        _messages.value = livemessages.value
    }

    fun getLiveOneDemandOrderResult() {
        livaDemandOrderChatRoom = repository.getLiveOneDemandOrder(demandDetail.value?.orderID)
    }
}