package com.nicole.petnanny.ui.profile.user

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
import com.nicole.petnanny.databinding.FragmentPetBinding
import com.nicole.petnanny.databinding.FragmentProfileAddUserBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections
import com.nicole.petnanny.ui.profile.service.add.AddServiceViewModel

class AddUserFragment: Fragment() {

    private val viewModel by viewModels<AddUserViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addServiceFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setUser()
                mainViewModel.changeServiceStatusFalse()
            }
        })

        viewModel.setUserData.observe(viewLifecycleOwner, Observer {
            Log.d("userEditText", "$it ")
            viewModel.addUser(it)
        })

        return binding.root
    }

}