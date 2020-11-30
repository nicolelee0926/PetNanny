package com.nicole.petnanny.ui.profile.nanny

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentProfileNannyCenterBinding

class NannyCenterFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileNannyCenterBinding.inflate(inflater, container, false)

        binding.btnNannyExamine.setOnClickListener {
            findNavController().navigate(NannyCenterFragmentDirections.actionNannyCenterFragmentToNannyExamineFragment())
        }

        binding.btnNannyLicenese.setOnClickListener {
            findNavController().navigate(NannyCenterFragmentDirections.actionNannyCenterFragmentToNannyLicense())
        }

        return binding.root
    }

}