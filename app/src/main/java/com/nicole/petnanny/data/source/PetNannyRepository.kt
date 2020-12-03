package com.nicole.petnanny.data.source

import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result

interface PetNannyRepository {

    suspend fun addPet(pet: Pet): Result<Boolean>
    suspend fun getPets(): Result<List<Pet>>

    suspend fun addService(service: Nanny): Result<Boolean>
    suspend fun getServices(): Result<List<Nanny>>


}