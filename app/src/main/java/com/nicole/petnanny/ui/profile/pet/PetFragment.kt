package com.nicole.petnanny.ui.profile.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class PetFragment() : Fragment()  {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private val viewModel by viewModels<PetViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val petAdapter = PetAdapter()
        binding.rvPet.adapter = petAdapter

        viewModel.pet.observe(viewLifecycleOwner, Observer {
                petAdapter.submitList(it)
        })

        binding.btnAddPet.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddPetFragmentL())
        }

        return binding.root
    }
}