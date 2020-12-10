package com.nicole.petnanny.ui.home.nannydetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class NannyDetailFragment:Fragment() {

    private val viewModel by viewModels<NannyDetailViewModel> { getVmFactory(NannyDetailFragmentArgs.fromBundle(requireArguments()).nanny) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnContactNanny.setOnClickListener {
            Log.d("***********", "${viewModel.navigateToDemandNannyData.value}")
                findNavController().navigate(NannyDetailFragmentDirections.actionNannyDetailFragmentToSendDemandFragment(viewModel.navigateToDemandNannyData.value!!))
        }

        return binding.root
    }


}