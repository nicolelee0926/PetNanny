package com.nicole.petnanny.ui.order.parentorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentOrderMyOrderBinding

class MyOrderFragment(): Fragment() {
    var type : Int = 0
    constructor(int : Int) : this() {
        this.type = int
    }

    private lateinit var viewModel: MyOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderMyOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MyOrderViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}