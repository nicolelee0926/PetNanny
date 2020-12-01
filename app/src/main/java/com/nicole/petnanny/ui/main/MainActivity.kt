package com.nicole.petnanny.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nicole.petnanny.CurrentFragmentType
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)
        binding.lifecycleOwner = this

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        setupNavController()
    }

    private fun setupNavController(){
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener{ navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when(navController.currentDestination?.id) {
                R.id.navigation_home -> CurrentFragmentType.HOME
                R.id.navigation_chat -> CurrentFragmentType.CHAT
                R.id.navigation_order -> CurrentFragmentType.ORDER
                R.id.navigation_profile -> CurrentFragmentType.PROFILE
                else -> viewModel.currentFragmentType.value
            }
        }
    }
}