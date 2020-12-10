package com.nicole.petnanny.ui.home.senddemand

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.nicole.petnanny.MobileNavigationDirections
import com.nicole.petnanny.databinding.FragmentSendDemandBinding
import com.nicole.petnanny.ext.getVmFactory
import java.text.SimpleDateFormat

class SendDemandFragment: Fragment() {

    private val viewModel by viewModels<SendDemandViewModel> { getVmFactory(SendDemandFragmentArgs.fromBundle(requireArguments()).nanny, SendDemandFragmentArgs.fromBundle(requireArguments()).user)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSendDemandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnSendDemand.setOnClickListener {
                viewModel.sendDemand()
        }

        viewModel.setDemandData.observe(viewLifecycleOwner, Observer {
            viewModel.addDemand(it)
            findNavController().navigate(MobileNavigationDirections.actionGlobalNavigationOrder())
        })

        //set data picker
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val now = Calendar.getInstance()
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))

        val picker = builder.build()


        binding.btnStartTime.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!, picker.toString())

        }

        binding.btnEndTime.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!, picker.toString())
        }


        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            binding.btnStartTime.setText("${SimpleDateFormat("yyyy.MM.dd").format(it.first)}")
            binding.btnEndTime.setText("${SimpleDateFormat("yyyy.MM.dd").format(it.second)}")
            calculationDay(it, binding)
        }

        return binding.root
    }

    fun calculationDay(time: Pair<Long, Long>, binding: FragmentSendDemandBinding) {
        val startDay = time.first
        val endDay = time.second
        startDay?.let {
            endDay?.let {
                val totalTime = (endDay - startDay) / (60*60*1000*24)
                binding.tvTotalDay.setText("$totalTime")
            }
        }
    }




}