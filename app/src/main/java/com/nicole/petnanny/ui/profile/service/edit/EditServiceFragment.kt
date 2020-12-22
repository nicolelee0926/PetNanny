package com.nicole.petnanny.ui.profile.service.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.databinding.FragmentProfileEditServiceDetailBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel

class EditServiceFragment : Fragment() {

    private val viewModel by viewModels<EditServiceViewModel> { getVmFactory(EditServiceFragmentArgs.fromBundle(requireArguments()).nanny) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileEditServiceDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //  preload gender and petLigation
        viewModel.serviceDetail.observe(viewLifecycleOwner, Observer {

            // Preload name
            binding.edServiceName.setText(it.serviceName)

            // Preload introduction
            binding.edServiceIntroduction.setText(it.nannyIntroduction)

            // Preload price
            binding.editServicePrice.setText(it.price)
        })

//        send to firebase update data
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.editServiceFlag.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.setEditService()
                Toast.makeText(requireContext(), "修改服務資料成功", Toast.LENGTH_SHORT).show()
                mainViewModel.changeEditServiceStatusFalse()
            }
        })

//        update modified edit pet data
        viewModel.setEditServiceData.observe(viewLifecycleOwner, Observer {
            Log.d("setEditPetDatauuuuu", "$it ")
            viewModel.updateService(it)
        })

        binding.spinnerServiceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedServiceType.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.spinnerServiceArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedServiceLocation.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerAcceptType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.editSelectedAcceptPet.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        return binding.root
    }
}