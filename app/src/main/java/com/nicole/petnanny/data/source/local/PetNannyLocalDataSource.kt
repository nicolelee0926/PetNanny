package com.nicole.petnanny.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.nicole.petnanny.data.*
import com.nicole.petnanny.data.source.PetNannyDataSource

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

    override suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean> {
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

}