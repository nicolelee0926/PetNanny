package app.appworks.school.publisher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.chat.demand.DemandViewModel
import com.nicole.petnanny.ui.chat.demand.add.DemandDetailViewModel
import com.nicole.petnanny.ui.home.HomeViewModel
import com.nicole.petnanny.ui.home.senddemand.SendDemandViewModel
import com.nicole.petnanny.ui.login.LoginViewModel
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.ProfileViewModel
import com.nicole.petnanny.ui.profile.nanny.NannyExamineViewModel
import com.nicole.petnanny.ui.profile.pet.PetViewModel
import com.nicole.petnanny.ui.profile.pet.add.AddPetViewModel
import com.nicole.petnanny.ui.profile.service.ServiceViewModel
import com.nicole.petnanny.ui.profile.service.add.AddServiceViewModel
import com.nicole.petnanny.ui.profile.user.AddUserViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: PetNannyRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(PetViewModel::class.java) ->
                    PetViewModel(repository)

                isAssignableFrom(AddPetViewModel::class.java) ->
                    AddPetViewModel(repository)

                isAssignableFrom(ServiceViewModel::class.java) ->
                    ServiceViewModel(repository)

                isAssignableFrom(AddServiceViewModel::class.java) ->
                    AddServiceViewModel(repository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(repository)

                isAssignableFrom(AddUserViewModel::class.java) ->
                    AddUserViewModel(repository)

                isAssignableFrom(NannyExamineViewModel::class.java) ->
                    NannyExamineViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(SendDemandViewModel::class.java) ->
                    SendDemandViewModel(repository)

                isAssignableFrom(DemandViewModel::class.java) ->
                    DemandViewModel(repository)

                isAssignableFrom(DemandDetailViewModel::class.java) ->
                    DemandDetailViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
