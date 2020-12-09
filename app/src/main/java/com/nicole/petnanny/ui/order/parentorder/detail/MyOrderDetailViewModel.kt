package com.nicole.petnanny.ui.order.parentorder.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.petnanny.data.Order
import com.nicole.petnanny.data.source.PetNannyRepository

class MyOrderDetailViewModel(private val repository: PetNannyRepository, private val argumentsMyOrder: Order): ViewModel() {

//        第一行給xml綁資料用 第二行將argus值指定給myOrderDetail
    val myOrderDetail: LiveData<Order>
        get() = myOrderDetailArgus

    var myOrderDetailArgus = MutableLiveData<Order>().apply {
        value = argumentsMyOrder
    }
}