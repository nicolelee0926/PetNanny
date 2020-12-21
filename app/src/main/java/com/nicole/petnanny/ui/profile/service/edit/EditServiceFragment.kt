package com.nicole.petnanny.ui.profile.service.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nicole.petnanny.databinding.FragmentProfileEditServiceDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class EditServiceFragment: Fragment() {

    private val viewModel by viewModels<EditServiceViewModel> { getVmFactory(EditServiceFragmentArgs.fromBundle(requireArguments()).nanny) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileEditServiceDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}