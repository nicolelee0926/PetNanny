package app.appworks.school.publisher.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.pet.PetViewModel
import com.nicole.petnanny.ui.profile.pet.add.AddPetFragment
import com.nicole.petnanny.ui.profile.pet.add.AddPetViewModel
import com.nicole.petnanny.ui.profile.service.ServiceViewModel
import com.nicole.petnanny.ui.profile.service.add.AddServiceViewModel

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels.
 */
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

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
