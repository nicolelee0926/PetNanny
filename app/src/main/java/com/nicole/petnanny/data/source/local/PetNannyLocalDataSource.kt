package com.nicole.petnanny.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.nicole.petnanny.data.*
import com.nicole.petnanny.data.source.PetNannyDataSource
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel

class PetNannyLocalDataSource(val context: Context): PetNannyDataSource {
    override suspend fun addPet(pet: Pet): Result<Boolean>{
        TODO("not implemented")
    }
    override suspend fun getPets(): Result<List<Pet>> {
        TODO("Not yet implemented")
    }


    override suspend fun addService(service: Nanny): Result<Boolean> {
        TODO("Not yet implemented")
    }
    override suspend fun getServices(): Result<List<Nanny>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userEmail: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun addNannyExamine(nannyExamine: NannyExamine): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUserToFirebase(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getServicesForHomePage(): Result<List<Nanny>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHomeServiceTypeFilter(serviceType: String): Result<List<Nanny>>{
        TODO("Not yet implemented")
    }

    override suspend fun addDemand(demand: Order): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyOrderDataResult(): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyClientDataResult(nannyEmail : String): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNannyAcceptStatus(orderID: String,viewModel: MyClientDetailViewModel): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyOrderNannyAcceptStatus(orderID: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateParentCheckoutCompleteStatus(
        orderID: String,
        viewModel: MyOrderDetailViewModel
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyClientParentCheckoutCompleteStatus(orderID: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNannyCompleteServiceStatus(
        orderID: String,
        viewModel: MyClientDetailViewModel
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getNannyServiceCompletedStatus(orderID: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateParentCheckCompleteServiceStatus(
        orderID: String,
        viewModel: MyOrderDetailViewModel
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getParentCheckServiceCompleteStatus(orderID: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getDemandChatListResult(): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun addMessage(userEmails: String, message: Message): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessage(orderID: String?): Result<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun getLiveDemandOrders(): MutableLiveData<List<Order>> {
        TODO("Not yet implemented")
    }

    override fun getLiveMessages(orderID: String?): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWorkChatListResult(nannyEmail: String): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWorkMessage(orderID: String?): Result<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun addWorkMessage(orderID: String, workMessage: Message): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getLiveWorkMessages(orderID: String?): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override fun getLiveWorkOrders(): MutableLiveData<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadPetPhoto(petPhotoLocalPath: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadServicePhoto(servicePhotoLocalPath: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPetsResult(): Result<List<Pet>> {
        TODO("Not yet implemented")
    }

    override suspend fun getThreeSelectedList(
        serviceType: String,
        petType: String,
        location: String
    ): Result<List<Nanny>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePet(pet: Pet): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateService(service: Nanny): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadEditPetPhoto(editPetPhotoLocalPath: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadEditServicePhoto(editServicePhotoLocalPath: String): Result<String> {
        TODO("Not yet implemented")
    }


}