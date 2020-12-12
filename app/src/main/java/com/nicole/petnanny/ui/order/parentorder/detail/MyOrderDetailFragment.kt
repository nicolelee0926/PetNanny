package com.nicole.petnanny.ui.order.parentorder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.petnanny.databinding.FragmentMyOrderDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailFragmentArgs
import com.nicole.petnanny.ui.home.nannydetail.NannyDetailViewModel

class MyOrderDetailFragment : Fragment() {

    private val viewModel by viewModels<MyOrderDetailViewModel> {
        getVmFactory(
            MyOrderDetailFragmentArgs.fromBundle(requireArguments()).order
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyOrderDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        保姆接受 家長要去完成付款
        viewModel.liveAcceptStatusNanny.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.layoutWaitYourCheckout.visibility = View.VISIBLE
                binding.btnCheckoutComplete.visibility = View.VISIBLE
            }
        })

//    update ParentCheckoutCompleteStatus
        binding.btnCheckoutComplete.setOnClickListener {
            viewModel.updateParentCheckoutCompleteStatus()
        }
//    observe ParentCheckoutCompleteStatus
        viewModel.liveCheckoutStatusParent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.btnCheckoutComplete.visibility = View.GONE
                binding.layoutYourCheckoutCompleted.visibility = View.VISIBLE
            }
        })



        return binding.root
    }
}