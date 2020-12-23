package com.nicole.petnanny.ui.profile.nanny

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentProfileNannyExamineBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections
import java.util.*


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
            if (it == true) {
                if (viewModel.checkInfoComplete()) {
                    viewModel.setNannyExamine()
                    mainViewModel.changeNannyExamineStatusFalse()
                    setDialog().apply {
                        findNavController().navigate(NannyExamineFragmentDirections.actionNannyExamineFragmentToNavigationProfile())
                    }
                } else {
                    Toast.makeText(requireContext(), "您的資料還沒填完唷", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.setNannyExamineData.observe(viewLifecycleOwner, Observer {
            Log.d("nannyExamineEditText", "$it ")
            viewModel.addNannyExamine(it)

            setDialog()
        })


        return binding.root
    }

    private fun setDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("保姆審查資料")
        builder.setMessage("建立成功，請耐心等待審查！")
        builder.setCancelable(true)
        val dlg: AlertDialog = builder.create()
        dlg.show()
        val t = Timer()
        t.schedule(object : TimerTask() {
            override fun run() {
                dlg.dismiss()
                t.cancel()
            }
        }, 3000)
    }
}