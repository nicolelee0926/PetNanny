package com.nicole.petnanny.ui.order.nannyorder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.databinding.FragmentMyClientDetailBinding
import com.nicole.petnanny.ext.getVmFactory

class MyClientDetailFragment: Fragment() {

    private val viewModel by viewModels<MyClientDetailViewModel> { getVmFactory(MyClientDetailFragmentArgs.fromBundle(requireArguments()).order) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyClientDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


//    updateNannyAcceptStatus
        binding.btnAcceptOrder.setOnClickListener {
            viewModel.updateNannyAcceptStatus()
        }
//    observe acceptStatus
        viewModel.liveAcceptStatusNanny.observe(viewLifecycleOwner, Observer {
            if (it ==true){
                binding.btnRejectOrder.visibility = View.GONE
                binding.btnAcceptOrder.visibility = View.GONE
                binding.layoutWaitParentCheckout.visibility = View.VISIBLE
            }
        })



        return binding.root
    }

}