package com.nicole.petnanny.data.source

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.nicole.petnanny.data.*

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


}