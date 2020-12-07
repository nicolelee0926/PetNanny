package com.nicole.petnanny.ui.home.nannydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class NannyDetailFragment:Fragment() {

    private val viewModel by viewModels<NannyDetailViewModel> { getVmFactory(NannyDetailFragmentArgs.fromBundle(requireArguments()).nannyDetail) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnContactNanny.setOnClickListener {
            findNavController().navigate(NannyDetailFragmentDirections.actionNannyDetailFragmentToSendDemandFragment())
        }

        return binding.root
    }
}