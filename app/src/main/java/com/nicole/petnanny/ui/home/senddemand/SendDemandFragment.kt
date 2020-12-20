package com.nicole.petnanny.ui.home.senddemand

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private val viewModel by viewModels<SendDemandViewModel> { getVmFactory(SendDemandFragmentArgs.fromBundle(requireArguments()).nanny) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSendDemandBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnSendDemand.setOnClickListener {
            if (viewModel.checkInfoComplete()) {
                viewModel.sendDemand()
            } else {
                Toast.makeText(requireContext(), "您的資料還沒填完唷", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.setDemandData.observe(viewLifecycleOwner, Observer {
            viewModel.addDemand(it)
            Toast.makeText(requireContext(), "需求單新增成功", Toast.LENGTH_SHORT).show()
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

//      set picker binding
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            binding.btnStartTime.text = ("${SimpleDateFormat("yyyy.MM.dd").format(it.first)}")
            viewModel.orderStartTime.value = binding.btnStartTime.text.toString()
            binding.btnEndTime.text = ("${SimpleDateFormat("yyyy.MM.dd").format(it.second)}")
            viewModel.orderEndTime.value = binding.btnEndTime.text.toString()
            calculationDay(it, binding)
        }

//      set choice pet button
        binding.btnChoiceUserPet.setOnClickListener {
            viewModel.userPetList.value?.let { it1 -> fragmentManager?.let { it2 ->
                SelectUserPetDialog(it1, viewModel).show(
                    it2, "")
            } }
        }


//       observe selected pet then change pet raw view
        viewModel.selectedPet.observe(viewLifecycleOwner, Observer {
            binding.tvUserPetName.visibility = View.VISIBLE
            binding.cardUserPetPhoto.visibility = View.VISIBLE
            binding.btnChoiceUserPet.visibility = View.GONE
        })

        return binding.root
    }

//    計算總天數
    fun calculationDay(time: Pair<Long, Long>, binding: FragmentSendDemandBinding) {
        val startDay = time.first
        val endDay = time.second
        startDay?.let {
            endDay?.let {
                val totalTime = ((endDay - startDay ) / (60*60*1000*24)) +1
                binding.tvTotalDay.text = "x $totalTime"
                viewModel.demandDay.value = binding.tvTotalDay.text.toString()

//                計算單價*天數等於總價
                val price = viewModel.nannyDataArgus.value?.price?.toLong()
                price?.let {
                    val totalPrice = totalTime * price
                    binding.tvTotalPrice.text = "$ $totalPrice"
                    viewModel.totalPrice.value = binding.tvTotalPrice.text.toString()
                }
            }
        }
    }


}