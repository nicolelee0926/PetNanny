package com.nicole.petnanny.data.source

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.nicole.petnanny.data.*

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


}