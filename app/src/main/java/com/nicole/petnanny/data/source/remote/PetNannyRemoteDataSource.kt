package com.nicole.petnanny.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.data.*
import com.nicole.petnanny.data.source.PetNannyDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.order.nannyorder.detail.MyClientDetailViewModel
import com.nicole.petnanny.ui.order.parentorder.detail.MyOrderDetailViewModel

object PetNannyRemoteDataSource : PetNannyDataSource {

    private const val KEY_CREATED_TIME = "createdTime"
    private const val PATH_PET = "Pet"
    private const val PATH_NANNY = "Nanny"
    private const val PATH_USER = "User"
    private const val PATH_ORDER = "Order"
    private const val PATH_MESSAGE = "Message"

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
                .update(
                    "selfIntroduction", user.selfIntroduction,
                    "userName", user.userName,
                    "photo", UserManager.user.value?.photo
                )
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
                        document.set(user).addOnSuccessListener {
                            Log.d("DocumentSnapshot", "add with userEmail: $userCollection")
                        }
                            .addOnFailureListener { e ->
                                Log.d("DocumentSnapshot111", "Error adding document $e")
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

    override suspend fun addDemand(demand: Order): Result<Boolean> =
        suspendCoroutine { continuation ->
            val orderCollection = FirebaseFirestore.getInstance().collection(PATH_ORDER)
            val document = orderCollection.document()

            demand.orderID = document.id

            document
                .set(demand)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addDemand", "addDemand: $demand")


                        val messageNanny = demand.nannyEmail?.let {
                            Message(
                                senderName = demand.nannyServiceDetail?.nannyName,
                                senderImage = demand.nannyServiceDetail?.nannyPhoto.toString(),
                                senderEmail = it,
                                messageTime = System.currentTimeMillis(),
                                isRead = false,
                                content = "你好，需要什麼服務呢？"
                            )
                        }

                        val messageParent = demand.userEmail?.let {
                            Message(
                                senderName = demand.userInfo?.userName,
                                senderImage = demand.userInfo?.photo.toString(),
                                senderEmail = it,
                                messageTime = System.currentTimeMillis(),
                                isRead = false,
                                content = "你好～～～"
                            )
                        }

                        FirebaseFirestore.getInstance().collection(PATH_ORDER).document(document.id)
                            .collection(PATH_MESSAGE).add(messageNanny!!)

                        FirebaseFirestore.getInstance().collection(PATH_ORDER).document(document.id)
                            .collection(PATH_MESSAGE).add(messageParent!!)

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

    override suspend fun getMyOrderDataResult(): Result<List<Order>> =
        suspendCoroutine { continuation ->
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

    override suspend fun getMyClientDataResult(nannyEmail: String): Result<List<Order>> =
        suspendCoroutine { continuation ->
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

    //    家長提出 保姆update接受狀態為true
    override suspend fun updateNannyAcceptStatus(
        orderID: String,
        viewModel: MyClientDetailViewModel
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val order = FirebaseFirestore.getInstance().collection(PATH_ORDER)

        order.document(orderID)
            .get()
            .addOnSuccessListener {
                Log.d("DocumentSnapshot", " $it ")
                val documentId = order.document(orderID)
                documentId.update("nannyAcceptStatus", true).addOnSuccessListener {
                    viewModel.liveAcceptStatusNanny.value = true
                }
            }
            .addOnFailureListener { e ->
                Log.d("Error adding document", "$e ")
            }

    }

    //      保姆接受後 家長等待付款
    override suspend fun getMyOrderNannyAcceptStatus(orderID: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            var nannyAcceptStatus = false

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.data.let {
                            Log.d("123", "${task.result}")
                            val order: Order = task.result?.toObject(Order::class.java)!!
                            nannyAcceptStatus = order.nannyAcceptStatus!!
                        }
                        continuation.resume(Result.Success(nannyAcceptStatus))
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

    //    家長等待付款 保姆準備開始服務
    override suspend fun updateParentCheckoutCompleteStatus(
        orderID: String,
        viewModel: MyOrderDetailViewModel
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val order = FirebaseFirestore.getInstance().collection(PATH_ORDER)

        order.document(orderID)
            .get()
            .addOnSuccessListener {
                Log.d("userCheckoutStatus", " $it ")
                val documentId = order.document(orderID)
                documentId.update("userCheckoutStatus", true).addOnSuccessListener {
                    viewModel.liveCheckoutStatusParent.value = true
                }
            }
            .addOnFailureListener { e ->
                Log.d("Error adding document", "$e ")
            }

    }

    //    家長付款完成 保姆收到通知
    override suspend fun getMyClientParentCheckoutCompleteStatus(orderID: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            var userCheckoutStatus = false

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.data.let {
                            Log.d("123", "${task.result}")
                            val order: Order = task.result?.toObject(Order::class.java)!!
                            userCheckoutStatus = order.userCheckoutStatus!!
                        }
                        continuation.resume(Result.Success(userCheckoutStatus))
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

    //    保姆完成服務 update完成狀態為true
    override suspend fun updateNannyCompleteServiceStatus(
        orderID: String,
        viewModel: MyClientDetailViewModel
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val order = FirebaseFirestore.getInstance().collection(PATH_ORDER)

        order.document(orderID)
            .get()
            .addOnSuccessListener {
                Log.d("DocumentSnapshot", " $it ")
                val documentId = order.document(orderID)
                documentId.update("nannyCompletedStatus", true).addOnSuccessListener {
                    viewModel.liveNannyCompleteServiceStatus.value = true
                }
            }
            .addOnFailureListener { e ->
                Log.d("Error adding document", "$e ")
            }

    }

    override suspend fun getNannyServiceCompletedStatus(orderID: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            var nannyServiceCompletedStatus = false

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.data.let {
                            Log.d("123", "${task.result}")
                            val order: Order = task.result?.toObject(Order::class.java)!!
                            nannyServiceCompletedStatus = order.nannyCompletedStatus!!
                        }
                        continuation.resume(Result.Success(nannyServiceCompletedStatus))
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

    override suspend fun updateParentCheckCompleteServiceStatus(
        orderID: String,
        viewModel: MyOrderDetailViewModel
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val order = FirebaseFirestore.getInstance().collection(PATH_ORDER)

        order.document(orderID)
            .get()
            .addOnSuccessListener {
                Log.d("DocumentSnapshot", " $it ")
                val documentId = order.document(orderID)
                documentId.update("userCheckedStatus", true).addOnSuccessListener {
                    viewModel.liveParentCheckCompleteServiceStatus.value = true
                }
            }
            .addOnFailureListener { e ->
                Log.d("Error adding document", "$e ")
            }

    }

    override suspend fun getParentCheckServiceCompleteStatus(orderID: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            var liveCheckServiceCompleteStatus = false

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.data.let {
                            Log.d("123", "${task.result}")
                            val order: Order = task.result?.toObject(Order::class.java)!!
                            liveCheckServiceCompleteStatus = order.userCheckedStatus!!
                        }
                        continuation.resume(Result.Success(liveCheckServiceCompleteStatus))
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


    override suspend fun getDemandChatListResult(): Result<List<Order>> =
        suspendCoroutine { continuation ->
            Log.d("userEmailyyyyyyyy", "${UserManager.user.value?.userEmail}")
            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .whereEqualTo("userEmail", UserManager.user.value?.userEmail)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Order>()
                        for (document in task.result!!) {
                            Log.d("resultMyOrder@@@@@", "${document.id} => ${document.data}")

                            val myOrder = document.toObject(Order::class.java)
                            list.add(myOrder)
                        }
                        var count = 0

                        list.forEach { data ->
                            data.orderID?.let {
                                FirebaseFirestore.getInstance()
                                    .collection(PATH_ORDER)
                                    .document(it).collection(PATH_MESSAGE)
                                    .orderBy("messageTime", Query.Direction.DESCENDING).get()
                                    .addOnSuccessListener { item ->
                                        val message = item.toObjects(Message::class.java)
                                        if (message.isNotEmpty()) {

                                            data.lastMessage = message[0]
                                            count += 1

                                            if (count == list.size) {
                                                continuation.resume(Result.Success(list))
                                            }
                                        }

//                                    如果抓最新的是對方的 要用filter
//                                    val lastMessage = message.filter { it ->
//                                        it.senderEmail != UserManager.user.value?.userEmail
//                                    }
//                                    Log.d("fffffff", "$lastMessage ");
//                                    data.lastMessage = lastMessage[0]


                                    }
                            }
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

    override suspend fun addMessage(orderID: String, message: Message): Result<Boolean> =
        suspendCoroutine { continuation ->
            Log.d("orderID", "$orderID, $message ")
            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .collection(PATH_MESSAGE)
                .add(message)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addMessage", "addPet: $message")

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

    override suspend fun getMessage(orderID: String?): Result<List<Message>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID!!)
                .collection(PATH_MESSAGE)
                .orderBy("messageTime", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Message>()
                        for (document in task.result!!) {
                            Log.d("getMessage", "${document.id} => ${document.data}")

                            val message = document.toObject(Message::class.java)
                            list.add(message)
                        }
                        continuation.resume(Result.Success(list))
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

    override fun getLiveDemandOrders(): MutableLiveData<List<Order>> {

        val liveData = MutableLiveData<List<Order>>()

        Log.d("getLiveDemandOrders", "getLiveDemandOrders ");

        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .addSnapshotListener { snapshot, exception ->

                exception?.let {
                    Log.d(
                        "get_service_exception",
                        "[${this::class.simpleName}] Error getting documents. ${it.message}"
                    )
                }

                val list = mutableListOf<Order>()
                for (document in snapshot!!) {
                    Log.d("getLiveDemandOrders111", "${document.id} => ${document.data}")

                    val order = document.toObject(Order::class.java)
                    Log.d("jjjjjjj", "$order ")
                    list.add(order)
                }
                Log.d("laaaaaaauuuuuuuuuuu", "$list ")

                list.forEach { data ->
                    data.orderID?.let {
                        FirebaseFirestore.getInstance()
                            .collection(PATH_ORDER)
                            .document(it)
                            .collection(PATH_MESSAGE)
                            .orderBy("messageTime", Query.Direction.DESCENDING)
                            .addSnapshotListener { item, error ->
                                Log.d("data~~~~~~~~~~`", "$data ")
                                val message = item?.toObjects(Message::class.java)
                                if (!message.isNullOrEmpty()) {
                                    data.lastMessage = message.get(0)

                                }
                            }
                    }
                }
                liveData.value = list
            }
        return liveData
    }


    override fun getLiveMessages(orderID: String?): MutableLiveData<List<Message>> {
        Log.d("startMessage", "ppppp")

        val liveData = MutableLiveData<List<Message>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .document(orderID as String)
            .collection(PATH_MESSAGE)
            .orderBy("messageTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    Log.d(
                        "get_service_exception",
                        "[${this::class.simpleName}] Error getting documents. ${it.message}"
                    )
                }

                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Log.d("resultMyDemandMessage", "${document.id} => ${document.data}")

                    val message = document.toObject(Message::class.java)
                    list.add(message)
                }
                liveData.value = list
                Log.d("vvvvvv", "$list ")
//                    list.sortBy { message -> message.messageTime }

            }
        return liveData
    }

    override suspend fun getWorkChatListResult(nannyEmail: String): Result<List<Order>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .whereEqualTo("nannyEmail", UserManager.user.value?.userEmail)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Order>()
                        for (document in task.result!!) {
                            Log.d("resultMyOrder@@@@@", "${document.id} => ${document.data}")

                            val myOrder = document.toObject(Order::class.java)
                            list.add(myOrder)
                        }
                        var count = 0

                        list.forEach { data ->
                            data.orderID?.let {
                                FirebaseFirestore.getInstance()
                                    .collection(PATH_ORDER)
                                    .document(it).collection(PATH_MESSAGE)
                                    .orderBy("messageTime", Query.Direction.DESCENDING).get()
                                    .addOnSuccessListener { item ->
                                        val message = item.toObjects(Message::class.java)
                                        if (message.isNotEmpty()) {
                                            data.lastMessage = message[0]

                                            count += 1

                                            if (count == list.size) {
                                                continuation.resume(Result.Success(list))
                                            }
                                        }
                                    }
                            }
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

    override suspend fun getWorkMessage(orderID: String?): Result<List<Message>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID!!)
                .collection(PATH_MESSAGE)
                .orderBy("messageTime", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Message>()
                        for (document in task.result!!) {
                            Log.d("getMessage", "${document.id} => ${document.data}")

                            val message = document.toObject(Message::class.java)
                            list.add(message)
                        }
                        continuation.resume(Result.Success(list))
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

    override suspend fun addWorkMessage(orderID: String, workMessage: Message): Result<Boolean> =
        suspendCoroutine { continuation ->
            Log.d("orderID", "$orderID, $workMessage ")
            FirebaseFirestore.getInstance()
                .collection(PATH_ORDER)
                .document(orderID)
                .collection(PATH_MESSAGE)
                .add(workMessage)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("addMessage", "addPet: $workMessage")

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

    override fun getLiveWorkMessages(orderID: String?): MutableLiveData<List<Message>> {
        Log.d("startMessage", "ppppp")

        val liveData = MutableLiveData<List<Message>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .document(orderID as String)
            .collection(PATH_MESSAGE)
            .orderBy("messageTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    Log.d(
                        "get_service_exception",
                        "[${this::class.simpleName}] Error getting documents. ${it.message}"
                    )
                }

                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Log.d("resultMyDemandMessage", "${document.id} => ${document.data}")

                    val message = document.toObject(Message::class.java)
                    list.add(message)
                }
                liveData.value = list
                Log.d("vvvvvv", "$list ")
//                    list.sortBy { message -> message.messageTime }

            }
        return liveData
    }

    override fun getLiveWorkOrders(): MutableLiveData<List<Order>> {

        val liveData = MutableLiveData<List<Order>>()

        Log.d("getLiveDemandOrders", "getLiveDemandOrders ")

        FirebaseFirestore.getInstance()
            .collection(PATH_ORDER)
            .addSnapshotListener { snapshot, exception ->

                exception?.let {
                    Log.d(
                        "get_service_exception",
                        "[${this::class.simpleName}] Error getting documents. ${it.message}"
                    )
                }

                val list = mutableListOf<Order>()
                for (document in snapshot!!) {
                    Log.d("getLiveDemandOrders111", "${document.id} => ${document.data}")

                    val order = document.toObject(Order::class.java)
                    Log.d("jjjjjjj", "$order ")
                    list.add(order)
                }


                list.forEach { data ->
                    data.orderID?.let {
                        FirebaseFirestore.getInstance()
                            .collection(PATH_ORDER)
                            .document(it).collection(PATH_MESSAGE)
                            .orderBy("messageTime", Query.Direction.DESCENDING)
                            .addSnapshotListener { item, error ->
                                Log.d("data~~~~~~~~~~`", "$data ")
                                val message = item?.toObjects(Message::class.java)
                                if (!message.isNullOrEmpty()) {
                                    data.lastMessage = message.get(0)
                                }
                            }
                    }
                }
                liveData.value = list
            }
        return liveData
    }

}