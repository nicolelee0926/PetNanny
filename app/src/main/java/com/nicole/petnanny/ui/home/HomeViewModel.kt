package com.nicole.petnanny.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Nanny

class HomeViewModel : ViewModel() {

    private val _getFakeNannyData = MutableLiveData<List<Nanny>>()
    val getFakeNannyData : LiveData<List<Nanny>>
        get() = _getFakeNannyData

    init {
        getFakeNannyData()
    }

    private fun getFakeNannyData() {
        val nannyData = mutableListOf(
            Nanny(serviceName = "胖小喵的窩",
                price = 1000,
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴"),
            Nanny(serviceName = "胖小喵的窩",
                price = 1000,
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴"),
            Nanny(serviceName = "胖小喵的窩",
                price = 1000,
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴")
        )
        _getFakeNannyData.value = nannyData
    }
}