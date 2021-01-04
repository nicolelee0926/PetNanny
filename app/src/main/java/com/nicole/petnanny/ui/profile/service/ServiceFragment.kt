package com.nicole.petnanny.ui.profile.service

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nicole.petnanny.databinding.FragmentServiceBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.login.UserManager
import com.nicole.petnanny.ui.profile.ProfileFragmentDirections
import com.nicole.petnanny.ui.profile.pet.PetViewModel
import java.util.*

class ServiceFragment() : Fragment() {
    var type: Int = 0

    constructor(int: Int) : this() {
        this.type = int
    }

    private val viewModel by viewModels<ServiceViewModel> { getVmFactory() }

    lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentServiceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val serviceAdapter = ServiceAdapter(viewModel, binding.layoutServiceListView)
        binding.rvNanny.adapter = serviceAdapter

        viewModel.service.observe(viewLifecycleOwner, Observer {
            serviceAdapter.submitList(it)
        })

        binding.btnAddService.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToAddServiceFragment())
        }

        //  navigate to edit service detail page
        viewModel.navigationToEditSerciveDetail.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToEditServiceFragment(it))
                viewModel.displayEditServiceDetailsComplete()
            }
        })

        //   show dialog
        viewModel.isShowDialog.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                setVerificationDialog()
            } else {
                binding.btnAddService.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("!!!!", "!!! ")
        if (UserManager.user.value?.verification == true) {
            UserManager.user.value?.userEmail?.let {
                Log.d("!!!", "$it ")
                viewModel.getServicesResult()
            }
        } else {
            setVerificationDialog()
        }
    }

    private fun setVerificationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("無法新增服務")
        builder.setMessage("請先至保姆中心通過審查！")
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