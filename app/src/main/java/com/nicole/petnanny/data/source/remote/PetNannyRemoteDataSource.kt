package com.nicole.petnanny.data.source.remote

import android.util.Log
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FirebaseFirestore
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.*
import com.nicole.petnanny.data.source.PetNannyDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.nicole.petnanny.ui.login.UserManager

object PetNannyRemoteDataSource : PetNannyDataSource {

    private const val KEY_CREATED_TIME = "createdTime"
    private const val PATH_PET = "Pet"
    private const val PATH_NANNY = "Nanny"
    private const val PATH_USER = "User"
    private const val PATH_ORDER = "Order"

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

                        Log.d(
                            "add_pet_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getPets(): Result<List<Pet>> = suspendCoroutine { continuation ->
        Log.d("userEmail", UserManager.user.value?.userEmail!!)
        FirebaseFirestore.getInstance()
            .collection(PATH_PET)
            .whereEqualTo("userEmail", UserManager.user.value?.userEmail)
//            .orderBy("createTime",Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("userEmail2", "$task.result ")
                    val list = mutableListOf<Pet>()
                    for (document in task.result!!) {
                        Log.d("result", "${document.id} => ${document.data}")

                        val pet = document.toObject(Pet::class.java)
                        list.add(pet)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    Log.d("userEmail3", "${task.result} ")
                    task.exception?.let {

                        Log.d(
                            "get_pet_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


    override suspend fun addService(service: Nanny): Result<Boolean> =
        suspendCoroutine { continuation ->
            val Nanny = FirebaseFirestore.getInstance().collection(PATH_NANNY)
            val document = Nanny.document()

            document
                .set(service)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addService", "addService: $service")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "add_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
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
            .whereEqualTo("userEmail", UserManager.user.value?.userEmail)
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

                        Log.d(
                            "get_service_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun updateUser(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance().collection(PATH_USER)
                .document("${UserManager.user.value?.userEmail}")
                    .update("selfIntroduction", user.selfIntroduction,
                            "userName", user.userName)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addUser123", "addUser: $user")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "add_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getUser(userEmail: String): Result<User> =
        suspendCoroutine { continuation ->
//            Log.d("1234", "${UserManager.user.value?.userEmail}")
            FirebaseFirestore.getInstance()
                .collection(PATH_USER)
                .document(UserManager.user.value?.userEmail ?: "")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        Log.d("~~~~", "${task.result?.data} ")
                        task.result?.data.let {
                            Log.d("123", "${task.result}")
                            val user: User = task.result?.toObject(User::class.java)!!
                            val res = Result.Success(user)
                            continuation.resume(res)
                        }
                    } else {
                        task.exception?.let {
                            Log.d(
                                "get_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }


    override suspend fun addNannyExamine(nannyExamine: Nanny): Result<Boolean> =
        suspendCoroutine { continuation ->
            val Nanny = FirebaseFirestore.getInstance().collection(PATH_NANNY)
            val document = Nanny.document()

            document
                .set(nannyExamine)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addNannyExamine", "addNannyExamine: $nannyExamine")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "add_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addUserToFirebase(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->
            var userCollection = FirebaseFirestore.getInstance().collection(PATH_USER)
            var document = userCollection.document(user.userEmail!!)
//            user.userEmail = document.id

            userCollection.whereEqualTo("userEmail", user.userEmail)
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        document.set(user).
                            addOnSuccessListener {
                                Log.d("DocumentSnapshot", "add with userEmail: $userCollection")
                        }
                            .addOnFailureListener { e ->
                                Log.d("DocumentSnapshot", "Error adding document $e")
                            }
                    } else {
                        for (myDocument in result) {
                            Log.d("Already initialized", "$result")
                        }
                    }
                }
        }

    override suspend fun getServicesForHomePage(): Result<List<Nanny>> =
        suspendCoroutine { continuation ->
            Log.d("dddddd", "kkfdfsdfdsffffff")
            FirebaseFirestore.getInstance()
                .collection(PATH_NANNY)
                .limit(5)
                .get()
                .addOnCompleteListener { task ->
                    Log.d("dddddd", "$task")
                    if (task.isSuccessful) {
                        val list = mutableListOf<Nanny>()
                        for ((index, document) in task.result!!.withIndex()) {
                            val service = document.toObject(Nanny::class.java)

                            list.add(service)

                            Log.d("result", "${document.id} => ${document.data}")
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "get_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getHomeServiceTypeFilter(serviceType: String): Result<List<Nanny>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_NANNY)
                .whereEqualTo("serviceType", serviceType)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("cccccccc", "$task.result ")
                        val list = mutableListOf<Nanny>()
                        for (document in task.result!!) {
                            Log.d("aaaaaaaaaaaaa", "${document.id}")

                            val nanny = document.toObject(Nanny::class.java)
                            list.add(nanny)
                            Log.d("ddddddd", "$list ")
                        }
                        continuation.resume(Result.Success(list))
                        Log.d("bbbbbbb", "$task.result ")
                    } else {
                        task.exception?.let {

                            Log.d(
                                "get_service_exception",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addDemand(demand: Order): Result<Boolean> = suspendCoroutine { continuation ->
        val orderCollection = FirebaseFirestore.getInstance().collection(PATH_ORDER)
        val document = orderCollection.document()

        demand.orderID = document.id

        document
            .set(demand)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("addDemand", "addDemand: $demand")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d(
                            "add_service_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getMyOrderDataResult(): Result<List<Order>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .whereEqualTo("userEmail", UserManager.user.value?.userEmail)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Order>()
                    for (document in task.result!!) {
                        Log.d("resultMyOrder", "${document.id} => ${document.data}")

                        val myOrder = document.toObject(Order::class.java)
                        list.add(myOrder)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Log.d(
                            "get_service_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getMyClientDataResult(nannyEmail : String): Result<List<Order>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .whereEqualTo("nannyEmail", UserManager.user.value?.userEmail)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Order>()
                    for (document in task.result!!) {
                        Log.d("resultMyClient", "${document.id} => ${document.data}")

                        val myClient = document.toObject(Order::class.java)
                        list.add(myClient)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Log.d(
                            "get_service_exception",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PetNannyApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


}