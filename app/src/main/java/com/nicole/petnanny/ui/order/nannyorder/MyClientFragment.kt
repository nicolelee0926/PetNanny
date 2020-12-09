package com.nicole.petnanny.ui.order.nannyorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentOrderMyClientBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.order.OrderFragmentDirections
import com.nicole.petnanny.ui.order.parentorder.MyOrderAdapter

class MyClientFragment(): Fragment() {

    private val viewModel by viewModels<MyClientViewModel> { getVmFactory() }

    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderMyClientBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val myClientAdapter = MyClientAdapter(viewModel)
        binding.rvOrderMyClient.adapter = myClientAdapter

        //        navigate到MyClient detail頁
        viewModel.navigationToMyClientDetail.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                findNavController().navigate(OrderFragmentDirections.actionNavigationOrderToMyClientDetailFragment())
                viewModel.displayMyClientDetailsComplete()
            }
            if (it != null) {
                viewModel.displayMyClientDetailsComplete()
            }
        })

        return binding.root
    }
}