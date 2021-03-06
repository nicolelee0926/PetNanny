package com.nicole.petnanny.ui.profile.nanny

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.NannyExamine
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NannyExamineViewModel(private val repository: PetNannyRepository) : ViewModel() {

    val setNannyExamineData = MutableLiveData<NannyExamine>()

    var nannyBirthday = MutableLiveData<String>().apply { value = "" }
    var nannyName = MutableLiveData<String>().apply { value = "" }
    var nannyPhone = MutableLiveData<String>().apply { value = "" }
    var nannyIDNumber = MutableLiveData<String>().apply { value = "" }
    var nannyPetExperience = MutableLiveData<String>().apply { value = "" }

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

    fun addNannyExamine(nannyExamine: NannyExamine) {
        Log.d("addNannyExamine", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addNannyExamine(nannyExamine)) {
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setNannyExamine() {
        setNannyExamineData.value = NannyExamine(
            nannyBirthday = nannyBirthday.value.toString(),
            nannyPhone = nannyPhone.value.toString(),
            nannyIDNumber = nannyIDNumber.value.toString(),
            nannyPetExperience = nannyPetExperience.value.toString(),
            nannyRealName = nannyName.value.toString(),
            userEmail = UserManager.user.value?.userEmail
        )
    }

    //    check info completed
    fun checkInfoComplete(): Boolean {
        return (nannyBirthday.value != "" &&
                nannyPhone.value != "" &&
                nannyIDNumber.value != "" &&
                nannyPetExperience.value != "" &&
                nannyName.value != "")
    }
}