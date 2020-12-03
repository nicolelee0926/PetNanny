package com.nicole.petnanny.data.source

import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result

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


}