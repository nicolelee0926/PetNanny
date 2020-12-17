package com.nicole.petnanny.data.source

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.nicole.petnanny.data.*
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel

class DefaultPetNannyRepository(private val remoteDataSource: PetNannyDataSource,
                                private val localDataSource: PetNannyDataSource
) : PetNannyRepository  {

    override suspend fun addPet(pet: Pet): Result<Boolean> {
        return remoteDataSource.addPet(pet)
    }
    override suspend fun getPets(): Result<List<Pet>> {
        return remoteDataSource.getPets()
    }


    override suspend fun addService(service: Nanny): Result<Boolean> {
        return remoteDataSource.addService(service)
    }
    override suspend fun getServices(): Result<List<Nanny>> {
        return remoteDataSource.getServices()
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        return remoteDataSource.updateUser(user)
    }

    override suspend fun getUser(userEmail: String): Result<User> {
        return remoteDataSource.getUser(userEmail)
    }

    override suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean> {
        return remoteDataSource.addNannyExamine(nannyExamine)
    }

    override suspend fun addUserToFirebase(user: User): Result<Boolean> {
        return remoteDataSource.addUserToFirebase(user)
    }

    override suspend fun getServicesForHomePage(): Result<List<Nanny>> {
        return remoteDataSource.getServicesForHomePage()
    }

    override suspend fun getHomeServiceTypeFilter(serviceType: String): Result<List<Nanny>> {
        return remoteDataSource.getHomeServiceTypeFilter(serviceType)
    }

    override suspend fun addDemand(demand: Order): Result<Boolean> {
        return remoteDataSource.addDemand(demand)
    }

    override suspend fun getMyOrderDataResult(): Result<List<Order>> {
        return remoteDataSource.getMyOrderDataResult()
    }

    override suspend fun getMyClientDataResult(nannyEmail : String): Result<List<Order>> {
        return remoteDataSource.getMyClientDataResult(nannyEmail)
    }

    override suspend fun updateNannyAcceptStatus(orderID: String, viewModel: MyClientDetailViewModel): Result<Boolean> {
        return remoteDataSource.updateNannyAcceptStatus(orderID,viewModel)
    }

    override suspend fun getMyOrderNannyAcceptStatus(orderID: String) : Result<Boolean> {
        return remoteDataSource.getMyOrderNannyAcceptStatus(orderID)
    }

    override suspend fun updateParentCheckoutCompleteStatus(orderID: String, viewModel: MyOrderDetailViewModel): Result<Boolean> {
        return remoteDataSource.updateParentCheckoutCompleteStatus(orderID,viewModel)
    }

    override suspend fun getMyClientParentCheckoutCompleteStatus(orderID: String): Result<Boolean> {
        return remoteDataSource.getMyClientParentCheckoutCompleteStatus(orderID)
    }

    override suspend fun updateNannyCompleteServiceStatus(orderID: String, viewModel: MyClientDetailViewModel): Result<Boolean> {
        return remoteDataSource.updateNannyCompleteServiceStatus(orderID, viewModel)
    }

    override suspend fun getNannyServiceCompletedStatus(orderID: String): Result<Boolean> {
        return remoteDataSource.getNannyServiceCompletedStatus(orderID)
    }

    override suspend fun updateParentCheckCompleteServiceStatus(orderID: String, viewModel: MyOrderDetailViewModel): Result<Boolean> {
        return remoteDataSource.updateParentCheckCompleteServiceStatus(orderID, viewModel)
    }

    override suspend fun getParentCheckServiceCompleteStatus(orderID: String): Result<Boolean> {
        return remoteDataSource.getParentCheckServiceCompleteStatus(orderID)
    }

    override suspend fun getDemandChatListResult(): Result<List<Order>> {
        return remoteDataSource.getDemandChatListResult()
    }

    override suspend fun addMessage(userEmails: String, message: Message): Result<Boolean> {
        return remoteDataSource.addMessage(userEmails, message)
    }

    override suspend fun getMessage(orderID: String?): Result<List<Message>> {
        return remoteDataSource.getMessage(orderID)
    }

    override fun getLiveDemandOrders(): MutableLiveData<List<Order>> {
        return remoteDataSource.getLiveDemandOrders()
    }

    override fun getLiveMessages(orderID: String?): MutableLiveData<List<Message>> {
        return remoteDataSource.getLiveMessages(orderID)
    }

    override suspend fun getWorkChatListResult(nannyEmail: String): Result<List<Order>> {
        return remoteDataSource.getWorkChatListResult(nannyEmail)
    }

    override suspend fun getWorkMessage(orderID: String?): Result<List<Message>> {
        return remoteDataSource.getWorkMessage(orderID)
    }

    override suspend fun addWorkMessage(orderID: String, workMessage: Message): Result<Boolean> {
        return remoteDataSource.addWorkMessage(orderID, workMessage)
    }

    override fun getLiveWorkMessages(orderID: String?): MutableLiveData<List<Message>> {
        return remoteDataSource.getLiveWorkMessages(orderID)
    }

    override fun getLiveWorkOrders(): MutableLiveData<List<Order>> {
        return remoteDataSource.getLiveWorkOrders()
    }

    override suspend fun uploadPetPhoto(petPhotoLocalPath: String): Result<String> {
        return remoteDataSource.uploadPetPhoto(petPhotoLocalPath)
    }
}