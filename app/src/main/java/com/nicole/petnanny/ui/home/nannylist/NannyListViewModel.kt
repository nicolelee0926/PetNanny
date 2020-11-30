package com.nicole.petnanny.ui.home.nannylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.model.Nanny

class NannyListViewModel: ViewModel() {
    private val _getFakeNannyListData = MutableLiveData<List<Nanny>>()
    val getFakeNannyListData : LiveData<List<Nanny>>
        get() = _getFakeNannyListData

    var navigationToNannyDetail = MutableLiveData<Boolean>()


    init {
        getFakeNannyData()
        navigationToNannyDetail.value = null
    }

    private fun getFakeNannyData() {
        val nannyListData = mutableListOf(
            Nanny(
                serviceArea = "中山區",
                serviceName = "小豬豬美容師專業洗澡",
                license = "美容師執照",
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴",
                price = 1500),
            Nanny(
                serviceArea = "中山區",
                serviceName = "小豬豬美容師專業洗澡",
                license = "美容師執照",
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴",
                price = 1500),
            Nanny(
                serviceArea = "中山區",
                serviceName = "小豬豬美容師專業洗澡",
                license = "美容師執照",
                nannyIntroduction = "洗澡好放心，免費附送洗健康藥浴",
                price = 1500)
        )
        _getFakeNannyListData.value = nannyListData
    }

    fun displayNannyDetailsComplete () {
        navigationToNannyDetail.value = null
    }


}