package com.nicole.petnanny.ui.profile.pet.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.network.LoadApiStatus
import com.nicole.petnanny.ui.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditPetViewModel(private val repository: PetNannyRepository, private val arguments: Pet): ViewModel() {

    //    第一行給xml綁資料用 第二行將argus值指定給petDetail
    val petDetail: LiveData<Pet>
        get() = petDetailArgus

    var petDetailArgus = MutableLiveData<Pet>().apply {
        value = arguments
    }

//    edit pet data 修該過後的整包pet
    val setEditPetData = MutableLiveData<Pet>()

    var editPetName  = MutableLiveData<String>().apply { value = "" }
    var editPetIntroduction = MutableLiveData<String>().apply { value = "" }
    var editPetVariety = MutableLiveData<String>().apply { value = "" }
    var editPetChipNumber = MutableLiveData<String>().apply { value = "" }
    var editSelectedGender = MutableLiveData<String>().apply { value = "" }
    var editSelectedLigation = MutableLiveData<String>().apply { value = "" }
    var editSelectedType  = MutableLiveData<String>().apply { value = "" }
    var editSelectedAge = MutableLiveData<String>().apply { value = "" }
    //    firebase photo local path
    val editPetPhotoRealPath = MutableLiveData<String>()

    //  修改成功flag
    private val _modifyDataFinished = MutableLiveData<Boolean>()
    val modifyDataFinished: LiveData<Boolean>
        get() = _modifyDataFinished

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
        _modifyDataFinished.value = null
    }

    fun updatePet(pet: Pet) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updatePet(pet)) {
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
        _modifyDataFinished.value = true
    }

    fun setEditPet() {
//        把之前帶過來的data先存起來
        val previousPetData = petDetail.value
//        再把新修改過的存到剛剛的liveData
        previousPetData?.petName = editPetName.value
        previousPetData?.petType = editSelectedType.value
        previousPetData?.petVariety = editPetVariety.value
        previousPetData?.gender = editSelectedGender.value
        previousPetData?.petAge = editSelectedAge.value
        previousPetData?.petIntroduction = editPetIntroduction.value
        previousPetData?.petLigation = editSelectedLigation.value
        previousPetData?.chipNumber = editPetChipNumber.value
        previousPetData?.petPhoto = editPetPhotoRealPath.value

        setEditPetData.value = previousPetData
    }

    fun setEditGender(gender: String) {
        editSelectedGender.value = gender
    }

    fun setEditLigation(ligation: String) {
        editSelectedLigation.value = ligation
    }

    fun modifyDataFinished() {
        _modifyDataFinished.value = null
    }

    //    upload editPetPhoto
    fun uploadEditPetPhoto(editPetPhotoLocalPath: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val result = repository.uploadEditPetPhoto(editPetPhotoLocalPath)

            when (result) {
                is Result.Success-> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    editPetPhotoRealPath.value = result.data
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