package com.nicole.petnanny.ui.home.senddemand

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.*
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SendDemandViewModel(
    private val repository: PetNannyRepository,
    private val arguments: Nanny
) : ViewModel() {

    val setDemandData = MutableLiveData<Order>()

    //    第一行給xml綁資料用 第二行將argus值指定給nannyDetail
    val nannyData: LiveData<Nanny>
        get() = nannyDataArgus


    //  接收 nanny detail 的資料
    var nannyDataArgus = MutableLiveData<Nanny>().apply {
        value = arguments
    }

    var orderStartTime = MutableLiveData<String>()
    var orderEndTime = MutableLiveData<String>()
    var orderServiceAddress = MutableLiveData<String>().apply { value = "" }
    var orderNote = MutableLiveData<String>().apply { value = "" }
    var demandDay = MutableLiveData<String>()
    var totalPrice = MutableLiveData<String>()

    //    用liveData存UserManager下單者的資料進去
    var userInfo = MutableLiveData<User>()
        get() = UserManager.user


    var _userPetList = MutableLiveData<List<Pet>>()  //給dialog存list用
    val userPetList: LiveData<List<Pet>>
        get() = _userPetList

//    存被選到的pet
    var selectedPet = MutableLiveData<Pet>()


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
        getUserPetsResult()
    }

    fun addDemand(demand: Order) {
        Log.d("addPet", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addDemand(demand)) {
                is Result.Success -> {
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

    //    get下來user的所有pet 送訂單時選擇寵物帶入
    fun getUserPetsResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUserPetsResult()

            _userPetList.value = when (result) {
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

    fun sendDemand() {
        setDemandData.value = Order(
            orderStartTime = orderStartTime.value.toString(),
            orderEndTime = orderEndTime.value.toString(),
            address = orderServiceAddress.value.toString(),
            note = orderNote.value.toString(),
            userEmail = UserManager.user.value?.userEmail,
            nannyServiceDetail = nannyDataArgus.value,
//            nannyEmail: 存保姆的email(如果是保姆身份 申請服務時多存一個自己的email在nannyEmail 到時訂單query用)
            nannyEmail = nannyDataArgus.value?.userEmail,
            demandDay = demandDay.value.toString(),
            totalPrice = totalPrice.value.toString(),
            userInfo = userInfo.value,
            createTime = System.currentTimeMillis(),
            selectedPet = selectedPet.value
            )
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //    check info completed
    fun checkInfoComplete(): Boolean {
        return (_userPetList.value != null &&
                orderStartTime.value != "" &&
                orderEndTime.value != "" &&
                orderServiceAddress.value != "" &&
                orderNote.value != "")
    }

}