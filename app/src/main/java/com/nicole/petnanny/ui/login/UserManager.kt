package com.nicole.petnanny.ui.login

import androidx.lifecycle.MutableLiveData
import com.nicole.petnanny.data.Nanny
import com.nicole.petnanny.data.User

object UserManager {

    var user = MutableLiveData<User>()

    var nanny = MutableLiveData<Nanny>()

}