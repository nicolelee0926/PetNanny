package com.nicole.petnanny.ui.profile.pet.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentProfileAddPetBinding

class AddPetFragmentL : Fragment() {
    private lateinit var viewModel: AddPetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddPetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(AddPetViewModel::class.java)
        binding.viewModel = viewModel









        return binding.root
    }
}