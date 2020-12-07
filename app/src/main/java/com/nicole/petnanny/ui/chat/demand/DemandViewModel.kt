package com.nicole.petnanny.ui.chat.demand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Message
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DemandViewModel(private val repository: PetNannyRepository): ViewModel() {

    private var _chatList = MutableLiveData<List<Message>>()
    val chatList: LiveData<List<Message>>
        get() = _chatList

    var navigationToChatRoomDetail = MutableLiveData<Boolean>()


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
        getFakeChatListData()

    }

    private fun getFakeChatListData() {
        val user1 = User(
               userName = "美惠姐")

//        val serviceType = Nanny(
//                serviceType = "到府美容")

        val chatRoom = mutableListOf(
            Message(
                receiver = user1,
                content = "明天下午可以過去嗎",
                )
        )
        _chatList.value = chatRoom
    }

    fun displayChatRoomDetailComplete() {
        navigationToChatRoomDetail.value = null
    }

}