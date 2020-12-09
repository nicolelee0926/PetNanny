package com.nicole.petnanny.ui.order.parentorder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nicole.petnanny.databinding.FragmentMyOrderDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class MyOrderDetailFragment: Fragment() {

    private val viewModel by viewModels<MyOrderDetailViewModel> { getVmFactory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyOrderDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }
}