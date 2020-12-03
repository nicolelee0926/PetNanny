package com.nicole.petnanny.data.source

import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User

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

    override suspend fun addUser(user: User): Result<Boolean> {
        return remoteDataSource.addUser(user)
    }

    override suspend fun getUser(): Result<User> {
        return remoteDataSource.getUser()
    }

    override suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean> {
        return remoteDataSource.addNannyExamine(nannyExamine)
    }


}