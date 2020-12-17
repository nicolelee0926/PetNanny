package com.nicole.petnanny.ui.chat.work.add

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

class WorkDetailViewModel(private val repository: PetNannyRepository, private val argumentsWork: Order): ViewModel() {

    //    第一行給xml綁資料用 第二行將argus值指定給DemandDetail
    val workDetail: LiveData<Order>
        get() = workDetailArgus

    var workDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsWork
    }

    // get message
    private var _workMessages = MutableLiveData<List<Message>>()
    val workMessages: LiveData<List<Message>>
        get() = _workMessages

    //  snapshot message
    var liveWorkMessages = MutableLiveData<List<Message>>()

    //  set Message
    var setWorkMessage = MutableLiveData<Message>()

    // EditText input
    val enterWorkMessage = MutableLiveData<String>()

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
        getWorkMessage()
        getLiveWorkMessagesResult()
    }

    fun getWorkMessage() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getWorkMessage(workDetail.value?.orderID)
            Log.d("xxxxxxx", result.toString())

            _workMessages.value = when (result) {
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


    fun addWorkMessage(setWorkMessage: Message) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addWorkMessage(workDetail.value?.orderID!!, setWorkMessage)) {
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


    fun setWorkMessage() {
        setWorkMessage.value = Message(
            content = enterWorkMessage.value.toString(),
            senderName = UserManager.user.value?.userName,
            messageTime = System.currentTimeMillis(),
            senderImage = UserManager.user.value?.photo!!,
            senderEmail = UserManager.user.value?.userEmail!!,
            id ="",
        )
    }

    fun getLiveWorkMessagesResult() {
        Log.d("rrrrrr", "${workDetail.value?.orderID} ")
        liveWorkMessages = repository.getLiveWorkMessages(workDetail.value?.orderID)
    }

    fun getLiveMessage() {
        _workMessages.value = liveWorkMessages.value
    }
}