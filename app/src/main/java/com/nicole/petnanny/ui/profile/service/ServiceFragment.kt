package com.nicole.petnanny.ui.profile.service

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentServiceBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections
import com.nicole.petnanny.ui.profile.pet.PetViewModel

class ServiceFragment(): Fragment() {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private val viewModel by viewModels<ServiceViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val serviceAdapter = ServiceAdapter()
        binding.rvNanny.adapter = serviceAdapter

        viewModel.service.observe(viewLifecycleOwner, Observer {
            serviceAdapter.submitList(it)
        })

        binding.btnAddService.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddServiceFragment())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("!!!!", "!!! ");
        UserManager.user.value?.userEmail?.let {
            Log.d("!!!", "$it ");
            viewModel.getServicesResult()
        }
    }
}