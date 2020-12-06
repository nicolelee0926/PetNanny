package com.nicole.petnanny.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nicole.petnanny.CurrentFragmentType
import com.nicole.petnanny.PetNannyApplication
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.ActivityMainBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.profile.pet.add.AddPetViewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)
        binding.lifecycleOwner = this

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        viewModel
        binding.viewModel = viewModel

        setupNavController()


        binding.textToolbarAddPet.setOnClickListener {
            Log.d("hiya_pet", "hiya ")
            viewModel.changePetStatusTrue()
        }

        binding.textToolbarAddService.setOnClickListener {
            Log.d("hiya_service", "hiya ")
            viewModel.changeServiceStatusTrue()
        }

        binding.textToolbarAddUser.setOnClickListener {
            Log.d("hiya_user", "hiya ")
            viewModel.changeUserStatusTrue()
        }

        binding.textToolbarAddNannyExamine.setOnClickListener {
            Log.d("hiya_user", "hiya ")
            viewModel.changeNannyExamineStatusTrue()
        }
    }

    private fun setupNavController(){
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener{ navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when(navController.currentDestination?.id) {
                R.id.navigation_home -> CurrentFragmentType.HOME
                R.id.navigation_chat -> CurrentFragmentType.CHAT
                R.id.navigation_order -> CurrentFragmentType.ORDER
                R.id.navigation_profile -> CurrentFragmentType.PROFILE
                R.id.addUserFragment -> CurrentFragmentType.PROFILE_USER_EDIT
                R.id.nannyCenterFragment -> CurrentFragmentType.PROFILE_NANNY_CENTER
                R.id.addPetFragmentL -> CurrentFragmentType.PROFILE_ADD_PET
                R.id.addServiceFragment -> CurrentFragmentType.PROFILE_ADD_SERVICE
                R.id.nannyExamineFragment -> CurrentFragmentType.PROFILE_NANNY_CENTER_EXAMINE
                R.id.nannyLicense -> CurrentFragmentType.PROFILE_NANNY_CENTER_LICENSE
                R.id.nannyDetailFragment -> CurrentFragmentType.HOME_NANNY_DETAIL
                R.id.nannyListFragment -> CurrentFragmentType.HOME_SEARCH_NANNY
                R.id.loginFragment -> CurrentFragmentType.LOGIN
                else -> viewModel.currentFragmentType.value
            }
        }
    }
}