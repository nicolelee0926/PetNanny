package com.nicole.petnanny.ui.profile.user


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AddUserViewModel(private val repository: PetNannyRepository): ViewModel() {


    val setUserData = MutableLiveData<User>()

    //Get user data for profile
    private var _personalInfo = MutableLiveData<User>()
    val personalInfo: LiveData<User>
        get() = _personalInfo

    var userIntroduction = MutableLiveData<String>().apply { value = "" }
    var userName = MutableLiveData<String>().apply { value = "" }

    //  傳送成功flag
    private val _submitDataFinished = MutableLiveData<Boolean>()
    val submitDataFinished: LiveData<Boolean>
        get() = _submitDataFinished

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

    init {
        _submitDataFinished.value = null
    }

    fun updateUser(user: User) {
        Log.d("addUser", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateUser(user)) {
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
        _submitDataFinished.value = true
    }

    init {
        UserManager.user.value?.userEmail?.let {
            getUserInfo(it)
        }
    }

//    一進來時要先get之前的user info
    private fun getUserInfo(userEmail: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUser(userEmail)

            _personalInfo.value = when (result) {
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
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setUser() {
        setUserData.value = User(
            userName = userName.value.toString(),
            selfIntroduction = userIntroduction.value.toString(),
            userEmail = UserManager.user.value?.userEmail,
            photo = UserManager.user.value?.photo.toString()
        )
    }

    fun submitToFireStoreFinished() {
        _submitDataFinished.value = null
    }
}