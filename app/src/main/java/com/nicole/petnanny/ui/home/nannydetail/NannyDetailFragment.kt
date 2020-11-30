package com.nicole.petnanny.ui.home.nannydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentHomeNannyDetailBinding
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections

class NannyDetailFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeNannyDetailBinding.inflate(inflater, container, false)

        binding.btnContactNanny.setOnClickListener {
            findNavController().navigate(NannyDetailFragmentDirections.actionNannyDetailFragmentToSendDemandFragment())
        }

        return binding.root
    }
}