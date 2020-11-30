package com.nicole.petnanny.ui.profile.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentServiceBinding
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class ServiceFragment(): Fragment() {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private lateinit var viewModel: ServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        binding.viewModel = viewModel

        val serviceAdapter = ServiceAdapter()
        binding.rvNanny.adapter = serviceAdapter

        viewModel.serviceListData.observe(viewLifecycleOwner, Observer {
            serviceAdapter.submitList(it)
        })

        binding.btnAddService.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddServiceFragment())
        }

        return binding.root
    }
}