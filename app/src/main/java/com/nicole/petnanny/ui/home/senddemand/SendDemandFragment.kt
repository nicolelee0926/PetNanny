package com.nicole.petnanny.ui.home.senddemand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.databinding.FragmentSendDemandBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailFragmentArgs
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel

class SendDemandFragment: Fragment() {

    private val viewModel by viewModels<SendDemandViewModel> { getVmFactory(SendDemandFragmentArgs.fromBundle(requireArguments()).nanny, SendDemandFragmentArgs.fromBundle(requireArguments()).user)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSendDemandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnSendDemand.setOnClickListener {
                viewModel.sendDemand()
        }

        viewModel.setDemandData.observe(viewLifecycleOwner, Observer {
            viewModel.addDemand(it)
            findNavController().navigate(MobileNavigationDirections.actionGlobalNavigationOrder())
        })



        return binding.root
    }
}