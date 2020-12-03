package com.nicole.petnanny.data.source.local

import android.content.Context
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.Result
import com.nicole.petnanny.data.User
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

    override suspend fun addUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean> {
        TODO("Not yet implemented")
    }
}