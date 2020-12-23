package com.nicole.petnanny.ui.profile.pet.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.ui.login.UserManager

class AddPetViewModel( private val repository: PetNannyRepository): ViewModel() {


    val setPetData = MutableLiveData<Pet>()

    var petName  = MutableLiveData<String>().apply { value = "" }
    var petIntroduction = MutableLiveData<String>().apply { value = "" }
    var petVariety = MutableLiveData<String>().apply { value = "" }
    var petChipNumber = MutableLiveData<String>().apply { value = "" }
    var selectedGender = MutableLiveData<String>().apply { value = "" }
    var selectedLigation = MutableLiveData<String>().apply { value = "" }
    var selectedType  = MutableLiveData<String>().apply { value = "" }
    var selectedAge = MutableLiveData<String>().apply { value = "" }
    //    firebase photo local path
    val petPhotoRealPath = MutableLiveData<String>()

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


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _submitDataFinished.value = null
    }

    fun addPet(pet: Pet) {
        Log.d("addPet", "hate")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addPet(pet)) {
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


    fun setPet() {
        setPetData.value =Pet(
                petName = petName.value.toString(),
                petIntroduction = petIntroduction.value.toString(),
                petVariety = petVariety.value.toString(),
                chipNumber = petChipNumber.value.toString(),
                gender = selectedGender.value.toString(),
                petLigation = selectedLigation.value.toString(),
                petType = selectedType.value.toString(),
                petAge = selectedAge.value.toString(),
                userEmail = UserManager.user.value?.userEmail,
                petPhoto = petPhotoRealPath.value.toString(),
                createTime = System.currentTimeMillis()
        )
    }

    fun setGender(gender: String) {
        selectedGender.value = gender
    }

    fun setLigation(ligation: String) {
        selectedLigation.value = ligation
    }

//    upload petPhoto
    fun uploadPetPhoto(petPhotoLocalPath: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val result = repository.uploadPetPhoto(petPhotoLocalPath)

            when (result) {
                is Result.Success-> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    petPhotoRealPath.value = result.data
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

//    check info completed
    fun checkInfoComplete(): Boolean {
        return !(petPhotoRealPath.value == null ||
                 petName.value == null ||
                 selectedAge.value == null ||
                 selectedGender.value == null ||
                 petVariety.value == null ||
                 petIntroduction.value == null ||
                 petChipNumber.value == null ||
                 selectedLigation.value == null ||
                 selectedType.value == null)
    }

    fun submitToFireStoreFinished() {
        _submitDataFinished.value = null
    }
}


