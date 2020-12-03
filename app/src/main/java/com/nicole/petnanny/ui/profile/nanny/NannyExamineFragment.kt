package com.nicole.petnanny.ui.profile.nanny

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentProfileNannyExamineBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel

class NannyExamineFragment: Fragment() {

    private val viewModel by viewModels<NannyExamineViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileNannyExamineBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addNannyExamineFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setNannyExamine()
                mainViewModel.changeNannyExamineStatusFalse()
            }
        })

        viewModel.setNannyExamineData.observe(viewLifecycleOwner, Observer {
            Log.d("nannyExamineEditText", "$it ")
            viewModel.addNannyExamine(it)
        })


        return binding.root
    }
}