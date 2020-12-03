package com.nicole.petnanny.data.source

import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User

interface PetNannyRepository {

    suspend fun addPet(pet: Pet): Result<Boolean>
    suspend fun getPets(): Result<List<Pet>>

    suspend fun addService(service: Nanny): Result<Boolean>
    suspend fun getServices(): Result<List<Nanny>>

    suspend fun addUser(user: User): Result<Boolean>
    suspend fun getUser(): Result<User>

    suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean>


}