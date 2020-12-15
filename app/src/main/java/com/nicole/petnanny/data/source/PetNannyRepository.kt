package com.nicole.petnanny.data.source

import androidx.lifecycle.MutableLiveData
import com.nicole.petnanny.data.*
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel

interface PetNannyRepository {

    suspend fun addPet(pet: Pet): Result<Boolean>
    suspend fun getPets(): Result<List<Pet>>

    suspend fun addService(service: Nanny): Result<Boolean>
    suspend fun getServices(): Result<List<Nanny>>

    suspend fun updateUser(user: User): Result<Boolean>
    suspend fun getUser(userEmail: String): Result<User>

    suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean>

    suspend fun addUserToFirebase(user: User): Result<Boolean>

    suspend fun getServicesForHomePage(): Result<List<Nanny>>

    suspend fun getHomeServiceTypeFilter(serviceType: String): Result<List<Nanny>>

    suspend fun addDemand(demand: Order): Result<Boolean>

    suspend fun getMyOrderDataResult(): Result<List<Order>>

    suspend fun getMyClientDataResult(nannyEmail : String): Result<List<Order>>

    suspend fun updateNannyAcceptStatus(orderID: String, viewModel: MyClientDetailViewModel) : Result<Boolean>

    suspend fun getMyOrderNannyAcceptStatus(orderID: String) : Result<Boolean>

    suspend fun updateParentCheckoutCompleteStatus(orderID: String, viewModel: MyOrderDetailViewModel): Result<Boolean>

    suspend fun getMyClientParentCheckoutCompleteStatus(orderID: String): Result<Boolean>

    suspend fun updateNannyCompleteServiceStatus(orderID: String, viewModel: MyClientDetailViewModel): Result<Boolean>

    suspend fun getNannyServiceCompletedStatus(orderID: String): Result<Boolean>

    suspend fun updateParentCheckCompleteServiceStatus(orderID: String, viewModel: MyOrderDetailViewModel): Result<Boolean>

    suspend fun getParentCheckServiceCompleteStatus(orderID: String): Result<Boolean>

    suspend fun getDemandChatListResult(): Result<List<Order>>

    suspend fun addMessage(userEmails: String, message: Message): Result<Boolean>

    suspend fun getMessage(orderID: String?): Result<List<Message>>

    fun getLiveDemandOrders(): MutableLiveData<List<Order>>

    fun getLiveMessages(orderID: String?): MutableLiveData<List<Message>>
}