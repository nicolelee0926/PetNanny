package com.nicole.petnanny.ui.profile.service.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentProfileAddServiceBinding

class AddServiceFragment: Fragment() {

    private lateinit var viewModel: AddServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(AddServiceViewModel::class.java)
        binding.viewModel = viewModel

//        binding.btnAddService.setOnClickListener {
//            viewModel.setService()
//        }

        viewModel.sendServiceData.observe(viewLifecycleOwner) {

        }

        return binding.root
    }
}