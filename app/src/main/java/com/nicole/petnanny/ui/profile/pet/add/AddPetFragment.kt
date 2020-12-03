package com.nicole.petnanny.ui.profile.pet.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nicole.petnanny.R
import com.nicole.petnanny.databinding.FragmentProfileAddPetBinding
import com.nicole.petnanny.ext.getVmFactory
import com.nicole.petnanny.ui.main.MainViewModel

class AddPetFragment : Fragment() {

    private val viewModel by viewModels<AddPetViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileAddPetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.addPetFlag.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.setPet()
                mainViewModel.changePetStatusFalse()
            }
        })

        viewModel.setPetData.observe(viewLifecycleOwner, Observer {
            Log.d("petEditText", "$it ")
            viewModel.addPet(it)
        })



        binding.radioGender.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_female -> {
                        viewModel.setGender("母")
                        Toast.makeText(requireContext(),"你選了母",Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_male -> {
                        viewModel.setGender("公")
                        Toast.makeText(requireContext(),"你選了公",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        binding.radioLigation.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_yes -> {
                        viewModel.setLigation("是")
                        Toast.makeText(requireContext(),"你選了是",Toast.LENGTH_SHORT).show()
                    }
                    R.id.radio_no -> {
                        viewModel.setLigation("否")
                        Toast.makeText(requireContext(),"你選了否",Toast.LENGTH_SHORT).show()
                    }
                }
            })


        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
               viewModel.selectedType.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                viewModel.selectedAge.value = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return binding.root
    }
}