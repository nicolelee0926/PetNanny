package com.nicole.petnanny.ui.order.parentorder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentOrderMyOrderBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.order.OrderFragmentDirections

class MyOrderFragment(): Fragment() {

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private val viewModel by viewModels<MyOrderViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderMyOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val myOrderAdapter = MyOrderAdapter(viewModel)
        binding.rvOrderMyOrder.adapter = myOrderAdapter

        viewModel.myOrderList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.tvParentNoOrder.text = "您目前沒有任何需求訂單喔"
                binding.ivParentNoOrder.setImageDrawable(resources.getDrawable(R.drawable.ic_no_order))
            } else {
                myOrderAdapter.submitList(it)
            }
        })

        //  navigate到MyOrder detail頁
        viewModel.navigationToMyOrderDetail.observe(viewLifecycleOwner, Observer {
            if(null != it) {
                findNavController().navigate(OrderFragmentDirections.actionNavigationOrderToMyOrderDetailFragment(it))
                viewModel.displayMyOrderDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        UserManager.user.value?.userEmail?.let {
            viewModel.getMyOrderDataResult()
        }
    }
}