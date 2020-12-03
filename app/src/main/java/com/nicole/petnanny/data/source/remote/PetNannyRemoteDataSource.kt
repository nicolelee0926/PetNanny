package com.nicole.petnanny.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.Pet
import com.nicole.petnanny.data.source.PetNannyDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.nicole.petnanny.data.Result

object PetNannyRemoteDataSource: PetNannyDataSource {

    private const val PATH_PET = "Pet"
    private const val PATH_NANNY = "Nanny"

    override suspend fun addPet(pet: Pet): Result<Boolean> = suspendCoroutine { continuation ->
        val Pet = FirebaseFirestore.getInstance().collection(PATH_PET)
        val document = Pet.document()

        pet.petID = document.id

        document
            .set(pet)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("addPet", "addPet: $pet")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d("add_pet_exception", "[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getPets(): Result<List<Pet>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_PET)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Pet>()
                    for (document in task.result!!) {
                        Log.d("result", "${document.id} => ${document.data}")

                        val pet = document.toObject(Pet::class.java)
                        list.add(pet)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Log.d("get_pet_exception", "[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addService(service: Nanny): Result<Boolean> = suspendCoroutine { continuation ->
        val Nanny = FirebaseFirestore.getInstance().collection(PATH_NANNY)
        val document = Nanny.document()

        service.nannyID = document.id

        document
            .set(service)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("addService", "addService: $service")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d("add_service_exception", "[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getServices(): Result<List<Nanny>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_NANNY)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Nanny>()
                    for (document in task.result!!) {
                        Log.d("result", "${document.id} => ${document.data}")

                        val service = document.toObject(Nanny::class.java)
                        list.add(service)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Log.d("get_service_exception", "[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


}